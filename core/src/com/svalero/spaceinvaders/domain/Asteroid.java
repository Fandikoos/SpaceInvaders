package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Asteroid extends Character{
    private float asteroidSpeed;

    public Asteroid(Texture texture, float screenWidth, float screenHeight) {
        super(texture, new Vector2(MathUtils.random(0, screenWidth - texture.getWidth()), screenHeight));
        this.asteroidSpeed = 80;
    }

    public void update (float delta){
        //Movemos el asteroide
        move(0, -asteroidSpeed * delta);
    }
}
