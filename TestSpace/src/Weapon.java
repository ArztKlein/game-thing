import java.awt.*;

public abstract class Weapon {
    protected int rateOfFire;
    protected int ammoCapacity;
    protected int availableRounds;
    protected double x, y;
    protected double lastShotTime;
    protected boolean isShooting;
    protected Player player;
    protected GameEngine gameEngine;
    private GameEngine.AudioClip weaponFired;
    private GameEngine.AudioClip emptyClip;
    private boolean audioPlaying;
    private String name;
    private Image sprite;

    public Weapon(Player player, GameEngine gameEngine, String gunFiredFileName, String name, String imagePath){
        x = player.getX();
        y = player.getY();
        audioPlaying = false;
        this.name = name;
        this.lastShotTime= 0;
        this.player = player;
        this.gameEngine = gameEngine;
        this.sprite = GameEngine.loadImage(imagePath);
        weaponFired = gameEngine.loadAudio(gunFiredFileName);
        emptyClip = gameEngine.loadAudio("TestSpace/resources/EmptyClip.wav");
    }
    public void update(double dt){
        //update the weapon position relative to the player
        x = player.getX();
        y = player.getY();
        //if the gun is shooting and there is ammo available then a shot should be fired
        if (isShooting && availableRounds > 0) {
            //get the difference between now and when the last shot was in milliseconds
            double currentTime = System.currentTimeMillis();
            double timeSinceLastShot = currentTime - lastShotTime;
            //if lastshottime is zero, firing has just started so fire a bullet immediately
            if (lastShotTime == 0) {
                //fire -> creates a bullet
                fire();
                //play audioclip depending on weapon type, if flamethrower is active, loop the audioclip if no clip is playing
                if(this instanceof Flamethrower){if(!audioPlaying){audioPlaying =true; gameEngine.startAudioLoop(weaponFired);}}
                else{gameEngine.playAudio(weaponFired);}
                lastShotTime = currentTime;
            }
            //if we are already firing, the next shot can be fire when the time since the last shot exceeds the rate of fire in milliseconds
            else if (timeSinceLastShot >= 1000.0 / rateOfFire) {
                fire();
                if(this instanceof Flamethrower){if(!audioPlaying){audioPlaying =true; gameEngine.startAudioLoop(weaponFired);}}
                else{gameEngine.playAudio(weaponFired);}
                lastShotTime = currentTime;
            }
        }
        //if the clip is empty play sound
        else if(isShooting && availableRounds == 0){gameEngine.playAudio(emptyClip);}

    }
    public abstract void fire();
    public abstract void incrementRounds();
    public void draw(TestSpace game){};

    public String getName() {
        return name;
    }
    public void startShooting(){
        isShooting = true;
        lastShotTime = 0;
    }
    //stop shooting and stop audio loop
    public void stopShooting(){
        isShooting = false;
        audioPlaying = false;
        gameEngine.stopAudioLoop(weaponFired);
    }

    public int getAvailableRounds() {
        return availableRounds;
    }

    public Image getSprite() {
        return sprite;
    }

    public boolean isShooting() {
        return isShooting;
    }
}
