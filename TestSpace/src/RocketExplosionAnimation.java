import java.awt.*;
import java.util.List;

public class RocketExplosionAnimation {
    private final Image collisionSprites;
    private final Image[] collisionExplosion = new Image[6];
    private boolean isActive;
    private int currentFrame;
    private final double x,y;
    private final int frameWidth, frameHeight;
    public RocketExplosionAnimation(double x, double y){
        //sprite sheet loaded
        collisionSprites = GameEngine.loadImage("TestSpace/resources/ExplosionSpriteSheet64_50.png");
        frameWidth = 20;
        frameHeight = 25;
        //split into 6 frames from 2 row, 3 columns and stored in image[]
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 2; j++){
                int index = i + j;
                collisionExplosion[index] = GameEngine.subImage(collisionSprites, (i * frameWidth), (j * frameHeight) ,
                        frameWidth, frameHeight);
            }
        }
        this.x = x;
        this.y = y;
        isActive = true;
        currentFrame = 0;
    }

    public void update(double dt) {
        if(isActive){
            currentFrame++;
            if (currentFrame >= collisionExplosion.length) {
                isActive = false;
            }
        }
    }
    public void draw(GameEngine g) {
        if(isActive){
            g.drawImage(collisionExplosion[currentFrame], x-(3*frameWidth/2), y-(3*frameHeight/2), 3*frameWidth, 3*frameHeight);
        }
    }
    public boolean isFinished(){
        return !isActive;
    }
}

