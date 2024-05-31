import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

public class Player {
    private final double y;
    private double x;
    private int direction;
    private int radius;
    private int health;
    private final Image sprite;
    private final double speed;
    private static ArrayList<Weapon> weapons = new ArrayList<>();
    private Weapon selectedWeapon;
    private static int selectedWeaponIndex;
    private int playerHealth;

    public Player(int x, int y, Image sprite, GameEngine g) {
        this.x = x;
        this.y = y;
        this.direction = 0;
        this.speed = 400.0D;
        this.health = 100;
        this.sprite = sprite;
        this.radius = sprite.getWidth(null)/2;
        this.playerHealth = 100;

        // Give weapons
        weapons.add(new MachineGun(this, g));
        weapons.add(new Flamethrower(this, g));
        weapons.add(new RocketLauncher(this, g));
        // Select the machine gun
        selectWeapon(0);
    }

    public void draw(TestSpace game) {
        Graphics2D g = game.mGraphics;
        g.drawImage(sprite, (int)x-radius*2, (int)y-radius*2, sprite.getWidth(null)*2, sprite.getHeight(null)*2, null);
        selectedWeapon.draw(game);
    }

    public void moveLeft() {
        this.direction = -1;
    }

    public void moveRight() {
        this.direction = 1;
    }

    public void stop() {
        this.direction = 0;
    }

    public void update(double dt) {
        this.x += (double)this.direction * this.speed * dt;
        if (this.x > TestSpace.WIDTH - 50) {
            this.x = TestSpace.WIDTH - 50;
        } else if (this.x < 50) {
            this.x = 50;
        }

        selectedWeapon.update(dt);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getDirection() {
        return direction;
    }

    public int getRadius(){return this.radius;}

    private void selectWeapon(int slot) {
        // Make sure old weapon stops shooting first
        if (selectedWeapon != null) stopShooting();
        // Select
        selectedWeapon = weapons.get(slot);
        selectedWeaponIndex = slot;
    }

    public boolean isShooting() {
        return selectedWeapon.isShooting();
    }
    public void startShooting(){
        selectedWeapon.startShooting();
    }
    public void stopShooting(){
        selectedWeapon.stopShooting();
    }

    public void selectNextWeapon() {
        // Increment counter
        int nextIndex = (selectedWeaponIndex + 1) % weapons.size();
        selectWeapon(nextIndex);
    }

    public void selectPrevWeapon() {
        // Decrement counter
        int prevIndex = selectedWeaponIndex - 1;
        if (prevIndex == -1) prevIndex = weapons.size() - 1;
        // Select
        selectWeapon(prevIndex);
    }
    public int getHealth() {
        return health;
    }

    // Updated method name and variable name
    public void reduceHealth(int damage) {
        playerHealth -= damage;
    }
    public boolean isPlayerDead(){
        return playerHealth <= 0;
    }
    public void resetHealth(){
        playerHealth = 100;
    }
    public int getPlayerHealth(){
        return playerHealth;
    }

    public static void getAmmo() {
        Weapon flame = weapons.get(1);
        Weapon rocket = weapons.get(2);

        double flameCap = 0.2; // sets the highest number for flame and lowest for rocket
        double rocketCap = 0.33; // sets the highest number for rocket, any number above this will not give ammo
        double rand = Math.random(); // creates a random number between 0 and 1.0

        if ((0 < rand) && (rand < flameCap)) {
            flame.incrementRounds();
        } else if ((flameCap < rand) && (rand < rocketCap)) {
            rocket.incrementRounds();
        }
    }

    //Clears the weapons after the game to be reset
    public static void clearWeapons() {
        weapons.remove(2);
        weapons.remove(1);
        weapons.remove(0);
    }

    public Weapon getWeapon() {
        return selectedWeapon;
    }
}
