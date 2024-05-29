import java.awt.*;

public class RocketLauncherProjectile extends Projectile{
    private final Image sprite;
    public RocketLauncherProjectile(double x, double y){
        super(x, y);
        this.sprite = GameEngine.loadImage("TestSpace/resources/bullet.png"); //doesnt exist yet
        radius = sprite.getWidth(null)/2;
        velY = -50;
        accelY = 700;
        damage = 3;
    }

    @Override
    public void update(double dt) {
        velY -= accelY * dt; //change the velocity based on the acceleration amount over time(dt)
        y += velY * dt; //change the position based on the velocity over time
    }

    @Override
    public void draw(GameEngine g) {
        g.drawImage(sprite,(int)x,(int)y, (int) (sprite.getWidth(null) ),(int) (sprite.getHeight(null) ));
    }

    @Override
    public double checkCollision(Alien enemy) {
        double dx = x - enemy.getX();
        double dy = y - enemy.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
