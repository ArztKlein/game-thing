public class Flamethrower extends Weapon {
    private double time;
    private final static int SWEEP_SPEED = 10;

    public Flamethrower(Player player, GameEngine g){
        super(player, g, "TestSpace/resources/FlamethrowerFired.wav");
        rateOfFire = 100;
        ammoCapacity = 1;
        availableRounds=1;
    }

    @Override
    public void fire() {
        // Only fire if rounds are available
        if (availableRounds <= 0) return;
        // Don't decrement available rounds, to make it infinite.

        double dx = Math.sin(time * SWEEP_SPEED);
//        double dy = Math.abs(Math.cos(time * SWEEP_SPEED)) + 1;

        Projectile bullet = new FlamethrowerProjectile(player.getX()+5, player.getY()-5, dx);
        TestSpace.bulletManager.addBullet(bullet);
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
}
