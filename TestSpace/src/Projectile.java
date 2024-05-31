public abstract class Projectile
{
    protected double x, y;
    protected double velY, velX, accelY;
    protected int damage;
    protected int radius;
    public Projectile(double x, double y){
        this.x = x;
        this.y = y;
        this.radius = 0;
        this.velY = 0;
        this.accelY = 0;
        this.damage = 0;
    }
    public abstract void update(double dt);

    public abstract void draw(GameEngine g);

    public abstract boolean checkCollision(AlienManager alienManager);

    public abstract boolean isFinished();

    public double getX() {
        return x;
    }

    public double getY(){
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    public double getDistance(Alien alien){
        double dx = x - alien.getX();
        double dy = y - alien.getY();
        return Math.sqrt(dx * dx + dy * dy);}
}
