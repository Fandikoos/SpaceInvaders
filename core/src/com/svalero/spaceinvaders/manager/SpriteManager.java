package com.svalero.spaceinvaders.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.svalero.spaceinvaders.Utils.HudUtils;
import com.svalero.spaceinvaders.domain.*;
import com.svalero.spaceinvaders.screen.BossScreen;
import com.svalero.spaceinvaders.screen.MainMenuScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpriteManager implements Disposable {

    Player player;
    boolean pause;
    public List<Asteroid> fallAsteroids;
    private float asteroidTimer;
    private float asteroidInterval;
    EnemyFleet enemies;
    Sound shots;
    Sound explosion;
    HudUtils hud;


    public SpriteManager(){
        initialize();
    }

    private void initialize(){
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeigth = Gdx.graphics.getHeight();
        player = new Player("ship", new Vector2(screenWidth / 2, 60),  screenWidth, screenHeigth);
        enemies = new EnemyFleet(new Texture("game/enemy.png"), screenWidth, screenHeigth);
        pause = false;
        shots = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/shot.mp3"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/explosion.mp3"));
        fallAsteroids = new ArrayList<>();
        asteroidTimer = 0;
        asteroidInterval = MathUtils.random(5, 10);  //Intervalo de tiempo entre asteroide y asteroide
        hud = new HudUtils();
        hud.lives = 3;
        hud.score = 0;
    }

    private void spawnAsteroids() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        fallAsteroids.add(new Asteroid(new Vector2(), new TextureRegion(new Texture("game/stone_1.png")), "stone", screenWidth, screenHeight));
    }

    private void handleEnemyCollisions() {
        // Cogemos los misles
        Iterator<Missile> missileIterator = player.getMissiles().iterator();
        while (missileIterator.hasNext()) {
            Missile missile = missileIterator.next();
            Rectangle missileBounds = missile.getBounds();

            // Cogemos los enemigos
            Iterator<Enemy> enemyIterator = enemies.getEnemies().iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                Rectangle enemyBounds = enemy.getBounds();

                // Si golpea un misil con un enemigo
                if (missileBounds.overlaps(enemyBounds)) {
                    // Eliminamos al enemigo y al misil
                    enemyIterator.remove();
                    missileIterator.remove();
                    player.increaseScore(25);
                    System.out.println(player.score);
                }
            }
        }
    }

    private void handlePlayerCollision() {
        Rectangle playerBounds = player.getBounds();

        Iterator<Missile> missileIterator = enemies.getMissiles().iterator();
        while (missileIterator.hasNext()) {
            Missile missile = missileIterator.next();
            Rectangle missileBounds = missile.getBounds();

            if (playerBounds.overlaps(missileBounds)) {
                missileIterator.remove();
                player.reduceLife();
                player.decreaseScore(10);
                System.out.println(player.score);

                if (player.lives == 0){
                    explosion.play();
                    player.die();
                }
            }
        }
    }

    private void handlePlayerCollisionWithAsteroid() {
        Rectangle playerBounds = player.getBounds();

        Iterator<Asteroid> asteroidIterator = fallAsteroids.iterator();
        while (asteroidIterator.hasNext()) {
            Asteroid asteroid = asteroidIterator.next();
            Rectangle asteroidBounds = asteroid.getBounds();

            if (playerBounds.overlaps(asteroidBounds)) {
                asteroidIterator.remove();
                explosion.play();
                player.die();
            }
        }
    }

    //Eventos de la pantalla
    private void handleGameScreeninputs(){
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pause = !pause;
        }
    }

    public void update(float dt){
        if (!pause){
            timeAsteroids(dt);

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                player.fire();
                if (ConfigurationManager.isSoundEnabled()){
                    shots.play();
                }
            }

            player.manageInput();
            player.moveMissiles(); // Mover y eliminar los misiles

            for (Asteroid asteroid : fallAsteroids){
                asteroid.update(dt);
            }

            handlePlayerCollision();
            handleEnemyCollisions();
            handlePlayerCollisionWithAsteroid();

            if (enemies.getEnemies().isEmpty()){
                ((Game) Gdx.app.getApplicationListener()).setScreen(new BossScreen());
            }
        }

        handleGameScreeninputs();
    }

    //Temporizador asteroides
    private void timeAsteroids(float dt){
        asteroidTimer += dt;
        if (asteroidTimer >= asteroidInterval){
            spawnAsteroids();
            asteroidTimer=0;
            asteroidInterval = MathUtils.random(5, 10);
        }
    }



    @Override
    public void dispose() {

    }
}
