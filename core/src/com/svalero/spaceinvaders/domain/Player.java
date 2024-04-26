package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.kotcrab.vis.ui.util.value.PrefHeightIfVisibleValue;
import com.svalero.spaceinvaders.Utils.PreferencesUtils;
import com.svalero.spaceinvaders.manager.ConfigurationManager;
import com.svalero.spaceinvaders.manager.MusicManager;
import com.svalero.spaceinvaders.manager.ResourceManager;
import com.svalero.spaceinvaders.screen.EndGameScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player extends Character{

    public int lives;
    public int score;
    public int level;
    private float screenWidth;
    private final float screenHeight;
    private List<Missile> missiles;
    Sound explosionForLive;
    Preferences prefs;
    private Explosion explosion;
    private Vector2 explosionPositionShip;
    private boolean isAlive;
    public boolean doubleScoreActive;
    public float doubleScoreTimer;
    private static final float DEFAULT_SPEED = 10;
    private static final float INCREASED_SPEED = 25;
    private float currentSpeed;

    public Player(String animationName, Vector2 position, float screenWidth, float screenHeight) {
        super(position, animationName);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.missiles = new ArrayList<>();
        prefs = PreferencesUtils.getPrefs();
        lives = 3;
        score = 0;
        explosionForLive = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/explosion_light.wav"));
        isAlive = true;
        doubleScoreTimer = 0;
        doubleScoreActive = false;
        currentSpeed = DEFAULT_SPEED;
    }

    public void increaseSpeed() {
        currentSpeed = INCREASED_SPEED; // Aumentamos velocidad
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                resetSpeed(); // Resetear velocidad después de 7 segundos
            }
        }, 7); // Duración de 7 segundos
    }

    public void resetSpeed(){
        currentSpeed = DEFAULT_SPEED;
    }

    //Movimiento de la Nave
    public void manageInput(){
        float speed = currentSpeed;
        double rightLimit = 1900.0;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rect.x < rightLimit - rect.width){
            move(speed, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && rect.x > 0){
            move(-speed, 0);
        }

        //Mover y eliminar misiles
        for (Missile missile : missiles){
            missile.move(0, 5);
        }

    }

    public void fire(){
        Texture missileTexture = new Texture("game/missilePlayer.png");

        float missileX = rect.x + 25;
        //Posicion y donde sale el misil que es justo encima de la nava
        float missileY = rect.y + currentFrame.getRegionHeight()/2;
        Vector2 missilePosition = new Vector2(missileX, missileY);
        Missile missile = new Missile(missileTexture, missilePosition);
        missiles.add(missile);
    }

    public void moveMissiles(){
        Iterator<Missile> iter = missiles.iterator();
        while (iter.hasNext()){
            Missile missile = iter.next();
            missile.move(0, 5);
            //Eliminar los que salgan de la pantalla
            if (missile.getPosition().y > screenHeight){
                iter.remove();
            }
        }
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    public Rectangle getBounds(){
        return new Rectangle(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    public void reduceLife() {
        this.lives--;
        prefs.putInteger("lives", this.lives);
        prefs.flush(); // Guardar
        if (ConfigurationManager.isSoundEnabled()){
            explosionForLive.play();
        }
    }

    public void activateDoubleScore(){
        doubleScoreActive = true;
        doubleScoreTimer = 0;
    }

    public void increaseScore(int points) {
        if (doubleScoreActive){
            points *= 2;
        }
        this.score += points;
        //prefs.putInteger("score", this.score);
        //prefs.flush(); Guardar
    }

    public void decreaseScore(int points) {
        this.score -= points;
        if (this.score < 0) {
            this.score = 0; // Para que la puntuación nunca negativa
        }
    }



    public void die() {
        explosionPositionShip = position.cpy(); // Copiamos la posición actual de la nave para hacer la explosion en la misma posición
        explosion = new Explosion(ResourceManager.getAnimation("explosion"));
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new EndGameScreen());
                MusicManager.stopGameMusic();
            }
        }, 1);
        isAlive = false;
    }

    public boolean isAlive(){
        return isAlive;
    }

    public void updateExplosion(float dt) {
        if (explosion != null) {
            explosionPositionShip = position.cpy();  // Actualizamos la posicion de la explosion
            explosion.update(dt);
            if (explosion.isFinished()) {
                die();
            }
        }
    }

    public void renderExplosion(SpriteBatch batch) {
        if (explosion != null) {
            explosion.drawPlayer(batch, explosionPositionShip); // Usamos la posicón de la explosion
        }
    }

    public void addLife(){
        lives++;
    }
}
