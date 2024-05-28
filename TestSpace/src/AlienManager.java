import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AlienManager {
    private List<Alien> aliens;
    private Image alienSprite;
    private Player player;
    private int wave = 0;
    private double timeSinceLastAlienSpawn = 0; // Handle time accumulation for alien spawning
    private final double ALIEN_SPAWN_INTERVAL = 0.10; // Adjust this value to control alien spawning rate
    private int aliensToSpawn = 0;

    public AlienManager(Image alienSprite, Player player) {
        this.alienSprite = alienSprite;
        this.aliens = new ArrayList<>();
        this.player = player;
    }

    private void spawnAlien() {
        double spawnWidth = 400;
        double offset = (TestSpace.WIDTH - spawnWidth) / 2;
        double spawnX = offset + (Math.random() * spawnWidth); // Spawn randomly across the top width of the screen
        Alien alien = new Alien(spawnX, 0, alienSprite);
        aliens.add(alien);
    }

    public void update(double dt) {
        // If this wave is over (no aliens left), start the new wave.
        if (aliens.size() == 0 && aliensToSpawn == 0) {
            aliensToSpawn = (int) (50 + 50 * wave * 0.3f);
            wave++;
        }

        // Spawn aliens
        timeSinceLastAlienSpawn += dt;
        if (timeSinceLastAlienSpawn >= ALIEN_SPAWN_INTERVAL && aliensToSpawn > 0) {
            spawnAlien();
            timeSinceLastAlienSpawn -= ALIEN_SPAWN_INTERVAL;
            aliensToSpawn--;
        }

        // Check for collisions
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
