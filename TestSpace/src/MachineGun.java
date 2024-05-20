public class MachineGun extends Weapon {

    public MachineGun(Player player, BulletManager bulletManager){
        super(10,1,99999999, new MachineGunProjectile(player.getX()+5, player.getY()-5) , player, bulletManager );

    }

    @Override
    public void fire() {
        if(availableRounds>0){
            Projectile bullet = new MachineGunProjectile(player.getX(), player.getY());
            bulletManager.addBullet(bullet);
            availableRounds--;}
        //click out of ammo sound?
    }
}
