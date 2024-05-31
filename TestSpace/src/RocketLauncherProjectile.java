import java.awt.*;
import java.util.List;
import java.util.ArrayList;
public class RocketLauncherProjectile extends Projectile{
    private final Image projectileSprite;

    public RocketLauncherProjectile(double x, double y){
        super(x, y);
        this.projectileSprite = GameEngine.loadImage("TestSpace/resources/bullet.png"); //doesnt exist yet
        radius = projectileSprite.getWidth(null);
        velY = -10;
        accelY = 200;
        damage = 3;

    }

    @Override
    public void update(double dt) {
        velY -= accelY * dt * (8 * Math.random()+4); //change the velocity based on the acceleration amount over time(dt)
        y += velY * dt; //change the position based on the velocity over time

    }

    @Override
    public void draw(GameEngine g) {
        g.drawImage(projectileSprite,(int)x -10,(int)y-10, (int) (projectileSprite.getWidth(null) ),(int) (projectileSprite.getHeight(null) ));
    }

    @Override
    public boolean isFinished() {
        return y < 0;
    }

    @Override
    public boolean checkCollision(AlienManager alienManager){
        List<Alien> deadAliens = new ArrayList<>();

        for (Alien alien : alienManager.getAliens()) {
            if (getDistance(alien) <= radius) {
                alien.playHitSound();
                RocketExplosionAnimation newExplosion = new RocketExplosionAnimation(alien.getX(), alien.getY());
                //Explosion Sound and Image
                alien.setHitpoints(damage);
                if (alien.getHitpoints() == 0) {
                    deadAliens.add(alien);
                }
                for (Alien nearbyAlien : alienManager.getAliens()) {
                    if (getDistance(nearbyAlien) <= 3 * radius) {
                        nearbyAlien.setHitpoints(damage);
                        if (nearbyAlien.getHitpoints() == 0) {
                            deadAliens.add(nearbyAlien);
                        }
                    }
                }
            }
        }
        if (!deadAliens.isEmpty()) {
            alienManager.getAliens().removeAll(deadAliens);
            Score.score += deadAliens.size();
            Player.getAmmo();
            return true;
        }
        return false;
    }
}
