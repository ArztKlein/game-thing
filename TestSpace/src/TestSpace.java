import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Timer;

public class TestSpace extends GameEngine {
    private final ParticleEmitter emitter = new AlienParticleEmitter();
    public static final int WIDTH  = 600;
    public static final int HEIGHT = 840;
    private BulletManager bulletManager;
    private Player player;
    private Weapon weapon; //this will need to be an array of weapons later

    Image background = loadImage("TestSpace/resources/LargeSpace.png");

    public static void main(String[] args) {
        createGame(new TestSpace(), 60);
    }

    public void init() {
        setWindowSize(WIDTH, HEIGHT);
        emitter.move(300, 50);
        this.bulletManager = new BulletManager();
        this.player = new Player(WIDTH / 2, HEIGHT - 100, GameEngine.loadImage("TestSpace/resources/Spaceman.png"));

        weapon = new MachineGun(player, bulletManager);
    }
    @Override
    public void update(double dt) {
        emitter.update((float) dt);
        player.update(dt);
        bulletManager.updateBullets(dt);
        weapon.update(dt);
    }

    @Override
    public void paintComponent() {
        changeBackgroundColor(black);
        clearBackground(WIDTH, HEIGHT);

        drawBackground();

        player.draw(this);

        drawEmitter();

        bulletManager.drawBullets(mGraphics);
    }

    public void drawBackground() {
        saveCurrentTransform();
        scale(1.2, 1.2);
        drawImage(background, 0,0);
        restoreLastTransform();
    }

    public void drawEmitter() {
        emitter.draw(this);
    }
    @Override
    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
            case (KeyEvent.VK_LEFT) -> player.moveLeft();
            case (KeyEvent.VK_RIGHT) -> player.moveRight();
            case (KeyEvent.VK_SPACE) -> weapon.startShooting();
        }
    }
    @Override
    public void keyReleased(KeyEvent event) {
        switch (event.getKeyCode()) {
            case (KeyEvent.VK_LEFT) -> {if(this.player.getDirection() == -1) {this.player.stop();}}
            case (KeyEvent.VK_RIGHT) -> {if(this.player.getDirection() == 1) {this.player.stop();}}
            case (KeyEvent.VK_SPACE) -> weapon.stopShooting();
        }
    }
}
