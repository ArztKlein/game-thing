public class RocketLauncher extends Weapon{
    public RocketLauncher(Player player){
        super(player);
        rateOfFire = 2;
        ammoCapacity = 5;
        availableRounds = 5;
    }
    @Override
    public void fire() {
        if(availableRounds>0){
            Projectile bullet = new RocketLauncherProjectile(player.getX()+5, player.getY()-5);
            TestSpace.bulletManager.addBullet(bullet);
            availableRounds--;
        }
        //click out of ammo sound?
    }
}
