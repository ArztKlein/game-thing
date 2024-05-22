import java.awt.*;

public class RocketLauncherProjectile extends Projectile{
    private final Image sprite;
    public RocketLauncherProjectile(double x, double y){
        super(x, y, 20 ,100, 10,10);
        this.sprite = GameEngine.loadImage("TestSpace/resources/rocket.png"); //doesnt exist yet
    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void draw(GameEngine g) {
        g.drawImage(sprite,(int)x,(int)y);
    }
}
