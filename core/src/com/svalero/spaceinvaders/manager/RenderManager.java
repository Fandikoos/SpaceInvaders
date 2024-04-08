package com.svalero.spaceinvaders.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.svalero.spaceinvaders.Utils.HudUtils;
import com.svalero.spaceinvaders.domain.Asteroid;
import com.svalero.spaceinvaders.domain.Missile;

public class RenderManager implements Disposable {

    SpriteBatch batch;
    SpriteManager spriteManager;
    Texture background = new Texture("background/game.png");
    boolean pause;
    HudUtils hud;


    public RenderManager(SpriteManager spriteManager, HudUtils hud){
        this.spriteManager = spriteManager;
        this.hud = hud;
        initialize();
    }

    private void initialize(){
        batch = new SpriteBatch();
    }

    private void drawPlayer(){
        spriteManager.player.draw(batch, 1.5f);
    }

    private void drawMissile(){
        for (Missile missile : spriteManager.player.getMissiles()){
            missile.draw(batch);
        }
    }

    private void drawAsteroids(){
        for (Asteroid asteroid : spriteManager.fallAsteroids){
            asteroid.draw(batch, 1.5f);
        }
    }

    private void drawEnemies(float dt){
        spriteManager.enemies.update(dt);
        spriteManager.enemies.draw(batch);
    }

    public void draw(float dt){
        if (!spriteManager.pause){
            ScreenUtils.clear(1, 0, 0, 1);

            batch.begin(); // ¡Asegúrate de llamar a batch.begin() aquí!

            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            drawPlayer();
            drawEnemies(dt);
            drawAsteroids();
            drawMissile();
            hud.render();

            batch.end();
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
    }
}