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
    private GameEngine gameEngine;
    public Alien(double x, double y, Image sprite, GameEngine g) {
        gameEngine = g;
        this.x = x;
        this.y = y;
        this.hitpoints = 3;
        this.sprite = sprite;
        this.radius = sprite.getWidth(null);
        this.speedY = DROP_SPEED;
        this.laneOffsetX = Math.random() * 60 - 30; // Random offset to create a tighter group
    }
    public int getHitpoints(){return this.hitpoints;}

    public void setHitpoints(int damage){
        if(hitpoints-damage>0){
            hitpoints =0;
        }
        else{
            hitpoints -= damage;
        }

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
            double targetX = player.getX();
            double targetY = player.getY();

            if (x < targetX) {
                x += CHASE_SPEED * dt;
            } else if (x > targetX) {
                x -= CHASE_SPEED * dt;
            }

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

    public double checkCollision(Player player) {
        double dx = x - player.getX(); //distance between the center of the circle x and the center circle x of enemy
        double dy = y - player.getY(); //distance between the center of the circle y and the center circle y of enemy
        return Math.sqrt(dx * dx + dy * dy); //get the magnitude(length from us to enemy)
    }

    public boolean hasReachedPlayerHeight(double playerY) {
        return y >= playerY;
    }
    public void playHitSound(){
        GameEngine.AudioClip hit =  gameEngine.loadAudio("TestSpace/resources/sfx-enemyhit.wav");
        gameEngine.playAudio(hit);
    }
}