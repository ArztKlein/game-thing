import java.awt.event.KeyEvent;

public class MainMenu {

    private static final int playButtonY = 200;
    private static final int scoreButtonY = 280;
    private static final int helpButtonY = 360;
    private static final int quitButtonY = 440;
    private static final int BUTTONS_AMOUNT = 4;

    private final String MAIN_MENU = "main menu";
    private final String SCORE_MENU = "score menu";
    private final String HELP_MENU = "help menu";
    private String state = MAIN_MENU;

    private int cursorIndex = 0;
    private static final int CURSOR_X_CENTRE = 120;
    private int cursorY = 0;
    private double cursorX = CURSOR_X_CENTRE;
    private double time = 0;

    private final int BUTTON_SIZE = 40;

    private final TestSpace game;

    public MainMenu(TestSpace game) {
        calcCursorY();
        this.game = game;
    }

    public void draw() {
        // Draw title
        game.changeColour(game.white);
        game.drawCentredText("Space Game", TestSpace.WIDTH / 2, 80, 50);

        // Draw buttons
        switch (state) {
            case MAIN_MENU -> drawMainMenu();
            case SCORE_MENU -> drawScoreMenu();
            case HELP_MENU -> drawHelpMenu();
        }
    }

    private void drawMainMenu() {
        float multiplier = (cursorIndex == 0 ) ? 1.2f : 1; // Make the selected button appear bigger
        game.drawCentredText("Play", TestSpace.WIDTH / 2, playButtonY, (int) (BUTTON_SIZE * multiplier));
        multiplier = (cursorIndex == 1 ) ? 1.2f : 1;
        game.drawCentredText("High Scores", TestSpace.WIDTH / 2, scoreButtonY, (int) (BUTTON_SIZE * multiplier));
        multiplier = (cursorIndex == 2 ) ? 1.2f : 1;
        game.drawCentredText("Help", TestSpace.WIDTH / 2, helpButtonY, (int) (BUTTON_SIZE * multiplier));
        multiplier = (cursorIndex == 3 ) ? 1.2f : 1;
        game.drawCentredText("Quit", TestSpace.WIDTH / 2, quitButtonY, (int) (BUTTON_SIZE * multiplier));

        // Draw cursor
        drawCursor();
    }

    private void drawHelpMenu() {
        game.drawCentredText("Press horizontal arrow keys to move the player.", TestSpace.WIDTH / 2, playButtonY, 25);
        game.drawCentredText("Press vertical arrow keys to change weapons.", TestSpace.WIDTH / 2, playButtonY + 40, 25);
        game.drawCentredText("Press space to use weapons.", TestSpace.WIDTH / 2, playButtonY + 80, 25);
        game.drawCentredText("TBA", TestSpace.WIDTH / 2, playButtonY + 120, 25);

        game.drawCentredText("Press enter to exit", TestSpace.WIDTH / 2, quitButtonY + 100, 35);
    }

    private void drawScoreMenu() {
        int count = Score.count;
        if (count > 10) {
            count = 10;
        }
        for (int i = 0; i < count; i++) {
            game.drawText(200, 200+(50*i), i+1 + ") ", 25);
            game.drawText(250, 200+(50*i) , Score.getHighScore(i) + " : " + Score.getName(i), 25);
        }
    }

    public void update(double dt) {
        time += dt;
        cursorX = CURSOR_X_CENTRE + Math.sin(time * 5) * 5;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: // Move cursor up
                cursorIndex = cursorIndex - 1;
                if (cursorIndex == -1) cursorIndex = BUTTONS_AMOUNT - 1;
                break;
            case KeyEvent.VK_DOWN: // Move cursor down
                cursorIndex = (cursorIndex + 1) % BUTTONS_AMOUNT;
                break;
            case KeyEvent.VK_ENTER: // Button pressed
            case KeyEvent.VK_RIGHT:
                pressMenuButton();
                break;
        }

        calcCursorY();
    }

    public void pressMenuButton() {
        if (state.equals(MAIN_MENU)) {
            switch (cursorIndex) {
                case 0: // Play
                    game.startGame();
                    break;
                case 1: // High Scores
                    state = SCORE_MENU;
                    break;
                case 2: // Help
                    state = HELP_MENU;
                    break;
                case 3: // Quit game
                    System.exit(0);
                    break;
            }
        } else { // Exit the menu.
            state = MAIN_MENU;
        }
    }

    private void calcCursorY() {
        switch (cursorIndex) {
            case (0) -> cursorY = playButtonY;
            case (1) -> cursorY = scoreButtonY;
            case (2) -> cursorY = helpButtonY;
            case (3) -> cursorY = quitButtonY;
        }
    }

    public void drawCursor() {
        game.drawCentredText(">", (int) cursorX, cursorY,40);
    }
}
