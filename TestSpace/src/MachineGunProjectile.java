import java.awt.*;
import java.awt.image.ImageObserver;

public class MachineGunProjectile extends Projectile{
    private final Image sprite;
    public MachineGunProjectile(double x, double y){
        super(x, y);
        sprite = GameEngine.loadImage("TestSpace/resources/bullet.png");
        radius = sprite.getWidth(null)/2;
        velY = -400;
        accelY = 100;
        damage = 1;
    }
    @Override
    public void update(double dt){
        velY -= accelY * dt * (150 * Math.random()+20); //change the velocity based on the acceleration amount over time(dt)
        y += velY * dt; //change the position based on the velocity over time
    }
    @Override
    public void draw(GameEngine g) {
        g.drawImage(sprite,(int)x,(int)y, sprite.getWidth(null)*.8 ,sprite.getHeight(null)*.8);
    }

    @Override
    public double checkCollision(Alien enemy) {
        double dx = x - enemy.getX(); //distance between the center of the circle x and the center circle x of enemy
        double dy = y - enemy.getY(); //distance between the center of the circle y and the center circle y of enemy
        return Math.sqrt(dx * dx + dy * dy); //get the magnitude(length from us to enemy)
    }
}
