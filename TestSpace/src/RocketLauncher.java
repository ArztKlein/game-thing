public class RocketLauncher extends Weapon{
    public RocketLauncher(Player player){
        super(1, 5, player);
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
