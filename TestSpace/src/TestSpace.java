import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Timer;

public class TestSpace extends GameEngine {
    private final ParticleEmitter emitter = new FireParticleEmitter();
    public static final int WIDTH  = 600;
    public static final int HEIGHT = 840;
    private BulletManager bulletManager;
    private Timer shootingTimer;
    private boolean shooting;
    private final int RPM = 300;
    private final int BULLET_INTERVAL = 200;
    private Player player;

    Image background = loadImage("TestSpace/resources/LargeSpace.png");

    public static void main(String[] args) {
        createGame(new TestSpace(), 60);
    }

    public void init() {
        setWindowSize(WIDTH, HEIGHT);
        emitter.move(WIDTH / 2f, HEIGHT - 100);
        this.bulletManager = new BulletManager(GameEngine.loadImage("TestSpace/resources/bullet.png"), HEIGHT);
        this.player = new Player(WIDTH / 2, HEIGHT - 100, loadImage("TestSpace/resources/Spaceman.png"));
        this.shooting = false;
    }
    @Override
    public void update(double dt) {
        player.update(dt);
        emitter.update((float) dt);
    }

    @Override
    public void paintComponent() {
        changeBackgroundColor(black);
        clearBackground(WIDTH, HEIGHT);

        drawBackground();

        player.draw(this);

        drawEmitter();

        bulletManager.drawBullets(this.mGraphics);
    }

    public void drawBackground() {
        saveCurrentTransform();
        scale(1.2, 1.2);
        drawImage(background, 0,0);
        restoreLastTransform();
    }


    public void drawEmitter() {
        emitter.move((float)player.getX()+20, HEIGHT - 105);
        emitter.draw(this);
    }


    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
            case (KeyEvent.VK_LEFT) -> player.moveLeft();
            case (KeyEvent.VK_RIGHT) -> player.moveRight();
            case (KeyEvent.VK_SPACE) -> emitter.emitterFrequency = 75;
        }
    }

    public void keyReleased(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_LEFT && this.player.getDirection() == -1) {
            this.player.stop();
        }

        if (event.getKeyCode() == KeyEvent.VK_RIGHT && this.player.getDirection() == 1) {
            this.player.stop();
        }
        if (event.getKeyCode() == KeyEvent.VK_SPACE) {
            emitter.emitterFrequency = 0;
        }
    }
}
