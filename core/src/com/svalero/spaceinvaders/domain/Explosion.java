package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Explosion {

    private Animation<TextureRegion> explosionAnimation;
    private float stateTime;
    private boolean explosionFinished;

    public Explosion(Array<TextureAtlas.AtlasRegion> explosionRegions){
        explosionAnimation = new Animation<>(0.1f, explosionRegions, Animation.PlayMode.NORMAL);
        stateTime = 0f;
        explosionFinished = false;
    }

    public void update(float dt) {
        stateTime += dt;
        if (explosionAnimation.isAnimationFinished(stateTime)) {
            explosionFinished = true;
        }
    }

    public void drawPlayer(SpriteBatch batch, Vector2 position) {
        TextureRegion currentFrame = explosionAnimation.getKeyFrame(stateTime);
        float x = position.x - currentFrame.getRegionWidth() / 2 + 20;
        float y = position.y - currentFrame.getRegionHeight() / 2 + 20;
        batch.draw(currentFrame, x, y);
    }

    public void drawBoss(SpriteBatch batch, Vector2 position) {
        TextureRegion currentFrame = explosionAnimation.getKeyFrame(stateTime);
        float x = position.x - currentFrame.getRegionWidth() / 4;
        float y = position.y - currentFrame.getRegionHeight() / 4;
        batch.draw(currentFrame, x, y);
    }

    public boolean isFinished() {
        return explosionFinished;
    }
}
