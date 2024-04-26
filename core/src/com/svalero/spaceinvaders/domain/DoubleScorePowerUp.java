package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DoubleScorePowerUp extends PowerUp{

    public DoubleScorePowerUp(TextureRegion textureRegion, float x, float y, float speed){
        super(textureRegion, x, y, speed);
    }

    public void update(float dt){
        position.y -= speed * dt;
    }

}
