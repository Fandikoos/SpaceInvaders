package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Player extends Character{

    private float screenWidth;

    public Player(Texture texture, Vector2 position, float screenWidth) {
        super(texture, position);
        this.screenWidth = screenWidth;
    }

    //Movimiento de la Nave
    public void manageInput(){
        float speed = 20;
        double rightLimit = 2000.0;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rect.x < rightLimit - rect.width){
            move(speed, 0);
            System.out.println(screenWidth);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && rect.x > 0){
            move(-speed, 0);
        }

    }
}
