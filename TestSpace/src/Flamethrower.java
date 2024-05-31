public class Flamethrower extends Weapon {
    private double time;
    private final static int SWEEP_SPEED = 10;

    public Flamethrower(Player player, GameEngine g){
        super(player, g, "TestSpace/resources/FlamethrowerFired.wav", "Flamethrower", "TestSpace/resources/FlameUI.png");
        rateOfFire = 100;
        ammoCapacity = 1000;
        availableRounds = 500;
    }

    @Override
    public void fire() {
        double dx = Math.sin(time * SWEEP_SPEED);
        Projectile bullet = new FlamethrowerProjectile(player.getX(), player.getY(), dx);
        TestSpace.bulletManager.addBullet(bullet);
        availableRounds--;
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        time += dt;
    }

    @Override
    public void draw(TestSpace game) {
        super.draw(game);
    }

    @Override
    public void startShooting() {
        super.startShooting();
    }

    @Override
    public void stopShooting() {
        super.stopShooting();
    }

    public void incrementRounds() {
        if (availableRounds < ammoCapacity) {
            availableRounds += 8;
        }
    }
}
