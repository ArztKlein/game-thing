import java.awt.*;

public class FireParticle extends Particle {
    private float red, green, blue;

    @Override
    public void draw(GameEngine ge) {
        if(notVisible()){
            return;
        }
        ge.saveCurrentTransform();

        ge.translate(x, y);
        ge.scale(scale*2, scale*1.5);
        float angle = (float)Math.atan2(vy,vx);
        ge.rotate((180*angle)/Math.PI+90);
        ge.changeColour(new Color(red, green, blue, alpha));
        ge.mGraphics.fillOval(-15, -30, 30, 60);

        ge.restoreLastTransform();

    }

    float smoothStep(float x) {
        if (x < 0) {x = 0;}
        if (x > 1f) {x = 1;}
        return x * x *  (3.0f - 2.0f * x);
    }

    @Override
    public void update(float dt){
        super.update(dt);
        scale = 0.1f+0.10f*(float)Math.log(time*100f);
        alpha = 1f - smoothStep(time*1.4f);
        red = 1.0f;
        green = (float)Math.exp(-time * 4.5f);
        blue = (float)Math.exp(-time * 10f);
    }
}
