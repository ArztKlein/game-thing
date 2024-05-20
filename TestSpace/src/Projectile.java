import java.awt.*;

public abstract class Projectile
{
    protected double x, y;
    protected double speed;
    protected int damage;
    public Projectile(double x, double y, double speed, int damage){
        this.x =x;
        this.y=y;
        this.speed=speed;
        this.damage= damage;
    }
   public abstract void update(double dt);

   public abstract void draw(Graphics2D g);

   public double getY(){return y;}
}
