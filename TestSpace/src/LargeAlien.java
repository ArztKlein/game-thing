import java.awt.*;

public class LargeAlien extends Alien {
    private static final int LARGE_ALIEN_HEALTH = 7; // health of a Large alien
    private static final double LARGE_ALIEN_DROP_SPEED = 50;
    private static final double LARGE_ALIEN_CHASE_SPEED = 70;
    private static final double SCALE_FACTOR = 2.0;
    double targetY;
    double targetX;
    private Image[] laSprites = new Image[4];
    private Image sprite;
    private int blinkCount = 0;
    private int blinkGap = 0;

    public LargeAlien(double x, double y, Image[] sprite, GameEngine g) {
        super(x, y, sprite,g);
        this.laSprites = sprite;
        setHitpoints(LARGE_ALIEN_HEALTH);
        this.speedY = LARGE_ALIEN_DROP_SPEED;
        this.radius = (int) ((laSprites[0].getWidth(null) * SCALE_FACTOR)/2); // adjust radius for collision detection
    }

    @Override
    public void update(double dt, Player player) {
        targetX = player.getX() + laneOffsetX;
        targetY = player.getY();

        //Changes the aliens sprite so their eyes follow the player
        if (targetX < x - 75) {
            sprite = laSprites[2];
        } else if (targetX > x + 75) {
            sprite = laSprites[0];
        } else {
            sprite = laSprites[1];
        }
        //Sets a timer for how long between alien blinks(blinkGap) and for how long they blink(blinkCount)
        if (blinkGap == 150) {
            sprite = laSprites[3];
            if (blinkCount == 10) {
                blinkGap = 0;
                blinkCount = 0;
            }
            blinkCount++;
        } else {
            blinkGap++;
        }

        if (!chasingPlayer) {
            y += speedY * dt;
            if (y >= TARGET_Y) {
                chasingPlayer = true;
            }
        } else {

            if (x < targetX) {
                x += LARGE_ALIEN_CHASE_SPEED * dt;
            } else if (x > targetX) {
                x -= LARGE_ALIEN_CHASE_SPEED * dt;
            }

            if (y < targetY) {
                y += LARGE_ALIEN_CHASE_SPEED * dt;
            } else if (y > targetY) {
                y -= LARGE_ALIEN_CHASE_SPEED * dt;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        int width = (laSprites[1].getWidth(null));
        int height = (laSprites[1].getHeight(null));
        g.drawImage(sprite, (int) x -radius, (int) y -radius, (int) (width * SCALE_FACTOR), (int) (height * SCALE_FACTOR), null);
    }
}