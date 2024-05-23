import java.awt.*;
import java.awt.image.ImageObserver;

public class MachineGunProjectile extends Projectile{
    private final Image sprite;
    public MachineGunProjectile(double x, double y){
        super(x, y, 0,-400, 1, 1);
        this.sprite = GameEngine.loadImage("TestSpace/resources/bullet.png");
        this.radius = sprite.getWidth(null)/2;
    }
    @Override
    public void update(double dt){
        velY += accelY * dt * (15 * Math.random()); //change the velocity based on the acceleration amount over time(dt)
        y += velY * dt; //change the position based on the velocity over time
    }
    @Override
    public void draw(GameEngine g) {
        g.drawImage(sprite,(int)x,(int)y, (int) (sprite.getWidth(null) ),(int) (sprite.getHeight(null) ));
    }

    @Override
    public boolean checkCollision(Alien enemy) {
        double dx = x - enemy.getX();
        double dy = y - enemy.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < radius + enemy.getRadius();
    }
}
