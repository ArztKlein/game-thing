import java.util.Timer;

public abstract class Weapon {
    protected int rateOfFire;
    protected int damage;
    protected int ammoCapacity;
    protected int availableRounds;
    protected Projectile projectileType;
    protected double x, y;
    protected double lastShotTime;
    protected boolean isShooting;
    protected final BulletManager bulletManager;
    protected final Player player;

    public Weapon(int rateOfFire, int damage, int ammoCapacity, Projectile projectileType, Player player, BulletManager bulletManager){
        this.rateOfFire = rateOfFire;
        this.damage = damage;
        this.ammoCapacity=ammoCapacity;
        this.availableRounds=ammoCapacity;
        this.x = player.getX()+5;
        this.y = player.getY()-5;
        this.projectileType = projectileType;
        this.bulletManager = bulletManager;
        this.player = player;
        this.isShooting = false;
        this.lastShotTime= 0;
    }
    public void update(double dt){
        x = (player.getX()+5) * dt;
        y = (player.getY()-5) * dt;
    }
    public abstract void fire();
    public boolean getIsShooting(){return isShooting;}
    public void setShootingTrue(){isShooting = true;}
    public void setShootingFalse(){isShooting = false;}

}
