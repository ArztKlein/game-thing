import java.awt.*;

public abstract class Particle {
    protected float x, y;
    protected float vx, vy;
    protected float scale;
    protected float alpha;
    protected float time;
    protected float maxLifeTime = 1.7f;

    float getInitialSpeed() {
        return -2.0f*((float)Math.random()*80.0f+150f);
    }

    void init(float startX, float startY, float emitterAngle) {
        x = startX;
        y = startY;
        float initialSpeed = getInitialSpeed();
        vy = initialSpeed*(float)Math.cos(emitterAngle);
        vx = initialSpeed*(float)Math.sin(emitterAngle);
        scale = (float)Math.random()*0.1f+.02f;
        //alpha = (float)Math.random()*0.1f+0.9f;
        alpha = 1;
        time = 0;
    }

    boolean notVisible() {
        return (time > maxLifeTime);
    }

    public void update(float dt) {
        time+=dt;
        x+=vx*dt;
        y+=vy*dt;
    }

    public abstract void draw(GameEngine ge);
}
