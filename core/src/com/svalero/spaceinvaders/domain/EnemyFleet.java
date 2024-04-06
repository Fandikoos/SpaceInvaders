package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class EnemyFleet {
    private List<Enemy> enemies;
    private List<Missile> missiles;
    private float speedEnemiesX = 90; //Velocidad horizontal que lleva la flota
    private boolean moveRight = true; //Controlamos la dirección de movimiento
    private float screenWidth = Gdx.graphics.getWidth();
    private float fleetWidth;
    private float limitFleetWidthX = 120;
    private float missileTimer;  //Temporizador para el disparo de misiles
    private final float missileInterval = 1.5f; //Intervalo de tiempo entre cada disparo
    Preferences prefs;
    Sound shots;


    public EnemyFleet(Texture texture, float screenWidth, float screenHeight){
        enemies = new ArrayList<>();
        missiles = new ArrayList<>();
        prefs = Gdx.app.getPreferences("SpaceInvaders");
        float shipWidth = texture.getWidth();
        float shipHeight = texture.getHeight();
        float spaceBetweenShips = 5;

        int rows = 3;
        int cols = 6;
        float startX = (screenWidth - (cols * (shipWidth + spaceBetweenShips))) / 2;
        float startY = screenHeight - screenHeight / 4; // Ajusta la posición vertical según sea necesario

        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                float x = startX + col * (shipWidth + spaceBetweenShips);
                float y = startY + row * (shipHeight + spaceBetweenShips);
                enemies.add(new Enemy(new Vector2(x, y), "enemy"));
            }
        }
    }

    public void draw(SpriteBatch batch) {
        for (Enemy enemy : enemies) {
            enemy.draw(batch, 2f);
        }
        for (Missile missile : missiles){
            missile.draw(batch);
        }
    }

    public void update(float dt){
        if (moveRight){
            moveRightPosition(dt);
        } else {
            moveLeftPosition(dt);
        }
        checkBounds(screenWidth);

        missileTimer += dt;
        //Disparar un misil si el temporizador ha alcanzado el intervalo
        if (missileTimer >= missileInterval){
            fireMissile();
            missileTimer = 0; // Reiniciamos el temporizador para ir a por los siguientes msiiles
                /*if (prefs.getBoolean("sound")){
                    shots.play();
                }*/
        }

        // Mover y actualizar la posición de los misiles
        for (int i = 0; i < missiles.size(); i++){
            Missile missile = missiles.get(i);
            missile.move(0, -100 * dt);
            // Eliminamos el misil si sale de la pantalla
            if (missile.getPosition().y < -missile.getTexture().getHeight()){
                missiles.remove(i);
                i--;  //Decrementamos el índice con el misil eliminado
            }
        }
    }

    private void fireMissile() {
        if (!enemies.isEmpty()){
            // Escogemos una nave aleatoria de la flota para que realice el disparo
            int randomIndex = MathUtils.random(enemies.size() - 1);
            Enemy randomEnemy = enemies.get(randomIndex);
            // Obtenemos la posicón de dicho enemigo
            Vector2 enemyPosition = randomEnemy.getPosition();
            // Agregamos el misil
            missiles.add(new Missile(new Texture("game/missileEnemies.png"), new Vector2(enemyPosition.x, enemyPosition.y)));
        }
    }

    private void moveRightPosition(float delta){
        float maxEnemyX = Float.MIN_VALUE;
        for (Enemy enemy : enemies){
            enemy.move(speedEnemiesX * delta, 0);
            if (enemy.getPosition().x > maxEnemyX){
                maxEnemyX = enemy.getPosition().x;
            }
        }
        fleetWidth = maxEnemyX + enemies.get(0).getTexture().getRegionWidth();
    }

    private void moveLeftPosition(float delta){
        float minEnemyX = Float.MAX_VALUE;
        for (Enemy enemy : enemies){
            enemy.move(-speedEnemiesX * delta, 0);
            if (enemy.getPosition().x < minEnemyX){
                minEnemyX = enemy.getPosition().x;
            }
            //Reflejamos el ancho total que tiene la flota de naves enemigas
            fleetWidth = enemies.get(0).getPosition().x + enemies.get(0).getTexture().getRegionWidth();
        }

    }

    public void checkBounds(float screenWidth){
        if (fleetWidth >= screenWidth){
            moveRight = false; //Cambiar de direccion hacia la izquierda
        }
        if (fleetWidth <= limitFleetWidthX){
            moveRight = true; //Cambiar de direccion hacia la derecha
        }
    }


    public List<Enemy> getEnemies(){
        return enemies;
    }


}
