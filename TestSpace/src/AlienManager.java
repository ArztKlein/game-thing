import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AlienManager {
    private List<Alien> aliens;
    private Image alienSprite;
    private Player player;
    private double elapsedTime;

    public AlienManager(Image alienSprite, Player player) {
        this.alienSprite = alienSprite;
        this.aliens = new ArrayList<>();
        this.player = player;
        this.elapsedTime = 0;
    }

    public void spawnAlien() {
        double spawnWidth = 400;
        double offset = (TestSpace.WIDTH - spawnWidth) / 2;
        double spawnX = offset + (Math.random() * spawnWidth); // Spawn randomly across the top width of the screen
        /*Alien alien = new Alien(spawnX, 0, alienSprite);
        aliens.add(alien);*/
        Alien alien;
        if (elapsedTime >= 50){
            alien = new LargeAlien(spawnX,0, alienSprite);
        }else if (elapsedTime >= 30){
            alien = new MediumAlien(spawnX,0, alienSprite);
        }else {
            alien = new Alien(spawnX, 0, alienSprite);
        }
        aliens.add(alien);
    }

    public void update(double dt) {
        elapsedTime += dt;
        Iterator<Alien> iterator = aliens.iterator();
        while (iterator.hasNext()) {
            Alien alien = iterator.next();
            alien.update(dt, player);
            if (alien.checkCollision(player) || alien.hasReachedPlayerHeight(player.getY())) {
                if (alien.checkCollision(player)) {
                    player.reduceHealth(5); // Reduce player's health on collision
                }
                iterator.remove();
            }
        }
    }

    public List<Alien> getAliens() {
        return this.aliens;
    }

    public void draw(Graphics2D g) {
        for (Alien alien : aliens) {
            alien.draw(g);
        }
    }
}