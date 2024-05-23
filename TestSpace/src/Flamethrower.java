public class Flamethrower extends Weapon {
    private ParticleEmitter emitter;

    public Flamethrower(Player player){
        super(32,1,player );
        emitter = new FireParticleEmitter();
        emitter.move((float)player.getX()+10, TestSpace.HEIGHT - 105);
    }

    @Override
    public void fire() {
        if(availableRounds>0){
            availableRounds--;
        }
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        emitter.update((float) dt);
    }

    @Override
    public void draw(TestSpace game) {
        super.draw(game);
        emitter.move((float)player.getX()+10, TestSpace.HEIGHT - 105);
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
