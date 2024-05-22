public class FireParticleEmitter extends ParticleEmitter{
    FireParticleEmitter() {
        super();
    }
    @Override
    Particle newParticle() {
        return new FireParticle();
    }
}
