import java.awt.*;
import java.util.Iterator;

public abstract class Projectile
{
    protected double x, y;
    protected double velY, velX, accelY;
    protected int damage;
    protected int radius;
    public Projectile(double x, double y){
        this.x = x;
        this.y = y;
        this.radius = 0;
        this.velY = 0;
        this.accelY = 0;
        this.damage = 0;
    }
    public abstract void update(double dt);

    public abstract void draw(GameEngine g);

    public abstract boolean checkCollision(AlienManager alienManager);

    public void setRadius(int radius){
        this.radius = radius;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public void setAccelY(double accelY) {
        this.accelY = accelY;
    }

    public double getX() {
        return x;
    }

    public double getY(){
        return y;
    }
    public int getDamage(){return this.damage;}

    public double getVelX() {
        return velX;
    }

    public double getAccelY() {
        return accelY;
    }

    public double getVelY() {
        return velY;
    }

    public int getRadius() {
        return radius;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    public double getDistance(Alien alien){
        double dx = x - alien.getX();
        double dy = y - alien.getY();
        return Math.sqrt(dx * dx + dy * dy);}
}
