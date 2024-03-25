package com.svalero.spaceinvaders.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.svalero.spaceinvaders.Utils.MusicManager;
import com.svalero.spaceinvaders.Utils.PreferencesUtils;
import com.svalero.spaceinvaders.domain.Asteroid;
import com.svalero.spaceinvaders.domain.EnemyFleet;
import com.svalero.spaceinvaders.domain.Missile;
import com.svalero.spaceinvaders.domain.Player;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    SpriteBatch batch;
    Player player;
    boolean pause;
    Texture background;
    EnemyFleet enemies;
    Preferences prefs;
    private List<Asteroid> fallAsteroids;
    private float asteroidTimer;
    private float asteroidInterval;
    Sound shots;

    @Override
    public void show() {
        MusicManager.stopMenuMusic();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeigth = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        player = new Player(new Texture("game/ship.png"), new Vector2(screenWidth / 2, 60), screenWidth, screenHeigth);
        background = new Texture("background/game.png");
        enemies = new EnemyFleet(new Texture("game/enemy.png"), screenWidth, screenHeigth);
        pause = false;
        shots = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/shot.mp3"));
        fallAsteroids = new ArrayList<>();
        asteroidTimer = 0;
        asteroidInterval = MathUtils.random(5, 10);  //Intervalo de tiempo entre asteroide y asteroide

        prefs = PreferencesUtils.getPrefs();
    }

    private void spawnAsteroids() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        fallAsteroids.add(new Asteroid(new Texture("game/asteroid.png"), screenWidth, screenHeight));
    }


    @Override
    public void render(float dt) {
        ScreenUtils.clear(1, 0, 0, 1);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Dibujar misiles
        for (Missile missile : player.getMissiles()){
            missile.draw(batch);
        }

        asteroidTimer += dt;
        if (asteroidTimer >= asteroidInterval){
            spawnAsteroids();
            asteroidTimer=0;
            asteroidInterval = MathUtils.random(5, 10);
        }

        //Dibujar asteroides
        for (Asteroid asteroid : fallAsteroids){
            asteroid.draw(batch, 0.7f);
        }

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            player.fire();
            if (prefs.getBoolean("sound")){
                shots.play();
            }
        }

        if (!pause) {
            player.manageInput();
            player.moveMissiles(); //Mover y eliminar los misiles

            for (Asteroid asteroid : fallAsteroids){
                asteroid.update(dt);
            }
        }

        batch.begin();
        player.draw(batch, 0.4f);
        batch.end();

        batch.begin();
        enemies.update(dt);
        enemies.draw(batch);
        batch.end();


        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pause = !pause;
        }

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        background.dispose();
    }
}
