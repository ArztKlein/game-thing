import java.awt.*;

public class Alien {
    private double x, y;
    private double speedY;
    private Image sprite;
    private boolean chasingPlayer = false;
    private static final double TARGET_Y = 150;
    private static final double DROP_SPEED = 50; // Adjust to control the drop speed of aliens
    private static final double CHASE_SPEED = 100; // Adjust to control the speed at which aliens follow the player
    private double laneOffsetX; // Offset to keep the formation

    public Alien(double x, double y, Image sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.speedY = DROP_SPEED;
        this.laneOffsetX = Math.random() * 60 - 30; // Random offset to create a tighter group
    }

    public void update(double dt, Player player) {
        if (!chasingPlayer) {
            y += speedY * dt;
            if (y >= TARGET_Y) {
                chasingPlayer = true;
            }
        } else {
            // Move towards the player's position
            double targetX = player.getX() + laneOffsetX;
            double targetY = player.getY();

            // Move horizontally towards the player's x position
            if (x < targetX) {
                x += CHASE_SPEED * dt;
            } else if (x > targetX) {
                x -= CHASE_SPEED * dt;
            }

            // Move vertically towards the player's y position
            if (y < targetY) {
                y += CHASE_SPEED * dt;
            } else if (y > targetY) {
                y -= CHASE_SPEED * dt;
            }
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(sprite, (int)x, (int)y, null);
    }

    public boolean checkCollision(Player player) {
        Rectangle alienBounds = new Rectangle((int)x, (int)y, sprite.getWidth(null), sprite.getHeight(null));
        Rectangle playerBounds = new Rectangle((int)player.getX(), (int)player.getY(), 40, 40);
        return alienBounds.intersects(playerBounds);
    }

    public boolean hasReachedPlayerHeight(Player player) {
        return y >= player.getY();
    }
}
