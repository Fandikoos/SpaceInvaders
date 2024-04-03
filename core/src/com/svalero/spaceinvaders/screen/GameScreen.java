package com.svalero.spaceinvaders.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.svalero.spaceinvaders.Utils.PreferencesUtils;
import com.svalero.spaceinvaders.manager.MusicManager;
import com.svalero.spaceinvaders.manager.RenderManager;
import com.svalero.spaceinvaders.manager.SpriteManager;


public class GameScreen implements Screen {
    SpriteBatch batch;
    Preferences prefs;
    SpriteManager spriteManager;
    RenderManager renderManager;

    @Override
    public void show() {
        MusicManager.stopMenuMusic();
        spriteManager = new SpriteManager();
        renderManager = new RenderManager(spriteManager);
        prefs = PreferencesUtils.getPrefs();
    }


    @Override
    public void render(float dt) {

        spriteManager.update(dt);
        renderManager.draw(dt);

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
