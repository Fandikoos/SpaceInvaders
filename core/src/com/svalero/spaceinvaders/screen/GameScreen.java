package com.svalero.spaceinvaders.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.svalero.spaceinvaders.Utils.HudUtils;
import com.svalero.spaceinvaders.Utils.PreferencesUtils;
import com.svalero.spaceinvaders.domain.Player;
import com.svalero.spaceinvaders.manager.*;


public class GameScreen implements Screen {
    SpriteBatch batch;
    Preferences prefs;
    SpriteManager spriteManager;
    RenderManager renderManager;
    Player player;
    HudUtils hud;


    @Override
    public void show() {
        ResourceManager.loadAllResources();
        while (!ResourceManager.update()) {}

        MusicManager.stopMenuMusic();
        player = new Player("ship", new Vector2(Gdx.graphics.getWidth() / 2, 60), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hud = new HudUtils(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), player);
        spriteManager = new SpriteManager(player, hud);
        renderManager = new RenderManager(spriteManager, hud);
        prefs = PreferencesUtils.getPrefs();

        if (prefs.getBoolean("sound")){
            MusicManager.playGameMusic();
        } else {
            MusicManager.stopGameMusic();
        }
    }


    @Override
    public void render(float dt) {

        spriteManager.update(dt);
        renderManager.draw(dt);
        hud.update(player);

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
    }
}