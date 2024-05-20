import java.awt.*;

public class Bullet
{
    private double x, y;
    private double speed;
    private Image sprite;

    public Bullet(double x, double y, Image sprite){
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        speed = 1000;

    }
    public void updateBullet(double dt){
        this.y -= speed * dt;

    }
    public boolean onScreen(){
        if(y<150){
            return false;
        }
        return true;
    }
    public double getX(){return this.x;}
    public double getY(){return this.y;}
}
