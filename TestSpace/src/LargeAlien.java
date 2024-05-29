import java.awt.*;

public class LargeAlien extends Alien {
    private static final int LARGE_ALIEN_HEALTH = 5; // health of a Large alien
    private static final double LARGE_ALIEN_DROP_SPEED = 30;
    private static final double LARGE_ALIEN_CHASE_SPEED = 50;
    private static final double SCALE_FACTOR = 3.0;

    public LargeAlien(double x, double y, Image sprite) {
        super(x, y, sprite);
        this.hitpoints = LARGE_ALIEN_HEALTH;
        this.speedY = LARGE_ALIEN_DROP_SPEED;
        this.radius = (int) (radius * SCALE_FACTOR); // adjust radius for collision detection
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
        int width = sprite.getWidth(null);
        int height = sprite.getHeight(null);
        g.drawImage(sprite, (int) x, (int) y, (int) (width * SCALE_FACTOR), (int) (height * SCALE_FACTOR), null);
    }
}