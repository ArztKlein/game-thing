import java.awt.*;
import java.awt.image.ImageObserver;

public class MachineGunProjectile extends Projectile{
    private final Image sprite;
    public MachineGunProjectile(double x, double y){
        super(x, y, 750, 1);
        this.sprite = GameEngine.loadImage("TestSpace/resources/bullet.png");
    }
    @Override
    public void update(double dt){
        y -= speed * dt;
    }
    @Override
    public void draw(Graphics2D g) {
    g.drawImage(sprite,(int)x,(int)y,null);
    }
}
