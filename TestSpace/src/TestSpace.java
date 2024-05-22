import java.awt.*;
import java.awt.event.KeyEvent;

public class TestSpace extends GameEngine {
    private final ParticleEmitter emitter = new FireParticleEmitter();
    private final MainMenu mainMenu = new MainMenu(this);
    public static final int WIDTH  = 600;
    public static final int HEIGHT = 840;
    public static final BulletManager bulletManager = new BulletManager();
    private Player player;

    public enum State {
        MAIN_MENU,
        PLAYING
    }

    private State state = State.MAIN_MENU;

    Image background = loadImage("TestSpace/resources/LargeSpace.png");

    public static void main(String[] args) {
        createGame(new TestSpace(), 60

         );
    }

    public void init() {
        setWindowSize(WIDTH, HEIGHT);
    }

    public void startGame() {
        this.player = new Player(WIDTH / 2, HEIGHT - 100, GameEngine.loadImage("TestSpace/resources/Spaceman.png"));
        emitter.move((float)player.getX()+10, HEIGHT - 105);

        state = State.PLAYING;
    }

    @Override
    public void update(double dt) {
        switch (state) {
            case MAIN_MENU -> mainMenu.update();
            case PLAYING -> {
                player.update(dt);
                emitter.update((float) dt);
                bulletManager.updateBullets(dt);
            }
        }
    }

    @Override
    public void paintComponent() {
        changeBackgroundColour(black);
        clearBackground(WIDTH, HEIGHT);

        switch (state) {
            case MAIN_MENU -> mainMenu.draw();
            case PLAYING -> {
                drawBackground();
                player.draw(this);
                drawEmitter();
                bulletManager.drawBullets(mGraphics);
            }
        }
    }

    public void drawBackground() {
        saveCurrentTransform();
        scale(1.2, 1.2);
        drawImage(background, 0,0);
        restoreLastTransform();
    }

    public void drawEmitter() {
        emitter.move((float)player.getX()+10, HEIGHT - 105);
        emitter.draw(this);
    }
    @Override
    public void keyPressed(KeyEvent event) {
        switch (state) {
            case MAIN_MENU -> mainMenu.keyPressed(event);
            case PLAYING -> {
                switch (event.getKeyCode()) {
                    case (KeyEvent.VK_LEFT) -> player.moveLeft();
                    case (KeyEvent.VK_RIGHT) -> player.moveRight();
                    case (KeyEvent.VK_SPACE) -> {
                        if (!player.isShooting()) {
                            player.startShooting();
                        }
                        emitter.emitterFrequency = 75;
                    }
                }
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent event) {
        if (state == State.PLAYING) {
            switch (event.getKeyCode()) {
                case (KeyEvent.VK_LEFT) -> {if(this.player.getDirection() == -1) {this.player.stop();}}
                case (KeyEvent.VK_RIGHT) -> {if(this.player.getDirection() == 1) {this.player.stop();}}
                case (KeyEvent.VK_SPACE) -> {
                    player.stopShooting();
                    emitter.emitterFrequency = 0;
                }
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent event){
        if (state == State.PLAYING) {
            switch (event.getKeyCode()) {
                case (KeyEvent.VK_SPACE) -> {
                    if (!player.isShooting()) {
                        player.startShooting();
                    }
                }
            }
        }
    }
}
