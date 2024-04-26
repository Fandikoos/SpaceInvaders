package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PowerUp {

    public Vector2 position;
    public TextureRegion textureRegion;
    public float speed;

    public PowerUp(TextureRegion textureRegion, float x, float y, float speed){
        this.textureRegion = textureRegion;
        this.position = new Vector2(x, y);
        this.speed = speed;
    }

    public void update(float dt){
        position.y -= speed * dt;
    }

    public void draw(SpriteBatch batch){
        batch.draw(textureRegion, position.x, position.y);
    }

    public Rectangle getBounds(){
        return new Rectangle(position.x, position.y, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }

}
