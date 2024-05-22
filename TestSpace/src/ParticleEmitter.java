import java.util.ArrayList;

public abstract class ParticleEmitter {
    int nParticles;
    float emitterFrequency;
    float sweepAngle;
    float sweepSpeed;
    float sweepRandom;
    ArrayList<Particle> particles;
    float emitterAngle=0;
    float startX, startY;
    float angle = 0f;
    float time = 0f;
    float lastEmitTime = 0;


    ParticleEmitter(int nParticles, int emitterFrequency, float sweepAngle, float sweepSpeed, float sweepRandom) {
        this.nParticles = nParticles;
        this.emitterFrequency = emitterFrequency;
        this.sweepAngle = sweepAngle;
        this.sweepSpeed = sweepSpeed;
        this.sweepRandom = sweepRandom;
        this.particles = new ArrayList<>(nParticles);
    }

    ParticleEmitter() {
        this(50,0,.2f, 6, 0.01f);
    }

    abstract Particle newParticle();
    public void move(float x, float y) {
        startX = x;
        startY = y;
    }
    Particle getParticle() {
        if(particles.size() < nParticles) {
            Particle p = newParticle();
            particles.add(p);
            return p;
        }
        for(Particle p : particles) {
            if(p.notVisible()) {
                return p;
            }
        }
        return null;
    }

    public void update(float dt) {
        time+=dt;
        emitterAngle = angle+
                (float)Math.sin(time*sweepSpeed)*
                        sweepAngle*(float)Math.PI+(float)Math.PI+
                (float)Math.random()*sweepRandom-
                sweepRandom/2.0f;
        if(time>lastEmitTime+1.0/emitterFrequency) {
            Particle p = getParticle();
            if(p!=null) {
                p.init(startX, startY, emitterAngle);
                lastEmitTime = time;
            }
        }
        for (Particle p : particles) {
            p.update(dt);
        }
    }

    public void draw(GameEngine ge) {
        for (Particle p : particles) {
            if (!p.notVisible()) {
                p.draw(ge);
            }
        }
    }

}
