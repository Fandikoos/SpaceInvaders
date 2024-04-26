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
import com.svalero.spaceinvaders.screen.GameScreen;
import com.svalero.spaceinvaders.screen.MainMenuScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpriteManager implements Disposable {

    public Boss boss;
    public Player player;
    boolean pause;
    private HudUtils hud;
    public List<Asteroid> fallAsteroids;
    private float asteroidTimer;
    private float asteroidInterval;
    public List<ExtraLifePowerUp> fallExtraLifes;
    private float lifePowerUpTimer;
    private float lifePowerUpInterval;
    public List<DoubleScorePowerUp> fallDoubleScore;
    private float doubleScorePowerUpTimer;
    private float doubleScorePowerUpInterval;
    EnemyFleet enemies;
    Sound shots;
    Sound explosion;
    private GameScreen gameScreen;


    // Añadimos la gameScreen al constructor para poder cambiar de pantalla cuando no queden enemigos
    public SpriteManager(Player player, HudUtils hud, GameScreen gameScreen){
        this.player = player;
        this.hud = hud;
        this.gameScreen = gameScreen;
        initialize();
    }

    private void initialize(){
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeigth = Gdx.graphics.getHeight();
        player = new Player("ship", new Vector2(screenWidth / 2, 60),  screenWidth, screenHeigth);
        enemies = new EnemyFleet(new Texture("game/enemy.png"), screenWidth, screenHeigth);
        boss = new Boss(new Vector2(), new TextureRegion(new Texture("game/boss_1.png")) ,"boss");
        pause = false;
        shots = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/laser.wav"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/explosion.mp3"));
        fallAsteroids = new ArrayList<>();
        fallExtraLifes = new ArrayList<>();
        fallDoubleScore = new ArrayList<>();
        asteroidTimer = 0;
        asteroidInterval = MathUtils.random(5, 10);  //Intervalo de tiempo entre asteroide y asteroide
        lifePowerUpTimer = 0;
        lifePowerUpInterval = MathUtils.random(15, 20);
        doubleScorePowerUpTimer = 0;
        doubleScorePowerUpInterval = MathUtils.random(15, 20);
    }

    private void spawnAsteroids() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        fallAsteroids.add(new Asteroid(new Vector2(), new TextureRegion(new Texture("game/stone_1.png")), "stone", screenWidth, screenHeight));
    }

    private void spawnLifePowerUp(){
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        TextureRegion lifeTexture = new TextureRegion(ResourceManager.getTexture("heart"));
        float x = MathUtils.random(0, screenWidth - lifeTexture.getRegionWidth());
        float y = screenHeight;
        float speed = 80;
        fallExtraLifes.add(new ExtraLifePowerUp(lifeTexture, x, y, speed));
    }

    private void spawnDoubleScorePowerUp(){
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        TextureRegion doubleScoreTexture = new TextureRegion(ResourceManager.getTexture("score"));
        float x = MathUtils.random(0, screenWidth - doubleScoreTexture.getRegionWidth());
        float y = screenHeight;
        float speed = 100;
        fallDoubleScore.add(new DoubleScorePowerUp(doubleScoreTexture, x, y, speed));
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
                }
            }
        }
    }

    private void handlePlayerMissileCollisionBoss(){
        Iterator<Missile> missileIterator = player.getMissiles().iterator();
        while (missileIterator.hasNext()) {
            Missile missile = missileIterator.next();
            Rectangle playerMissileBounds = missile.getBounds();

            Rectangle bossBound = boss.getBounds();

            // Comprobamos si hay colisión entre el misil del jugador y el boss
            if (bossBound.overlaps(playerMissileBounds)) {
                boss.reduceHealthBoss(25);
                System.out.println(boss.health);
                player.increaseScore(30);

                if (boss.health <= 0) {
                    boss.dieBoss();
                }

                // Eliminamos el misil después de golpear al jefe
                missileIterator.remove();
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

                if (player.lives == 0){
                    if (ConfigurationManager.isSoundEnabled()){
                        explosion.play();
                    }
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
                if (ConfigurationManager.isSoundEnabled()){
                    explosion.play();
                }
                player.die();
            }
        }
    }

    private void handleCollisionWithExtraLife(){
        Rectangle playerBounds = player.getBounds();
        Iterator<ExtraLifePowerUp> extraLifePowerUpIterator = fallExtraLifes.iterator();

        while (extraLifePowerUpIterator.hasNext()){
            ExtraLifePowerUp extraLifePowerUp = extraLifePowerUpIterator.next();
            Rectangle lifeBound = extraLifePowerUp.getBounds();

            if (playerBounds.overlaps(lifeBound)){
                extraLifePowerUpIterator.remove();
                player.addLife();
            }
        }
    }

    private void handleCollisionWithDoubleScore(){
        Rectangle playerBounds = player.getBounds();
        Iterator<DoubleScorePowerUp> doubleScorePowerUpIterator = fallDoubleScore.iterator();

        while (doubleScorePowerUpIterator.hasNext()){
            DoubleScorePowerUp doubleScorePowerUp = doubleScorePowerUpIterator.next();
            Rectangle doubleScoreBound = doubleScorePowerUp.getBounds();

            if (playerBounds.overlaps(doubleScoreBound)){
                doubleScorePowerUpIterator.remove();
                player.activateDoubleScore();
            }
        }
    }

    //Eventos de la pantalla
    private void handleGameScreeninputs(){
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            MusicManager.stopGameMusic();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pause = !pause;
        }
    }



    public void update(float dt){
        if (!pause){
            timeAsteroids(dt);
            timeLifePowerUps(dt);

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                player.fire();
                if (ConfigurationManager.isSoundEnabled()){
                    shots.play();
                }
            }

            player.manageInput();
            player.moveMissiles(); // Mover y eliminar los misiles
            player.updateExplosion(dt);

            for (Asteroid asteroid : fallAsteroids){
                asteroid.update(dt);
            }

            for (ExtraLifePowerUp powerUp : fallExtraLifes){
                powerUp.update(dt);
            }

            for (DoubleScorePowerUp powerUp : fallDoubleScore){
                powerUp.update(dt);
            }

            handlePlayerCollision();
            handleEnemyCollisions();
            handlePlayerCollisionWithAsteroid();
            handleCollisionWithExtraLife();
            handleCollisionWithDoubleScore();

            if (player.doubleScoreActive){
                // Actualizamos el temporizador de doble puntuacion si está activo
                player.doubleScoreTimer += dt;

                //Si pasan 10 segundo desactivar el efecto de puntos dobles
                if (player.doubleScoreTimer >= 10){
                    player.doubleScoreActive = false;
                    player.doubleScoreTimer = 0;
                }
            }

            if (enemies.getEnemies().isEmpty()){
                // Metodo de la GameScreen para cambiar de nivel con toda la info acerca del player, hud, sprite manager y render manager
                gameScreen.switchToBossScreen();
            }
        }

        handleGameScreeninputs();
        hud.update(player);
    }

    public void updateBoss(float dt){
        if (!pause){
            timeAsteroids(dt);
            timeLifePowerUps(dt);


            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                player.fire();
                if (ConfigurationManager.isSoundEnabled()){
                    shots.play();
                }
            }

            player.manageInput();
            player.moveMissiles();
            player.updateExplosion(dt);

            for (Asteroid asteroid : fallAsteroids){
                asteroid.update(dt);
            }

            for (ExtraLifePowerUp powerUp : fallExtraLifes){
                powerUp.update(dt);
            }

            for (DoubleScorePowerUp powerUp : fallDoubleScore){
                powerUp.update(dt);
            }

            boss.fireMissile(dt);
            moveMissilesBoss(dt);
            boss.updateExplosionBoss(dt);

            handlePlayerCollisionBoss();
            handlePlayerMissileCollisionBoss();
            handlePlayerCollisionWithAsteroid();
            handleCollisionWithExtraLife();
            handleCollisionWithDoubleScore();

            if (player.doubleScoreActive){
                // Actualizamos el temporizador de doble puntuacion si está activo
                player.doubleScoreTimer += dt;

                //Si pasan 10 segundo desactivar el efecto de puntos dobles
                if (player.doubleScoreTimer >= 10){
                    player.doubleScoreActive = false;
                    player.doubleScoreTimer = 0;
                }
            }
        }

        handleGameScreeninputs();
        hud.update(player);
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

    private void timeLifePowerUps(float dt) {
        lifePowerUpTimer += dt;
        doubleScorePowerUpTimer += dt;

        if (lifePowerUpTimer >= lifePowerUpInterval){
            spawnLifePowerUp();
            lifePowerUpTimer = 0;
            lifePowerUpInterval = MathUtils.random(15, 20);
        }
        if (doubleScorePowerUpTimer >= doubleScorePowerUpInterval){
            spawnDoubleScorePowerUp();
            doubleScorePowerUpTimer = 0;
            doubleScorePowerUpInterval = MathUtils.random(15, 20);
        }
    }

    private void moveMissilesBoss(float dt) {
        Iterator<Missile> missileIterator = boss.getMissiles().iterator();
        while (missileIterator.hasNext()) {
            Missile missile = missileIterator.next();
            missile.move(0, -Missile.SPEED * dt);

            // Si el misil sale de la pantalla, quítalo
            if (missile.getPosition().y < 0 || missile.getPosition().y > Gdx.graphics.getHeight()) {
                missileIterator.remove();
            }
        }
    }

    private void handlePlayerCollisionBoss() {
        Rectangle playerBounds = player.getBounds();

        Iterator<Missile> missileIterator = boss.getMissiles().iterator();
        while (missileIterator.hasNext()) {
            Missile missile = missileIterator.next();
            Rectangle missileBounds = missile.getBounds();

            if (playerBounds.overlaps(missileBounds)) {
                missileIterator.remove();
                player.reduceLife();
                player.decreaseScore(20);

                if (player.lives == 0){
                    explosion.play();
                    player.die();
                }
            }
        }
    }



    @Override
    public void dispose() {

    }
}
