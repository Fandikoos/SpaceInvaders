package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Missile {

    private Texture texture;
    private Vector2 position;
    private Rectangle bounds;

    public Missile (Texture texture, Vector2 position) {
        this.texture = texture;
        this.position = position;
        this.bounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, position.x, position.y);  //Dibujar el misil
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void move(float dx, float dy){
        position.x += dx;
        position.y += dy;
        bounds.setPosition(position);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return texture;
    }
}
