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
        projectiles.add(projectile);}

    public void updateBullets(double dt, AlienManager alienManager){
        Iterator<Projectile> bulletIterator = projectiles.iterator();

        while(bulletIterator.hasNext()) {
            Projectile projectile = bulletIterator.next();
            projectile.update(dt);

            boolean collisionDetected = false;
            Iterator<Alien> alienIterator = alienManager.getAliens().iterator();

            while (alienIterator.hasNext()) {
                Alien alien = alienIterator.next();
                if (projectile.checkCollision(alien)){
                    alienIterator.remove();
                    collisionDetected = true;
                    break;
                }
            }
            if (collisionDetected || projectile.getY() < 250) {
                bulletIterator.remove();
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
