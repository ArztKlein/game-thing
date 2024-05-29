import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class MachineGunProjectile extends Projectile{
    private final Image sprite;
    public MachineGunProjectile(double x, double y){
        super(x, y);
        sprite = GameEngine.loadImage("TestSpace/resources/bullet.png");
        radius = sprite.getWidth(null)/2;
        velY = -400;
        accelY = 100;
        damage = 1;
    }
    @Override
    public void update(double dt){
        velY -= accelY * dt * (8 * Math.random()+4); //change the velocity based on the acceleration amount over time(dt)
        y += velY * dt; //change the position based on the velocity over time
    }
    @Override
    public void draw(GameEngine g) {
        g.drawImage(sprite,(int)x,(int)y, (double) sprite.getWidth(null) /2 , (double) sprite.getHeight(null) /2);
    }

    @Override
    public boolean checkCollision(AlienManager alienManager){
        List<Alien> deadAliens = new ArrayList<>();

        for (Alien alien : alienManager.getAliens()) {
            if (getDistance(alien) < radius) {
                alien.setHitpoints(damage);
                if (alien.getHitpoints() == 0) {
                    deadAliens.add(alien);
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
