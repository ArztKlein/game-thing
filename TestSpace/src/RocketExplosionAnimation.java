import java.awt.*;

public class RocketExplosionAnimation extends GameEngine{
    private final Image collisionSprites;
    private final Image[] collisionExplosion;
    private boolean isActive;
    private int i;
    public RocketExplosionAnimation(int x, int y){
        collisionSprites = loadImage("TestSpace/resources/ExplosionSpriteSheet64_50.png");
        collisionExplosion = new Image[6];
        splitSprite();
        isActive = true;
    }
    @Override
    public void update(double dt) {

    }

    @Override
    public void paintComponent() {

        for(int i = 0; i < collisionExplosion.length; i++){

        }
    }
    public void splitSprite(){
        for (int i = 0; i < 2; i++) {
            for(int j = 0; j < 3; j++){
                int index = i + j;
                if(index <= collisionExplosion.length){
                    collisionExplosion[index] = GameEngine.subImage(collisionSprites, (i * collisionSprites.getWidth(null)/3), j * (collisionSprites.getHeight(null)/2) , collisionSprites.getWidth(null)/3, collisionSprites.getHeight(null)/2);
                }
            }
        }
    }


}

