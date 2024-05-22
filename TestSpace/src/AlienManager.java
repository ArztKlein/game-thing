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
        double spawnX = Math.random() * TestSpace.WIDTH; // Spawn randomly across the top width of the screen
        Alien alien = new Alien(spawnX, 0, alienSprite);
        aliens.add(alien);
    }

    public void update(double dt) {
        Iterator<Alien> iterator = aliens.iterator();
        while (iterator.hasNext()) {
            Alien alien = iterator.next();
            alien.update(dt, player);
            if (alien.checkCollision(player) || alien.hasReachedPlayerHeight(player)) {
                iterator.remove();
            }
        }
    }

    public void draw(Graphics2D g) {
        for (Alien alien : aliens) {
            alien.draw(g);
        }
    }
}
