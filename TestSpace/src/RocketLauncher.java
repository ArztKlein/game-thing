public class RocketLauncher extends Weapon{
    public RocketLauncher(Player player){
        super(player);
        rateOfFire = 10;
        ammoCapacity = 500;
        availableRounds = 500;
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
