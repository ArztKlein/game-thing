public class MachineGun extends Weapon {

    public MachineGun(Player player, GameEngine g){
        super(player, g, "TestSpace/resources/MachineGunFired.wav", "Machine gun", "TestSpace/resources/BulletUI.png");
        rateOfFire = 20;
        ammoCapacity = Integer.MAX_VALUE;
        availableRounds = Integer.MAX_VALUE;
    }

    @Override
    public void fire() {
        //create a bullet and add it to the bullet manager
        Projectile bullet = new MachineGunProjectile(player.getX(), player.getY());
        TestSpace.bulletManager.addBullet(bullet);
        // Don't decrement available rounds to keep it infinite
    }
    @Override
    public void incrementRounds() {
        // Ammo is constantly infinite, so do nothing.
    }
}
