import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AlienManager {
    private List<Alien> aliens;
    private Image alienSprite;
    private Player player;

    public AlienManager(Image alienSprite, Player player) {
        this.alienSprite = alienSprite;
        this.aliens = new ArrayList<>();
        this.player = player;
    }

    public void spawnAlien() {
        double spawnWidth = 400;
        double offset = (TestSpace.WIDTH -spawnWidth)/2;
        double spawnX =  offset + (Math.random() * spawnWidth); // Spawn randomly across the top width of the screen
        Alien alien = new Alien(spawnX, 0, alienSprite);
        aliens.add(alien);
    }
    public void update(double dt) {
        Iterator<Alien> iterator = aliens.iterator();
        while (iterator.hasNext()) {
            Alien alien = iterator.next();
            alien.update(dt, player);

            // Check collision with player
            if (alien.checkCollision(player)) {
                player.reduceHealth(10); // Reduce player's health
                iterator.remove();
            }

            // Check if the alien has reached the player's height
            if (alien.hasReachedPlayerHeight(player.getY())) {
                iterator.remove();
            }
        }
    }

    public List<Alien> getAliens(){return this.aliens;}
    public void draw(Graphics2D g) {
        for (Alien alien : aliens) {
            alien.draw(g);
        }
    }
}