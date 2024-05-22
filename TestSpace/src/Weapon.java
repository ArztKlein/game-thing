import java.util.Timer;

public abstract class Weapon {
    protected int rateOfFire;
    protected int damage;
    protected int ammoCapacity;
    protected int availableRounds;
    protected double x, y;
    protected double lastShotTime;
    protected boolean isShooting;
    protected final Player player;

    public Weapon(int rateOfFire, int damage, int ammoCapacity, Player player){
        this.rateOfFire = rateOfFire;
        this.damage = damage;
        this.ammoCapacity=ammoCapacity;
        this.availableRounds=ammoCapacity;
        this.x = player.getX()+5;
        this.y = player.getY()-5;
        this.player = player;
        this.isShooting = false;
        this.lastShotTime= 0;
    }
    public void update(double dt){
        x = (player.getX()+5);
        y = (player.getY()-5);

        if (isShooting && availableRounds > 0) {
            double currentTime = System.currentTimeMillis();
            if (currentTime - lastShotTime >= 1000.0 / rateOfFire) {
                fire();
                lastShotTime = currentTime;
            }
        }
    }
    public abstract void fire();
    public boolean isShooting(){return isShooting;}
    public void startShooting(){
        isShooting = true;
        lastShotTime = System.currentTimeMillis();
    }
    public void stopShooting(){
        isShooting = false;
    }

    public void draw(TestSpace game){}

}
