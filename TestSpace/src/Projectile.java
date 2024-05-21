import java.awt.*;

public abstract class Projectile
{
    protected double x, y;
    protected double velY, accelY;
    protected int damage;
    public Projectile(double x, double y, double velY, double accelY, int damage){
        this.x =x;
        this.y=y;
        this.velY=velY;
        this.accelY= accelY;
        this.damage= damage;
    }
   public abstract void update(double dt);

   public abstract void draw(Graphics2D g);

   public double getY(){return y;}
}
