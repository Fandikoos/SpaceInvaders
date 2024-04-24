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

    public Player(String animationName, Vector2 position, float screenWidth, float screenHeight) {
        super(position, animationName);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.missiles = new ArrayList<>();
        prefs = PreferencesUtils.getPrefs();
        lives = 3;
        score = 0;
        level = 1;
        explosionForLive = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/explosion_light.wav"));
    }

    //Movimiento de la Nave
    public void manageInput(){
        float speed = 20;
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

    public int getScore() {
        return score;
    }

    public void increaseScore(int points) {
        this.score += points;
        prefs.putInteger("score", this.score);
        prefs.flush(); // Guardar
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
            explosion.draw(batch, explosionPositionShip); // Usamos la posicón de la explosion
        }
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
