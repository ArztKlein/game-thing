import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BulletManager {
    private List<Projectile> projectiles;
    private List<RocketExplosionAnimation> explosions;

    public BulletManager(){
        projectiles = new ArrayList<>();
        explosions = new ArrayList<>();
    }
    public void addBullet(Projectile projectile){
        projectiles.add(projectile);
    }
    public void updateBullets(double dt, AlienManager alienManager){
        Iterator<Projectile> bulletIterator = projectiles.iterator();
        //for each bullet check if it's colliding with an enemy
        while(bulletIterator.hasNext()) {
            Projectile projectile = bulletIterator.next();
            //move the projectile
            projectile.update(dt);

            if (projectile.checkCollision(alienManager)) {
                if (!(projectile instanceof FlamethrowerProjectile)) {
                    bulletIterator.remove();
                }
                if (projectile instanceof RocketLauncherProjectile) {
                    //if there is a collision and the projectile is a rocket, create an explosion animation
                    RocketExplosionAnimation explosion = new RocketExplosionAnimation(projectile.getX(), projectile.getY());
                    explosions.add(explosion);
                }
            //if there is not collision but the bullet reaches it max travel length, remove it
            } else if (projectile.isFinished()) {
                bulletIterator.remove();
            }
        }
        //if there are explosions to render do so.
        if(!explosions.isEmpty()){
            // Update the explosions
            Iterator<RocketExplosionAnimation> explosionIterator = explosions.iterator();
            while (explosionIterator.hasNext()) {
                RocketExplosionAnimation explosion = explosionIterator.next();
                //update each explosion to tick through the frames
                explosion.update(dt);
                //if the explosion is finished, remove the explosion
                if (explosion.isFinished()) {
                    explosionIterator.remove();
                }
            }
        }

    }
    public void drawBullets(GameEngine g){
        for(Projectile b: projectiles){
            b.draw(g);
        }
        for(RocketExplosionAnimation boom : explosions){
            boom.draw(g);
        }
    }
}
