import java.awt.*;
import java.util.List;
import java.util.ArrayList;
public class RocketLauncherProjectile extends Projectile{
    private final Image projectileSprite;

    public RocketLauncherProjectile(double x, double y){
        super(x, y);
        this.projectileSprite = GameEngine.loadImage("TestSpace/resources/bullet.png");
        radius = projectileSprite.getWidth(null)/2;
        velY = -200;
        accelY = 100;
        damage = 5;
    }
    @Override
    public void update(double dt) {
        velY -= accelY * dt * (8 * Math.random()+4); //change the velocity based on the acceleration amount over time(dt)
        y += velY * dt; //change the position based on the velocity over time
    }
    @Override
    public void draw(GameEngine g) {
        g.drawImage(projectileSprite,x -radius,y-radius, projectileSprite.getWidth(null) ,projectileSprite.getHeight(null));
    }
    @Override
    public boolean isFinished() {
        return y < 0;
    }
    @Override
    public boolean checkCollision(AlienManager alienManager){
        List<Alien> deadAliens = new ArrayList<>();

        for (Alien alien : alienManager.getAliens()) {
            if (getDistance(alien) <= radius+6) {
                alien.playHitSound();
                //explosion animation occurs on impact
                RocketExplosionAnimation newExplosion = new RocketExplosionAnimation(alien.getX(), alien.getY());
                alien.takeDamage(damage);
                if (alien.getHitpoints() == 0) {
                    deadAliens.add(alien);
                }
                for (Alien nearbyAlien : alienManager.getAliens()) {
                    //check a radius around the projectile and damage them too
                    if (getDistance(nearbyAlien) <= 6 * radius) {
                        nearbyAlien.takeDamage(damage);
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
