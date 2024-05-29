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
        List<Alien>deadAliens = new ArrayList<>();
        Projectile projectile;

        while(bulletIterator.hasNext()) {
            projectile = bulletIterator.next();
            projectile.update(dt);
            boolean collisionDetected = false;
            Iterator<Alien> alienIterator = alienManager.getAliens().iterator();

            while(alienIterator.hasNext()) {
                if (projectile instanceof RocketLauncherProjectile) {
                    //rocket collisions
                    Alien alien = alienIterator.next();
                    if (projectile.checkCollision(alien) < projectile.getRadius()) {
                        alien.setHitpoints(projectile.getDamage());
                        if (alien.getHitpoints() == 0) {
                            deadAliens.add(alien);
                        }
                        collisionDetected = true;
                    }
                }

                if (projectile instanceof MachineGunProjectile) {
                    //machine gun collisions
                    Alien alien = alienIterator.next();
                    if (projectile.checkCollision(alien) < projectile.getRadius()) {
                        alien.setHitpoints(projectile.getDamage());
                        if (alien.getHitpoints() == 0) {
                            alienIterator.remove();
                            Score.score++;                              //Raises the players score when an alien is killed
                            Player.getAmmo();
                        }
                        collisionDetected = true;
                    }
                }
                if (projectile instanceof FlamethrowerProjectile) {
                    Alien alien = alienIterator.next();
                    if (projectile.checkCollision(alien) < projectile.getRadius()) {
                        alien.setHitpoints(projectile.getDamage());
                        if (alien.getHitpoints() == 0) {
                            alienIterator.remove();
                            Score.score++;                              //Raises the players score when an alien is killed
                            Player.getAmmo();
                        }
                        collisionDetected = true;
                    }
                }
                if (projectile instanceof RocketLauncherProjectile && collisionDetected) {
                    for (Alien nearbyAlien : alienManager.getAliens()) {
                        if (projectile.checkCollision(nearbyAlien) < 3 * projectile.getRadius()) {
                            nearbyAlien.setHitpoints(projectile.getDamage());
                            if (nearbyAlien.getHitpoints() == 0) {
                                deadAliens.add(nearbyAlien);
                            }
                        }
                    }
                }
            }
            if (projectile instanceof RocketLauncherProjectile) {
                if(collisionDetected){
                    alienManager.getAliens().removeAll(deadAliens);
                    Score.score += deadAliens.size(); // Raises the players score for all the aliens killed
                    Player.getAmmo();
                    bulletIterator.remove();
                }
                else if(projectile.getY() < 0){
                    bulletIterator.remove();
                }
            }
            else if (projectile instanceof MachineGunProjectile) {
                if(collisionDetected){
                    bulletIterator.remove();
                }
                else if(projectile.getY() < 150){
                    bulletIterator.remove();
                }
            }
            else if (projectile instanceof FlamethrowerProjectile){
                if(projectile.getY() < 30 || ((FlamethrowerProjectile) projectile).hasDisappeared()){
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
