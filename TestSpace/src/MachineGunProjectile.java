import java.awt.*;
import java.awt.image.ImageObserver;

public class MachineGunProjectile extends Projectile{
    private final Image sprite;
    public MachineGunProjectile(double x, double y){
        super(x, y, -750, 38, 1);
        this.sprite = GameEngine.loadImage("TestSpace/resources/bullet.png");
    }
    @Override
    public void update(double dt){
        velY += accelY * dt * (15 * Math.random()); //change the velocity based on the acceleration amount over time(dt)
        y += velY * dt; //change the position based on the velocity over time
    }
    @Override
    public void draw(Graphics2D g) {
        g.drawImage(sprite,(int)x,(int)y, (int) (sprite.getWidth(null) *0.5),(int) (sprite.getHeight(null) *0.5),null);
    }
}
