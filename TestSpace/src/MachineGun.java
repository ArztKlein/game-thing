public class MachineGun extends Weapon {

    public MachineGun(Player player){
        super(32,1,99999999, new MachineGunProjectile(player.getX()+5, player.getY()-5) , player );

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
