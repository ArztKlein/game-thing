import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class FlamethrowerProjectile extends Projectile {
    private final static int SPEED = 200;
    private double scale;
    private float red, green, blue, alpha;
    private double time = 0;

    public FlamethrowerProjectile(double x, double y, double dx) {
        super(x, y);
        this.velX = dx * SPEED;
        accelY = 10;
        radius = 5;
        velY = SPEED;
    }

    @Override
    public void update(double dt){

        velY += accelY * dt * (15 * Math.random()); //change the velocity based on the acceleration amount over time(dt)
        x += velX * dt;
        y -= velY * dt;
        time += dt;

        scale = 0.1f+0.10f*(float)Math.log(time*100f);
        alpha = (float) (1f - smoothStep(time*1.4f));
        red = 1.0f;
        green = (float)Math.exp(-time * 4.5f);
        blue = (float)Math.exp(-time * 10f);
    }
    @Override
    public void draw(GameEngine g) {
        g.changeColour(new Color(red, green, blue, alpha));
        g.drawSolidCircle(x, y, radius);
    }

    @Override
    public boolean checkCollision(AlienManager alienManager){
        List<Alien> deadAliens = new ArrayList<>();

        for (Alien alien : alienManager.getAliens()) {
            if (getDistance(alien) < radius) {
                alien.playHitSound();
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


    double smoothStep(double x) {
        if (x < 0) {x = 0;}
        if (x > 1f) {x = 1;}
        return x * x *  (3.0f - 2.0f * x);
    }

    @Override
    public boolean isFinished() {
        return alpha <= 0;
    }
}
