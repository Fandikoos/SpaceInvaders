package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.svalero.spaceinvaders.screen.WinnerGameScreen;

import java.util.ArrayList;
import java.util.List;

public class Boss extends Enemy{

    public int health;
    private TextureRegion textureRegion;
    private List<Missile> missiles;
    private float missileTimer;
    public boolean isRegenerating;
    public boolean resize;
    private float movementTimer; // Temporizador para cambiar la dirección de movimiento
    private float movementInterval;  // Intervalo de tiempo para cambiar la dirección
    private Vector2 velocity;

    public Boss(Vector2 position, TextureRegion textureRegion, String animationName) {
        super(position, animationName);
        this.textureRegion = textureRegion;
        health = 200;
        isRegenerating = false;
        resize = false;

        movementTimer = 0;
        movementInterval = MathUtils.random(3);

        velocity = new Vector2();
        missiles = new ArrayList<>();

        if (textureRegion != null){
            float bossX = (Gdx.graphics.getWidth() - textureRegion.getRegionWidth()) / 2;
            float bossY = (Gdx.graphics.getHeight()) - (textureRegion.getRegionHeight() * 2.5f);
            this.getPosition().set(bossX, bossY);
        }
    }

    public void setVelocity(float x, float y) {
        velocity.set(x, y);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void reduceHealthBoss(int damage){
        health -= damage;
        if (health == 100 && !isRegenerating && !resize){
            isRegenerating = true;
            regenerate();
        }
    }

    private void regenerate() {
        // Aumentamos la salud del boss como si se regenerara vida
        health += 25;
        resizeBoss();
        resize = true;
    }

    private void resizeBoss() {
        // Se hace mas grande el boss
        if (resize){
            float newResizeBoss = 3f;
            setScale(newResizeBoss);
        }

    }

    private void setScale (float scale){
        textureRegion.setRegionWidth((int) (textureRegion.getRegionWidth() * scale));
        textureRegion.setRegionHeight((int) (textureRegion.getRegionHeight() * scale));
    }

    public void fireMissile(float dt){
        missileTimer += dt;

        // Disparamos el misil si el temporizador alcanza el intervalo
        if (missileTimer >= 1.5f) {
            if (missiles == null) {
                missiles = new ArrayList<>();
            }

            // Calcula la posición de inicio del misil desde la posición del Boss
            float missileX = getPosition().x + (textureRegion.getRegionWidth() - Missile.WIDTH) / 2;
            float missileY = getPosition().y;

            // Crea un nuevo misil y agrégalo a la lista
            missiles.add(new Missile(new Texture("game/missileEnemies.png"), new Vector2(missileX, missileY)));

            // Reinicia el temporizador
            missileTimer = 0;
        }
    }

    public void moveBoss(float dt){
        movementTimer += dt; // Incrementa el temporizador

        // Cambiamos la dirección del movimiento cuando se alcance el intervalo
        if (movementTimer >= movementInterval){
            // Generamos un nuevo intervalo aleatorio
            movementInterval = MathUtils.random(3);
            // Cambiamos la dirección
            setRandomMovementDirection();
            // Reiniciamos el temporizador
            movementTimer = 0;
        }

        // Calculamos la nueva posición del Boss
        float newX = getPosition().x + getVelocity().x * dt;

        // Verificamos si el Boss se sale por el lado izquierdo de la pantalla
        if (newX < 0) {
            // Si se sale, ajustamos su posición al borde izquierdo de la pantalla
            newX = 0;
        }

        // Verificamos si el Boss se sale por el lado derecho de la pantalla
        float maxX = Gdx.graphics.getWidth() - textureRegion.getRegionWidth() * 2;
        if (newX > maxX) {
            // Si se sale, ajustamos su posición al borde derecho de la pantalla
            newX = maxX;
        }

        // Movemos al boss en la dirección en cuestión
        move(newX - getPosition().x, getVelocity().y * dt);
    }

    private void setRandomMovementDirection(){
        // Velocidad aleatoria en el eje x
        float randomSpeed = MathUtils.random(50, 150); // Modifica estos valores según la velocidad deseada
        // Dirección aleatoria: izquierda (-1) o derecha (1)
        float direction = MathUtils.randomBoolean() ? -1 : 1;
        // Establecer la velocidad en la dirección aleatoria
        setVelocity(randomSpeed * direction, 0);
    }

    public List<Missile> getMissiles(){
        return missiles;
    }

    public void dieBoss(){
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new WinnerGameScreen());
            }
        }, 1);
    }

    public int getHealth(){
        return health;
    }
}
