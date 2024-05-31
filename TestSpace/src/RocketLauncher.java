public class RocketLauncher extends Weapon{
    public RocketLauncher(Player player, GameEngine g){
        super(player, g, "TestSpace/resources/RocketLauncherFired.wav", "Rocket launcher", "TestSpace/resources/RocketUI.png");
        rateOfFire = 1;
        ammoCapacity = 8;
        availableRounds = 8;
    }
    @Override
    public void fire() {
            Projectile bullet = new RocketLauncherProjectile(player.getX(), player.getY());
            TestSpace.bulletManager.addBullet(bullet);
            availableRounds--;
          }

    @Override
    public void incrementRounds() {
        if (availableRounds < ammoCapacity) {
            availableRounds++;
        }
    }
}
