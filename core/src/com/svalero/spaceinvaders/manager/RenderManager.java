package com.svalero.spaceinvaders.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.svalero.spaceinvaders.Utils.HudUtils;
import com.svalero.spaceinvaders.domain.*;

public class RenderManager implements Disposable {

    SpriteBatch batch;
    SpriteManager spriteManager;
    Texture background = new Texture("background/game.png");
    HudUtils hud;
    private float bossScale = 2f;


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

    private void drawMissilePlayer(){
        for (Missile missile : spriteManager.player.getMissiles()){
            missile.draw(batch);
        }
    }

    private void drawMissileBoss(){
        for (Missile missile : spriteManager.boss.getMissiles()){
            missile.draw(batch);
        }
    }

    private void drawAsteroids(){
        for (Asteroid asteroid : spriteManager.fallAsteroids){
            asteroid.draw(batch, 1.5f);
        }
    }

    private void drawPowerUps(){
        for (ExtraLifePowerUp powerUp : spriteManager.fallExtraLifes){
            powerUp.draw(batch);
        }
        for (DoubleScorePowerUp powerUp : spriteManager.fallDoubleScore){
            powerUp.draw(batch);
        }
        for (SpeedPowerUp powerUp : spriteManager.fallSpeedPowerUp){
            powerUp.draw(batch);
        }
    }

    private void drawEnemies(float dt){
        spriteManager.enemies.update(dt);
        spriteManager.enemies.draw(batch);
    }

    private void drawBoss(float dt){
        spriteManager.boss.moveBoss(dt);
        spriteManager.boss.draw(batch, bossScale);
        if (spriteManager.boss.resize){
            bossScale = 3f;
        }
        if (spriteManager.boss.shieldActive) {
            // Actualización del escudo
            spriteManager.boss.updateShield(dt);
            batch.draw(spriteManager.boss.shieldTexture,
                    spriteManager.boss.getPosition().x,
                    spriteManager.boss.getPosition().y);
        }
    }

    public void draw(float dt){
        if (!spriteManager.pause){
            ScreenUtils.clear(1, 0, 0, 1);

            batch.begin();

            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            if (spriteManager.player.isAlive()){
                drawPlayer();
                drawMissilePlayer();
            }
            drawPowerUps();
            drawEnemies(dt);
            drawAsteroids();
            hud.render();
            spriteManager.player.renderExplosion(batch);
            batch.end();
        }

    }

    public void drawBossScreen(float dt){
        if (!spriteManager.pause){
            ScreenUtils.clear(1, 0, 0, 1);

            batch.begin();

            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            if (spriteManager.player.isAlive()){
                drawPlayer();
                drawMissilePlayer();
            }
            if (spriteManager.boss.isAlive()){
                drawBoss(dt);
                drawMissileBoss();
            }
            drawPowerUps();
            drawAsteroids();
            hud.render();
            spriteManager.player.renderExplosion(batch);
            spriteManager.boss.renderExplosion(batch);
            batch.end();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
    }
}