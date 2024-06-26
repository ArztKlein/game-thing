import java.awt.*;

public class Alien {
    protected double x, y;
    protected int radius;
    protected int hitpoints;
    protected double speedY;
    protected boolean chasingPlayer = false;
    protected static final double TARGET_Y = 500;
    protected static final double DROP_SPEED = 50; // Adjust to control the drop speed of aliens
    protected static final double CHASE_SPEED = 80; // Adjust to control the speed at which aliens follow the player
    protected double laneOffsetX; // Offset to keep the formation
    private GameEngine gameEngine;
    private final Image[] aSprites;
    private Image aSprite;
    private int flowCount = 0;
    boolean forward = true;

    public Alien(double x, double y, Image[] sprite, GameEngine g) {
        gameEngine = g;
        this.x = x;
        this.y = y;
        setHitpoints(2);
        this.aSprites = sprite;
        this.speedY = DROP_SPEED;
        this.laneOffsetX = Math.random() * 60 - 30; // Random offset to create a tighter group

        this.radius = aSprites[0].getWidth(null)/2;
    }
    public int getHitpoints(){return this.hitpoints;}
    public void setHitpoints(int hp){ this.hitpoints = hp;}
    public void takeDamage(int damage){
        System.out.println(hitpoints);
        hitpoints -= damage;
        if(hitpoints<=0){
            System.out.println(hitpoints);
            hitpoints =0;
        }
        System.out.println(hitpoints);
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

        if (flowCount < 7) {
            if (flowCount == 0) {
                forward = true;
            }
            if (forward) {
                flowCount++;
            } else {
                flowCount--;
            }
            aSprite = aSprites[0];
        } else if(flowCount <= 14) {
            if (forward) {
                flowCount++;
            } else {
                flowCount--;
            }
            aSprite = aSprites[1];
        } else {
            if (forward) {
                flowCount++;
            } else {
                flowCount--;
            }
            aSprite = aSprites[2];
            if (flowCount == 21) {
                forward = false;
            }
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(aSprite, (int)(x-radius), (int)(y-radius), null);
    }

    public double checkCollision(Player player) {
        double dx = x - player.getX(); //distance between the centre of the circle x and the centre circle x of enemy
        double dy = y - player.getY(); //distance between the centre of the circle y and the centre circle y of enemy
        return Math.sqrt(dx * dx + dy * dy); //get the magnitude(length from us to enemy)
    }

    public boolean hasReachedPlayerHeight(double playerY) {
        return y >= playerY;
    }
    public void playHitSound(){
        GameEngine.AudioClip hit =  gameEngine.loadAudio("TestSpace/resources/EnemyHit.wav");
        gameEngine.playAudio(hit);
    }
}