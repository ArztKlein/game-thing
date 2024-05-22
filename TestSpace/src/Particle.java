public abstract class Particle {
    protected float x, y;
    protected float vx, vy;
    protected float scale;
    protected float alpha;
    protected float time;
    protected float maxLifeTime = 3;

    float getInitialSpeed() {
        return 1.2f*((float) Math.random() * 80.0f + 150f);
    }

    void init(float startX, float startY, float emitterAngle) {
        x = startX;
        y = startY;
        float initialSpeed = getInitialSpeed();
        vy = initialSpeed*(float)Math.cos(emitterAngle);
        vx = initialSpeed*(float)Math.sin(emitterAngle);
        scale = (float)Math.random()*0.1f+0.2f;
        alpha = (float)Math.random()*0.1f+0.9f;
        time = 0;
    }

    boolean notVisible() {
        return (alpha <= 0.01f ||time > maxLifeTime);
    }

    public void update(float dt) {
        time+=dt;
        x+=vx*dt;
        y+=vy*dt;
    }

    public abstract void draw(GameEngine ge);
}
