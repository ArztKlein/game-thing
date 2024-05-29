import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

public class BulletManager {
    private List<Projectile> projectiles;

    public BulletManager(){
        projectiles = new ArrayList<>();
    }

    public void addBullet(Projectile projectile){
        projectiles.add(projectile);
    }

    public void updateBullets(double dt, AlienManager alienManager){
        Iterator<Projectile> bulletIterator = projectiles.iterator();

        while(bulletIterator.hasNext()) {
            Projectile projectile = bulletIterator.next();
            projectile.update(dt);

            if (projectile instanceof RocketLauncherProjectile) {
                //rocket collisions
                if (projectile.checkCollision(alienManager)) {
                     bulletIterator.remove();
                } else if (projectile.getY() < 0) {
                     bulletIterator.remove();
                }
            }
            if (projectile instanceof MachineGunProjectile) {
                //machine gun collisions
                if (projectile.checkCollision(alienManager)) {
                    bulletIterator.remove();
                } else if (projectile.getY() < 250) {
                    bulletIterator.remove();
                }
            }
            if (projectile instanceof FlamethrowerProjectile) {
                if (projectile.checkCollision(alienManager)) {
                    bulletIterator.remove();
                } else if (projectile.getY() < 250) {
                    bulletIterator.remove();
                }
            }
        }
    }
    public List<Projectile> getProjectiles(){return this.projectiles;}

    public void drawBullets(GameEngine g){
        for(Projectile b: projectiles){
            b.draw(g);
        }
    }
}
