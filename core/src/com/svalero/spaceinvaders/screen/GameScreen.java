package com.svalero.spaceinvaders.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.svalero.spaceinvaders.Utils.HudUtils;
import com.svalero.spaceinvaders.Utils.PreferencesUtils;
import com.svalero.spaceinvaders.domain.Player;
import com.svalero.spaceinvaders.manager.MusicManager;
import com.svalero.spaceinvaders.manager.RenderManager;
import com.svalero.spaceinvaders.manager.ResourceManager;
import com.svalero.spaceinvaders.manager.SpriteManager;


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
        spriteManager = new SpriteManager();
        hud = new HudUtils();
        renderManager = new RenderManager(spriteManager, hud);
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
