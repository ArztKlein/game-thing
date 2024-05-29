public class MachineGun extends Weapon {

    public MachineGun(Player player){
        super(player);
        rateOfFire = 10;
        ammoCapacity = 999999999;
        availableRounds = 999999999;
    }

    @Override
    public void fire() {
        if(availableRounds>0){
            Projectile bullet = new MachineGunProjectile(player.getX()+5, player.getY()-5);
            TestSpace.bulletManager.addBullet(bullet);
            availableRounds--;
        }
        //click out of ammo sound?
    }
}
