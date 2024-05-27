public class Flamethrower extends Weapon {
    private ParticleEmitter emitter;
    private double time;
    private final static int SWEEP_SPEED = 10;

    public Flamethrower(Player player){
        super(100,1, player);
        emitter = new FireParticleEmitter();
        emitter.move((float)player.getX()+10, TestSpace.HEIGHT - 105);
    }

    @Override
    public void fire() {
        // Only fire if rounds are available
        if (availableRounds <= 0) return;
        // Don't decrement available rounds, to make it infinite.

        double dx = Math.sin(time * SWEEP_SPEED);
//        double dy = Math.abs(Math.cos(time * SWEEP_SPEED)) + 1;
        double dy = 1;

        Projectile bullet = new FlamethrowerProjectile(player.getX()+5, player.getY()-5, dx, dy);
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
        emitter.draw(game);
    }

    @Override
    public void startShooting() {
        super.startShooting();
        emitter.emitterFrequency = 75;
    }

    @Override
    public void stopShooting() {
        super.stopShooting();
        emitter.emitterFrequency = 0;
    }
}
