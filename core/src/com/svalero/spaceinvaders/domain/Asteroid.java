package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Asteroid extends Enemy{
    private float asteroidSpeed;
    private TextureRegion textureRegion;
    private float screenWidth;


    public Asteroid(Vector2 position, TextureRegion textureRegion, String animationName, float screenWidth, float screenHeight) {
        super(position, animationName);
        this.textureRegion = textureRegion;
        this.screenWidth = screenWidth;
        this.asteroidSpeed = 80;
        // Asegúrate de que la textura se haya cargado antes de intentar obtener su ancho
        if (textureRegion != null) {
            float x = MathUtils.random(0, screenWidth - textureRegion.getRegionWidth());
            this.getPosition().set(x, screenHeight); // Asignamos directamente la posición al objeto Vector2 de la clase Enemy
        }
    }

    public void update (float delta){
        //Movemos el asteroide
        move(0, -asteroidSpeed * delta);
    }
}
