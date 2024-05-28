import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class TestSpace extends GameEngine {
    private final MainMenu mainMenu = new MainMenu(this);
    public static final int WIDTH  = 600;
    public static final int HEIGHT = 840;
    public static final BulletManager bulletManager = new BulletManager();
    private Player player;
    private AlienManager alienManager;
    public static JTextField name;
    public String scoreName;
    private final Score score = new Score();
    public boolean gotName, settingScore;
    boolean isWeapon;

    public enum State {
        MAIN_MENU,
        PLAYING,
        GAME_OVER
    }

    private State state = State.MAIN_MENU;

    Image background = loadImage("TestSpace/resources/LargeSpace.png");

    public static void main(String[] args) {
        createGame(new TestSpace(), 60);
    }

    public void init() {
        setWindowSize(WIDTH, HEIGHT);

        //init score
        Score.score = 0;
        gotName = false;
        name = new JTextField();

        isWeapon = true;
    }

    public void startGame() {
        this.player = new Player(WIDTH / 2, HEIGHT - 75, GameEngine.loadImage("TestSpace/resources/Spaceman.png"));
        state = State.PLAYING;
        alienManager = new AlienManager(loadImage("TestSpace/resources/Alien.png"), player); // Initialise the AlienManager
    }

    @Override
    public void update(double dt) {
        switch (state) {
            case MAIN_MENU:
                init();
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
                manageScore();
                if (isWeapon) {
                    Player.clearWeapons();
                    isWeapon = false;
                    state = State.MAIN_MENU;
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
                bulletManager.drawBullets(this);
                alienManager.draw(this.mGraphics);
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
        scale(1.2,1.2);
        drawImage(background, 0,0);
        restoreLastTransform();
    }

    public void drawGameOver() {
        saveCurrentTransform();
        changeColour(white);
        drawCentredText("Game Over", WIDTH / 2, 200, 80);
        drawCentredText("Score: " + Score.score, WIDTH / 2, 300, 60);
        restoreLastTransform();
    }

    public void drawScore() {
        changeColour(white);
        drawText(10,30, "Score: " + Score.score, 30);
    }

    public void manageScore() {
        if (score.checkScore()) {
            if (gotName) {
                score.updateHighScore(scoreName);
                state = State.MAIN_MENU;
            } else {
                enterName();
            }
        }
    }

    public void enterName() {
        name.setBounds(200, 360, 200, 30);
        mPanel.add(name);
        name.requestFocus();
        if (settingScore) {
            scoreName = name.getText();
            if (scoreName.isEmpty()) {
                scoreName = "Anon";
            }
            gotName = true;
            mPanel.remove(name);
        }
        settingScore = false;
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
                if(event.getKeyCode() == KeyEvent.VK_ENTER) {
                    settingScore = true;
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