import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AlienManager {
    private List<Alien> aliens;
    private Image alienSprite;
    private Player player;
    private double elapsedTime;
    private int wave;
    private int aliensToSpawn;
    private int aliensSpawned;
    private int aliensKilled;
    private final double ALIEN_SPAWN_INTERVAL = 0.1;
    private double timeSinceLastAlienSpawn;

    public AlienManager(Image alienSprite, Player player) {
        this.alienSprite = alienSprite;
        this.aliens = new ArrayList<>();
        this.player = player;
        this.elapsedTime = 0;

        this.wave = 0;
        this.aliensToSpawn = 0;
        this.aliensSpawned = 0;
        this.aliensKilled = 0;
        this.timeSinceLastAlienSpawn = 0;
    }

    public void spawnAlien() {
        double spawnWidth = 400;
        double offset = (TestSpace.WIDTH - spawnWidth) / 2;
        double spawnX = offset + (Math.random() * spawnWidth); // Spawn randomly across the top width of the screen
        Alien alien;

        // Modified to spawn different types of aliens based on the wave
        if (wave >= 5 && Math.random() < 0.1) {
            alien = new LargeAlien(spawnX, 0, alienSprite);
        } else if (wave >= 3 && Math.random() < 0.3) {
            alien = new MediumAlien(spawnX, 0, alienSprite);
        } else {
            alien = new Alien(spawnX, 0, alienSprite);
        }
        aliens.add(alien);
        aliensSpawned++;
    }

    public void update(double dt) {
        elapsedTime += dt;
        timeSinceLastAlienSpawn += dt;

        // Check if there are aliens left to spawn and if it's time to spawn a new one
        if (timeSinceLastAlienSpawn >= ALIEN_SPAWN_INTERVAL && aliensSpawned < aliensToSpawn) {
            spawnAlien();
            timeSinceLastAlienSpawn = 0;
        }

        Iterator<Alien> iterator = aliens.iterator();
        while (iterator.hasNext()) {
            Alien alien = iterator.next();
            alien.update(dt, player);
            if (alien.checkCollision(player) <= alien.radius || alien.hasReachedPlayerHeight(player.getY())) {
                if (alien.checkCollision(player) <= alien.radius) {
                    player.reduceHealth(5); // Reduce player's health on collision
                }
                iterator.remove();
                aliensKilled++;
            }
        }

        // If this wave is over (no aliens left), start the new wave.
        if (aliens.size() == 0 && aliensSpawned == aliensToSpawn) { // Modified condition
            wave++;
            aliensToSpawn = (int) (50 + 50 * wave * 0.3f); // Calculate number of aliens for new wave
            aliensSpawned = 0;
            aliensKilled = 0;
        }
    }

    public List<Alien> getAliens() {
        return this.aliens;
    }

    public void draw(GameEngine g) {
        for (Alien alien : aliens) {
            alien.draw(g.mGraphics);
        }
    }
}
