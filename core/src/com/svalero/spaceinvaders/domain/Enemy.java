package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Character{


    public Enemy(Texture texture, Vector2 position) {
        super(texture, position);
    }

    public Vector2 getPosition(){
        return position;
    }

    public Texture getTexture() {
        return texture;
    }

}
