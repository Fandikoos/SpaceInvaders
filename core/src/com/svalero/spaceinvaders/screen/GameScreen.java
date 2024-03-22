package com.svalero.spaceinvaders.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.svalero.spaceinvaders.domain.Missile;
import com.svalero.spaceinvaders.domain.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen implements Screen {
    SpriteBatch batch;
    Player player;
    boolean pause;
    Texture background;

    @Override
    public void show() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeigth = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        player = new Player(new Texture("ships/ship.png"), new Vector2(screenWidth / 2, 60), screenWidth, screenHeigth);
        background = new Texture("background/game.png");
        pause = false;
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

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            player.fire();
        }

        if (!pause) {
            player.manageInput();
            player.moveMissiles(); //Mover y eliminar los misiles
        }

        batch.begin();
        player.draw(batch, 0.4f);
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
