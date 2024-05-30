import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

public class BulletManager {
    private List<Projectile> projectiles;
    private List<RocketExplosionAnimation> explosions;
    public BulletManager(){
        projectiles = new ArrayList<>();
        explosions = new ArrayList<>();
    }

    public void addBullet(Projectile projectile){
        //can i make an explosion here for the rocket instance since every rocket bullet needs an explosion
        projectiles.add(projectile);
    }
    public void updateBullets(double dt, AlienManager alienManager){
        Iterator<Projectile> bulletIterator = projectiles.iterator();

        while(bulletIterator.hasNext()) {
            Projectile projectile = bulletIterator.next();
            projectile.update(dt);

            if (projectile.checkCollision(alienManager)) {
                if (!(projectile instanceof FlamethrowerProjectile)) {
                    bulletIterator.remove();
                }
                if (projectile instanceof RocketLauncherProjectile) {
                    RocketExplosionAnimation explosion = new RocketExplosionAnimation(projectile.getX(), projectile.getY());
                    explosions.add(explosion);
                }

            } else if (projectile.isFinished()) {
                bulletIterator.remove();
            }
        }
        if(!explosions.isEmpty()){
            // Update the explosions
            Iterator<RocketExplosionAnimation> explosionIterator = explosions.iterator();
            while (explosionIterator.hasNext()) {
                RocketExplosionAnimation explosion = explosionIterator.next();
                explosion.update(dt);
                if (explosion.isFinished()) {
                    explosionIterator.remove();
                }
            }
        }

    }
    public List<Projectile> getProjectiles(){return this.projectiles;}

    public void drawBullets(GameEngine g){
        for(Projectile b: projectiles){
            b.draw(g);
        }
        for(RocketExplosionAnimation boom : explosions){
            boom.draw(g);
        }
    }
}
