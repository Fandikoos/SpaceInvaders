package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.svalero.spaceinvaders.screen.EndGameScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player extends Character{

    public int lives;
    public int score;
    private float screenWidth;
    private final float screenHeight;
    private List<Missile> missiles;

    public Player(String animationName, Vector2 vector2, float screenWidth, float screenHeight) {
        super(new Vector2(0, 0), animationName);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.missiles = new ArrayList<>();
        this.lives = 3;
        this.score = 0;
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
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int points) {
        this.score += points;
    }

    public void decreaseScore(int points) {
        this.score -= points;
        if (this.score < 0) {
            this.score = 0; // Para que la puntuación nunca negativa
        }
    }

    public void die() {
        ((Game) Gdx.app.getApplicationListener()).setScreen(new EndGameScreen());
    }
}
