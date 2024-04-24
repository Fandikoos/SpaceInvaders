package com.svalero.spaceinvaders.screen;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.svalero.spaceinvaders.Utils.HudUtils;
import com.svalero.spaceinvaders.Utils.PreferencesUtils;
import com.svalero.spaceinvaders.domain.Boss;
import com.svalero.spaceinvaders.domain.Player;
import com.svalero.spaceinvaders.manager.*;


public class BossScreen implements Screen {

    SpriteBatch batch;
    Preferences prefs;
    SpriteManager spriteManager;
    RenderManager renderManager;
    Player player;
    Boss boss;
    HudUtils hud;

    // Constructor que toma el jugador y el HUD como parámetros
    public BossScreen(Player player, HudUtils hud, SpriteManager spriteManager, RenderManager renderManager) {
        this.player = player;
        this.hud = hud;
        this.spriteManager = spriteManager;
        this.renderManager = renderManager;
        prefs = PreferencesUtils.getPrefs();
    }

    @Override
    public void show() {
        ResourceManager.loadAllResources();
        while (!ResourceManager.update()) {}

        MusicManager.stopMenuMusic();
        prefs = PreferencesUtils.getPrefs();
    }

    @Override
    public void render(float dt) {
        spriteManager.updateBoss(dt);
        renderManager.drawBossScreen(dt);

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
        boss.dispose();
    }
}