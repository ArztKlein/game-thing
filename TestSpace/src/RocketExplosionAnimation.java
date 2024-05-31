import java.awt.*;

public class RocketExplosionAnimation {
    private final Image collisionSprites;
    private final Image[] collisionExplosion = new Image[6];
    private boolean isActive;
    private int currentFrame;
    private final double x,y;
    private final int frameWidth, frameHeight;

    public RocketExplosionAnimation(double x, double y){
        frameWidth = 20;
        frameHeight = 25;
        //sprite sheet loaded
        collisionSprites = GameEngine.loadImage("TestSpace/resources/ExplosionSpriteSheet64_50.png");
        //split into 6 frames from 2 row, 3 columns and stored in image[]
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 2; j++){
                int index = i*2 + j;
                collisionExplosion[index] = GameEngine.subImage(collisionSprites, (i * frameWidth), (j * frameHeight) ,
                        frameWidth, frameHeight);
            }
        }
        this.x = x;
        this.y = y;
        isActive = true;
        currentFrame = -1;
    }

    public void update(double dt) {
        //if the animation is playing, go to next frame
        if(isActive){
            currentFrame++;
            //once all frames have been shown, deactivate animation
            if (currentFrame == collisionExplosion.length) {
                isActive = false;
            }
        }
    }
    public void draw(GameEngine g) {
        if(isActive){
            g.drawImage(collisionExplosion[currentFrame], x-((double) (3 * frameWidth)), y-((double) (3 * frameHeight)), 6*frameWidth, 6*frameHeight);
        }
    }
    public boolean isFinished(){
        return !isActive;
    }
}

