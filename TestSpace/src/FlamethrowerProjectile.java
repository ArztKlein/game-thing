import java.awt.*;
import java.awt.image.ImageObserver;

public class FlamethrowerProjectile extends Projectile {
    private final static int SPEED = 400;
    private double scale;
    private float red, green, blue, alpha;
    private double time = 0;
    private double dx, dy;

    public FlamethrowerProjectile(double x, double y, double dx, double dy) {
        super(x, y, 10,-dy * SPEED, 1, 1);
        this.velX = dx * SPEED;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void update(double dt){
        time += dt;

        velY += accelY * dt * (15 * Math.random()); //change the velocity based on the acceleration amount over time(dt)
        y += velY * dt;
        x += velX * dt;

        scale = 0.1f+0.10f*(float)Math.log(time*100f);
        alpha = (float) (1f - smoothStep(time*1.4f));
        red = 1.0f;
        green = (float)Math.exp(-time * 4.5f);
        blue = (float)Math.exp(-time * 10f);
    }
    @Override
    public void draw(GameEngine g) {
        g.saveCurrentTransform();

//        g.translate(x, y);
//        g.scale(scale*2, scale*1.5);
//        float angle = (float)Math.atan2(dx,dy);
//        g.rotate((180*angle)/Math.PI+90);
        g.changeColour(new Color(red, green, blue, alpha));
//        g.mGraphics.fillOval(-15, -30, 15, 15);
        g.drawSolidCircle(x, y, radius);

        g.restoreLastTransform();
    }

    @Override
    public boolean checkCollision(Alien enemy) {
        double dx = x - enemy.getX();
        double dy = y - enemy.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < radius + enemy.getRadius();
    }

    double smoothStep(double x) {
        if (x < 0) {x = 0;}
        if (x > 1f) {x = 1;}
        return x * x *  (3.0f - 2.0f * x);
    }
}
