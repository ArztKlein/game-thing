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
                    if(!alien.setHitpoints(projectile.getDamage()))      //This is what when a collision occurs
                    {
                        alienIterator.remove();                     //delete the alien
                        Score.score++;                              //Raises the players score when an alien is killed
                        Player.getAmmo();                      // Checks to see if the alien drops ammo for the player

                    }
                    collisionDetected = true;                       //set flag to delete bullet
                    break;                                          //break because each bullet only has one collision before being destroyed but each bullet/collision needs to be checked.
                }
            }
            if (collisionDetected || projectile.getY() < 250) {     //delete bullet
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
