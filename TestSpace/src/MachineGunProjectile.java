import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MachineGunProjectile extends Projectile{
    private final Image sprite;
    public MachineGunProjectile(double x, double y){
        super(x, y);
        sprite = GameEngine.loadImage("TestSpace/resources/bullet.png");
        radius = sprite.getWidth(null)/4;
        velY = -500; //initial speed in the vertical direction
        accelY = 120; //speed the bullet up for visual effect
        damage = 1;
    }
    @Override
    public void update(double dt){
        velY -= accelY * dt * (8 * Math.random()+4); //change the velocity based on the acceleration amount over time(dt)
        y += velY * dt; //change the position based on the velocity over time
    }
    @Override
    public void draw(GameEngine g) {
        g.drawImage(sprite,x-radius,y-radius, (double) sprite.getWidth(null) /2 , (double) sprite.getHeight(null) /2);
    }
    @Override
    public boolean checkCollision(AlienManager alienManager){
        List<Alien> deadAliens = new ArrayList<>();
        //check the current projectile against all aliens
        for (Alien alien : alienManager.getAliens()) {
            //circle to circle collision
            if (getDistance(alien) <= (radius+alien.getRadius())) {
                alien.playHitSound();
                alien.takeDamage(damage);
                if (alien.getHitpoints() == 0) {
                    //if the alien dies, add it to a list for later deletion
                    deadAliens.add(alien);
                }
                break;
            }
        }
        //if there are aliens in the list, delete them
        if (!deadAliens.isEmpty()) {
            alienManager.getAliens().removeAll(deadAliens);
            //increase player score, and a chance at an ammo drop
            Score.score += deadAliens.size();
            Player.getAmmo();
            return true;
        }
        return false;
    }
    //this is the y value of the point where each bullet dies
    @Override
    public boolean isFinished() {
        return y < 250;
    }
}
