public class MachineGun extends Weapon {

    public MachineGun(Player player, GameEngine g){
        super(player, g, "TestSpace/resources/MachineGunFired.wav", "Machine gun", "TestSpace/resources/BulletUI.png");
        rateOfFire = 10;
        ammoCapacity = Integer.MAX_VALUE;
        availableRounds = Integer.MAX_VALUE;
    }

    @Override
    public void fire() {
        if(availableRounds>0){
            Projectile bullet = new MachineGunProjectile(player.getX()+5, player.getY()-5);
            TestSpace.bulletManager.addBullet(bullet);
            // Don't decrement available rounds to keep it infinite
        }
    }

    @Override
    public void incrementRounds() {
        // Ammo is constantly infinite, so do nothing.
    }
}
