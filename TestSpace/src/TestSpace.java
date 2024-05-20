import javax.swing.*;
import java.awt.*;

public class TestSpace extends GameEngine {
    private final ParticleEmitter emitter = new AlienParticleEmitter();
    Image background = loadImage("src/resources/LargeSpace.png");
    Image player = loadImage("src/resources/Spaceman.png");
    int width = 600;
    int height = 840;

    public static void main(String[] args) {
        createGame(new TestSpace(), 60);
    }

    public void init() {
        setWindowSize(width, height);
        emitter.move(300, 50);
    }
    @Override
    public void update(double dt) {
        emitter.update((float) dt);
    }

    @Override
    public void paintComponent() {
        changeBackgroundColor(black);
        clearBackground(width, height);

        drawBackground();

        drawPlayer();

        drawEmitter();
    }

    public void drawBackground() {
        saveCurrentTransform();
        scale(1.2, 1.2);
        drawImage(background, 0,0);
        restoreLastTransform();
    }

    public void drawPlayer() {
        saveCurrentTransform();
        scale(3,3);
        drawImage(player, 90, 200);
        restoreLastTransform();
    }

    public void drawEmitter() {
        emitter.draw(this);
    }
}
