package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Character {

    public Enemy(Vector2 position, String animationName) {
        super(position, animationName);
    }

    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return currentFrame;
    }

    public Rectangle getBounds(){
        return new Rectangle(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }
}
