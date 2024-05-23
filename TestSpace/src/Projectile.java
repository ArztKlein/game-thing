import java.awt.*;

public abstract class Projectile
{
    protected double x, y;
    protected double velY, velX, accelY;
    protected int damage;
    protected int radius;
    public Projectile(double x, double y, int radius, double velY, double accelY, int damage){
        this.x =x;
        this.y=y;
        this.radius = radius;
        this.velY=velY;
        this.accelY= accelY;
        this.damage= damage;
    }
   public abstract void update(double dt);

   public abstract void draw(GameEngine g);

   public double getY(){return y;}
    public int getDamage(){return this.damage;}
    public abstract boolean checkCollision(Alien enemy);
}
