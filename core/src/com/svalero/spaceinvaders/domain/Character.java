package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.svalero.spaceinvaders.SpaceInvaders;

public class Character implements Disposable {

    public Texture texture;
    public Vector2 position;
    public Rectangle rect;

    public Character(Texture texture, Vector2 position) {
        this.texture = texture;
        this.position = position;
        rect = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public void draw (SpriteBatch batch, float scale){
        batch.draw(texture, position.x, position.y, texture.getWidth() * scale, texture.getHeight() * scale);
    }

    public void move(float x, float y){
        position.add(x, y);
        rect.x += x;
        rect.y += y;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
