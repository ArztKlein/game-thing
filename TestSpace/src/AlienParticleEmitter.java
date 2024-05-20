import java.awt.*;

public class AlienParticleEmitter extends ParticleEmitter{
Image image;
    AlienParticleEmitter() {
        super();
        image = GameEngine.loadImage("TestSpace/src/resources/Alien.png");
    }
    @Override
    AlienParticle newParticle() {
        AlienParticle p = new AlienParticle(image);
        return p;
    }
}
