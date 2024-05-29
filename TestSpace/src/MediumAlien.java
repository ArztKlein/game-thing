import java.awt.*;

public class MediumAlien extends Alien {
    private static final int MEDIUM_ALIEN_HEALTH = 4; // health of a medium alien
    private static final double MEDIUM_ALIEN_DROP_SPEED = 25;
    private static final double MEDIUM_ALIEN_CHASE_SPEED = 60;
    private static final double SCALE_FACTOR = 2.0;

    public MediumAlien(double x, double y, Image sprite, GameEngine g) {
        super(x, y, sprite, g);
        this.hitpoints = MEDIUM_ALIEN_HEALTH;
        this.speedY = MEDIUM_ALIEN_DROP_SPEED;
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
    }

    @Override
    public void draw(Graphics2D g) {
        int width = sprite.getWidth(null);
        int height = sprite.getHeight(null);
        g.drawImage(sprite, (int) x, (int) y, (int) (width * SCALE_FACTOR), (int) (height * SCALE_FACTOR), null);
    }
}