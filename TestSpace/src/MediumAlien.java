import java.awt.*;

public class MediumAlien extends Alien {
    private static final int MEDIUM_ALIEN_HEALTH = 6; // health of a medium alien
    private static final double MEDIUM_ALIEN_DROP_SPEED = 50;
    private static final double MEDIUM_ALIEN_CHASE_SPEED = 70;
    private static final double SCALE_FACTOR = 1.5;
    private Image[] maSprites = new Image[4];
    private Image sprite;
    private int turnCount = 0;
    private boolean forward;


    public MediumAlien(double x, double y, Image[] sprite, GameEngine g) {
        super(x, y, sprite, g);
        this.maSprites = sprite;
        this.hitpoints = MEDIUM_ALIEN_HEALTH;
        this.speedY = MEDIUM_ALIEN_DROP_SPEED;
        this.radius = (int) (maSprites[0].getWidth(null) * SCALE_FACTOR); // adjust radius for collision detection
    }

    @Override
    public void update(double dt, Player player) {
        if (!chasingPlayer) {
            y += speedY * dt;
            if (y >= TARGET_Y) {
                chasingPlayer = true;
            }
        } else {
            double targetX = player.getX() + laneOffsetX;
            double targetY = player.getY();

            if (x < targetX) {
                x += MEDIUM_ALIEN_CHASE_SPEED * dt;
            } else if (x > targetX) {
                x -= MEDIUM_ALIEN_CHASE_SPEED * dt;
            }

            if (y < targetY) {
                y += MEDIUM_ALIEN_CHASE_SPEED * dt;
            } else if (y > targetY) {
                y -= MEDIUM_ALIEN_CHASE_SPEED * dt;
            }
        }

        if (turnCount <= 30) {
            if (turnCount == 0) {
                forward = true;
            }
            sprite = maSprites[0];
            if (forward) {
                turnCount++;
            } else {
                turnCount--;
            }
        } else if (turnCount <= 35) {
            sprite = maSprites[1];
            if (forward) {
                turnCount++;
            } else {
                turnCount--;
            }
        } else if (turnCount <= 40) {
            sprite = maSprites[2];
            if (forward) {
                turnCount++;
            } else {
                turnCount--;
            }
        } else {
            sprite = maSprites[3];
            if (forward) {
                turnCount++;
            } else {
                turnCount--;
            }
            if (turnCount == 70) {
                forward = false;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        int width = sprite.getWidth(null);
        int height = sprite.getHeight(null);
        g.drawImage(sprite, (int) x, (int) y, (int) (width * SCALE_FACTOR), (int) (height * SCALE_FACTOR), null);
    }
}