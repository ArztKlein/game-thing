import java.awt.*;

public class AlienParticle extends Particle {
private final Image image;

private float angle;
private float rotationSpeed;
AlienParticle(Image i) {
    image = i;
}

    @Override
    void init(float startX, float startY, float emitterAngle) {
    super.init(startX, startY, emitterAngle);
    angle=(float)Math.random()*360f;
    }

    @Override
    public void draw(GameEngine ge) {
        if(notVisible()){
            return;
        }
        ge.saveCurrentTransform();
        ge.translate(x, y-20);
        ge.scale(scale*1.5, scale*1.5);
        ge.drawImage(image, 0, 0);
        ge.mGraphics.setComposite(
                AlphaComposite.SrcOver);
        ge.restoreLastTransform();

    }
    @Override
    public void update(float dt){
        super.update(dt);
        scale = 0.1f+0.10f*(float)Math.log(time*100f);

    }
}
