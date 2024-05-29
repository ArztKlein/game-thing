public class RocketLauncher extends Weapon{
    public RocketLauncher(Player player, GameEngine g){
        super(player, g, "TestSpace/resources/RocketLauncherFired.wav");
        rateOfFire = 1;
        ammoCapacity = 5;
        availableRounds = 5;
    }
    @Override
    public void fire() {
        if(availableRounds>0){
            Projectile bullet = new RocketLauncherProjectile(player.getX()+2, player.getY()-5);
            TestSpace.bulletManager.addBullet(bullet);
            availableRounds--;
        }
        //click out of ammo sound?
    }
}
