import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class TestSpace extends GameEngine {
    private final MainMenu mainMenu = new MainMenu(this);
    public static final int WIDTH = 600;
    public static final int HEIGHT = 840;
    public static final BulletManager bulletManager = new BulletManager();
    private Player player;
    private AlienManager alienManager;
    public static JTextField name;
    public String scoreName;
    private final Score score = new Score();
    boolean isWeapon;
    AudioClip backgroundMusic;

    public enum State {
        MAIN_MENU,
        PLAYING,
        GAME_OVER
    }

    public enum ScoreState {
        CHECK,
        SET_SCORE,
        NULL
    }

    private State state = State.MAIN_MENU;
    private ScoreState scoreState;

    Image background = loadImage("TestSpace/resources/LargeSpace.png");
    Image rocketUI = loadImage("TestSpace/resources/RocketUI.png");
    Image flameUI = loadImage("TestSpace/resources/FlameUI.png");
    Image bulletUI = loadImage("TestSpace/resources/BulletUI.png");

    public static void main(String[] args) {
        createGame(new TestSpace(), 60);
    }

    public void init() {
        setWindowSize(WIDTH, HEIGHT);

        // Init score
        Score.score = 0;
        scoreState = ScoreState.CHECK;
        name = new JTextField();

        isWeapon = true;

        backgroundMusic = loadAudio("TestSpace/resources/bensound-scifi.wav");
        startAudioLoop(backgroundMusic);
    }

    public void startGame() {
        this.player = new Player(WIDTH / 2, HEIGHT - 75, GameEngine.loadImage("TestSpace/resources/Spaceman.png"), this);
        state = State.PLAYING;

        //Initialize the alien sprites
        Image[] aSprites = new Image[3];
        Image[] maSprites = new Image[4];
        Image[] laSprites = new Image[4];
        for (int i = 0; i < 3; i++) {
            aSprites[i] = GameEngine.subImage(loadImage("TestSpace/resources/Flooper.png"), i*40, 0, 40, 40);
        }
        for ( int i = 0; i < 4; i++) {
            maSprites[i] = GameEngine.subImage(loadImage("TestSpace/resources/MedAlien.png"), i*30, 0, 30, 30);
            laSprites[i] = GameEngine.subImage(loadImage("TestSpace/resources/LargeAlien.png"), i*29, 0, 29, 37);
        }
        // Initialize the AlienManager
        alienManager = new AlienManager(aSprites, maSprites, laSprites, player, this);
    }

    @Override
    public void update(double dt) {
        switch (state) {
            case MAIN_MENU:
                mainMenu.update(dt);
                break;
            case PLAYING:
                player.update(dt);
                bulletManager.updateBullets(dt, alienManager);
                alienManager.update(dt);

                // Check if player's health is zero and return to main menu if true
                if (player.isPlayerDead()) {
                    state = State.GAME_OVER;
                    // Reset player's health when returning to main menu
                    player.resetHealth();
                }
                break;
            case GAME_OVER:
                if (isWeapon) {
                    Player.clearWeapons();
                    isWeapon = false;
                }
                manageScore();
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
                bulletManager.drawBullets(this);
                alienManager.draw(this);
                drawPlayerHealth();
                drawScore();
                break;
            case GAME_OVER:
                drawBackground();
                drawGameOver();
                break;
        }
    }

    private void drawPlayerHealth() {
        changeColour(white);
        saveCurrentTransform();
        changeColour(white);
        drawText(440, 30, "Health: " + player.getPlayerHealth(), 30);
        restoreLastTransform();
    }

    public void drawBackground() {
        saveCurrentTransform();
        scale(1.2, 1.2);
        drawImage(background, 0, 0);
        restoreLastTransform();
    }

    public void drawGameOver() {
        saveCurrentTransform();
        changeColour(white);
        drawCentredText("Game Over", WIDTH / 2, 200, 80);
        drawCentredText("Score: " + Score.score, WIDTH / 2, 300, 60);
        drawCentredText("Enter your name", WIDTH / 2, 350, 20);
        restoreLastTransform();
    }

    public void drawScore() {
        changeColour(white);
        drawText(10, 30, "Score: " + Score.score, 30);
    }

    public void manageScore() {
        switch (scoreState) {
            case CHECK -> {
                if (score.checkScore()) {
                    enterName();
                }
            }
            case SET_SCORE -> {
                score.updateHighScore(scoreName);
                scoreState = ScoreState.NULL;
            }
            case NULL -> {
                init();
                state = State.MAIN_MENU;
            }
        }
    }

    public void enterName() {
        name.setBounds(200, 360, 200, 30);
        mPanel.add(name);
        name.requestFocus();
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
                    }
                    case (KeyEvent.VK_UP) -> player.selectNextWeapon();
                    case (KeyEvent.VK_DOWN) -> player.selectPrevWeapon();
                }
            }
            case GAME_OVER -> {
                if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                    scoreName = name.getText();
                    if (scoreName.isEmpty()) {
                        scoreName = "Anon";
                    }
                    mPanel.remove(name);
                    scoreState = ScoreState.SET_SCORE;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if (state == State.PLAYING) {
            switch (event.getKeyCode()) {
                case (KeyEvent.VK_LEFT) -> {
                    if (this.player.getDirection() == -1) {
                        this.player.stop();
                    }
                }
                case (KeyEvent.VK_RIGHT) -> {
                    if (this.player.getDirection() == 1) {
                        this.player.stop();
                    }
                }
                case (KeyEvent.VK_SPACE) -> {
                    player.stopShooting();
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent event) {
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
