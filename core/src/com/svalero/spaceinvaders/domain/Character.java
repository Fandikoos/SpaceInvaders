package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.svalero.spaceinvaders.SpaceInvaders;
import com.svalero.spaceinvaders.manager.ResourceManager;

public class Character implements Disposable {

    public Vector2 position;
    public Rectangle rect;
    private Animation<TextureRegion> animation;
    private float stateTime;
    public TextureRegion currentFrame;

    public Character(Vector2 position, String animationName) {
        this.position = position;

        animation = new Animation<>(0.15f, ResourceManager.getAnimation(animationName));
        currentFrame = animation.getKeyFrame(0);

        rect = new Rectangle(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    public void draw (SpriteBatch batch, float scale){
        stateTime += Gdx.graphics.getDeltaTime();

        currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, position.x, position.y, currentFrame.getRegionWidth() * scale, currentFrame.getRegionHeight() * scale);
    }

    public void move(float x, float y){
        position.add(x, y);
        rect.x += x;
        rect.y += y;
    }

    @Override
    public void dispose() {
    }
}
