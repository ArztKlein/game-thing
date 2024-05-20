import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BulletManager {
    private Image bulletSprite;
    private List<Bullet> bullets;
    private int screenHeight;

    public BulletManager(Image bulletSprite, int screenHeight){
        this.bulletSprite = bulletSprite;
        this.screenHeight = screenHeight;
        bullets = new ArrayList<>();
    }
    public void addBullet(double x, double y){
        bullets.add(new Bullet(x, y, bulletSprite));
    }
    public void updateBullets(double dt){
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while(bulletIterator.hasNext()){
            Bullet bullet = bulletIterator.next();
            bullet.updateBullet(dt);
            if(!bullet.onScreen()){
                bulletIterator.remove();
            }
        }
    }
    public void drawBullets(Graphics2D g){
        for(Bullet b: bullets){
            g.drawImage(bulletSprite, (int)b.getX(), (int)b.getY(), null);
        }
    }
}
