import java.awt.*;

public class Alien {
    protected double x, y;
    protected int radius;
    protected int hitpoints;
    protected double speedY;
    protected Image sprite;
    protected boolean chasingPlayer = false;
    protected static final double TARGET_Y = 375;
    protected static final double DROP_SPEED = 35; // Adjust to control the drop speed of aliens
    protected static final double CHASE_SPEED = 80; // Adjust to control the speed at which aliens follow the player
    protected double laneOffsetX; // Offset to keep the formation

    public Alien(double x, double y, Image sprite) {
        this.x = x;
        this.y = y;
        this.hitpoints = 3;
        this.radius = 15; //sprite.width(null)/2
        this.sprite = sprite;
        this.speedY = DROP_SPEED;
        this.laneOffsetX = Math.random() * 60 - 30; // Random offset to create a tighter group
    }
    public int getHitpoints(){return this.hitpoints;}
    public boolean setHitpoints(int damage){
        int alienHP = getHitpoints();
        alienHP -= damage;
        if(alienHP<=0){return false;}
        this.hitpoints = alienHP;
        return true;
    }
    public double getX(){return this.x;}
    public double getY(){return this.y;}
    public int getRadius(){return this.radius;}
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
