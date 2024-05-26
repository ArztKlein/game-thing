import java.awt.*;
import java.awt.event.KeyEvent;

public class TestSpace extends GameEngine {
    private final ParticleEmitter emitter = new FireParticleEmitter();
    private final MainMenu mainMenu = new MainMenu(this);
    public static final int WIDTH  = 600;
    public static final int HEIGHT = 840;
    public static final BulletManager bulletManager = new BulletManager();
    private Player player;
    private AlienManager alienManager;
    private double timeSinceLastAlienSpawn = 0; // Handle time accumulation for alien spawning
    private final double ALIEN_SPAWN_INTERVAL = 0.05; // Adjust this value to control alien spawning rate

    public enum State {
        MAIN_MENU,
        PLAYING
    }

    private State state = State.MAIN_MENU;

    Image background = loadImage("TestSpace/resources/LargeSpace.png");

    public static void main(String[] args) {
        createGame(new TestSpace(), 60);
    }

    public void init() {
        setWindowSize(WIDTH, HEIGHT);
    }

    public void startGame() {
        this.player = new Player(WIDTH / 2, HEIGHT - 75, GameEngine.loadImage("TestSpace/resources/Spaceman.png"));
        emitter.move((float)player.getX()+10, HEIGHT - 105);
        state = State.PLAYING;
        alienManager = new AlienManager(loadImage("TestSpace/resources/Alien.png"), player); // Initialise the AlienManager
    }

    @Override
    public void update(double dt) {
        switch (state) {
            case MAIN_MENU:
                mainMenu.update();
                break;
            case PLAYING:
                player.update(dt);
                emitter.update((float) dt);
                bulletManager.updateBullets(dt, alienManager);
                alienManager.update(dt);

                // Check if player's health is zero and return to main menu if true
                if (player.isPlayerDead()) {
                    state = State.MAIN_MENU;
                    // Reset player's health when returning to main menu
                    player.resetHealth();
                }

                timeSinceLastAlienSpawn += dt;
                if (timeSinceLastAlienSpawn >= ALIEN_SPAWN_INTERVAL) {
                    alienManager.spawnAlien();
                    timeSinceLastAlienSpawn -= ALIEN_SPAWN_INTERVAL;
                }
                break;
        }
    }

    @Override
    public void paintComponent() {
        changeBackgroundColour(black);
        clearBackground(WIDTH, HEIGHT);

        switch (state) {
            case MAIN_MENU:
                mainMenu.draw();
                break;
            case PLAYING:
                drawBackground();
                player.draw(this);
                drawPlayerHealth();
                drawEmitter();
                bulletManager.drawBullets(this);
                alienManager.draw(this.mGraphics);
                break;


        }

    }

    private void drawPlayerHealth() {
        drawText(20, 40, "Health: " + player.getPlayerHealth());

    }


    public void drawBackground() {
        saveCurrentTransform();
        scale(1.2,1.2);
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