package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.svalero.spaceinvaders.manager.ConfigurationManager;
import com.svalero.spaceinvaders.manager.MusicManager;
import com.svalero.spaceinvaders.manager.ResourceManager;
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

    public boolean shieldActive;
    public TextureRegion shieldTexture;
    private boolean shieldTimerActive;
    private float shieldTimer;
    private static final float SHIELD_DURATION = 6f;
    Sound shotBoss;
    Sound explosionBoss;
    Preferences prefs;
    private Explosion explosion;
    private Vector2 explosionPositionBoss;
    private boolean isAlive;

    public Boss(Vector2 position, TextureRegion textureRegion, String animationName) {
        super(position, animationName);
        this.textureRegion = textureRegion;
        prefs = Gdx.app.getPreferences("SpaceInvaders");
        String difficulty = prefs.getString("difficulty", "low"); // Obtener la dificultad guardada

        if (difficulty.equals("low")) {
            health = 200; // Establecer vida para dificultad baja
        } else {
            health = 300; // Establecer vida para dificultad alta
        }

        isRegenerating = false;
        resize = false;

        shieldActive = false;
        shieldTexture = new TextureRegion(new Texture("game/shield.png"));
        shieldTimerActive = false;
        shieldTimer = 0;

        movementTimer = 0;
        movementInterval = MathUtils.random(3);

        velocity = new Vector2();
        missiles = new ArrayList<>();

        if (textureRegion != null){
            float bossX = (Gdx.graphics.getWidth() - textureRegion.getRegionWidth()) / 2;
            float bossY = (Gdx.graphics.getHeight()) - (textureRegion.getRegionHeight() * 2.5f);
            this.getPosition().set(bossX, bossY);
        }

        shotBoss = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/shot.mp3"));
        explosionBoss = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/explosion.mp3"));
        isAlive = true;
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

    public void setVelocity(float x, float y) {
        velocity.set(x, y);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void reduceHealthBoss(int damage){
        // Si el escudo esta activo, la ejecución de este método no continúa, por lo que
        // es la forma en la que hacemos inmune al Boss y por tanto no pierde vida
        if (shieldActive) return;

        health -= damage;
        if (health == 100 && !isRegenerating && !resize){
            isRegenerating = true;
            regenerate();
            activateShield();
        }
    }

    public void activateShield() {
        shieldActive = true;
        shieldTimerActive = true;
    }

    public void desactivateShield(){
        shieldActive = false;
    }

    // Actualización del escudo
    public void updateShield(float dt){
        // Si el timer del escudo esta activo empezamos a contar segundos para su desactivación
        if (shieldTimerActive){
            shieldTimer += dt;
            // Cuando sea mayor o igual a la duración estatíca del escudo, pues lo desactivamos
            if (shieldTimer >= SHIELD_DURATION){
                shieldTimerActive = false;
                desactivateShield();
                shieldTimer = 0;
            }
        }
    }

    private void regenerate() {
        // Aumentamos la salud del boss como si se regenerara vida
        health += 50;
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

            if (ConfigurationManager.isSoundEnabled()){
                float volumen = 2f;
                long soundShot =  shotBoss.play();
                shotBoss.setVolume(soundShot, volumen);
            }

            // Reinicia el temporizador
            missileTimer = 0;
        }
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
        explosionPositionBoss = position.cpy();
        explosion = new Explosion(ResourceManager.getAnimation("explosion"));
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new WinnerGameScreen());
                if (ConfigurationManager.isSoundEnabled()){
                    float volumen = 2f;
                    long bossDie = explosionBoss.play();
                    explosionBoss.setVolume(bossDie, volumen);
                }
                MusicManager.stopGameMusic();
            }
        }, 1);
        isAlive = false;
    }

    public boolean isAlive(){
        return isAlive;
    }

    public void updateExplosionBoss(float dt) {
        if (explosion != null) {
            explosionPositionBoss = position.cpy(); // Actualizamos la posición de la explosión
            explosion.update(dt);
            if (explosion.isFinished()) {
                // Si la explosión ha terminado, llamamos a dieBoss() para cambiar la pantalla, detener la música, etc.
                dieBoss();
            }
        }
    }

    public void renderExplosion(SpriteBatch batch) {
        if (explosion != null) {
            explosion.drawBoss(batch, explosionPositionBoss); // Dibujamos la explosión en la posición del Boss
        }
    }
}
