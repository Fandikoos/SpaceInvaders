package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class EnemyFleet {
    private List<Enemy> enemies;

    public EnemyFleet(Texture texture, float screenWidth, float screenHeight){
        enemies = new ArrayList<>();
        float shipWidth = texture.getWidth();
        float shipHeight = texture.getHeight();
        float spaceBetweenShips = 5;

        int rows = 3;
        int cols = 6;
        float startX = (screenWidth - (cols * (shipWidth + spaceBetweenShips))) / 2;
        float startY = screenHeight - screenHeight / 4; // Ajusta la posición vertical según sea necesario

        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                float x = startX + col * (shipWidth + spaceBetweenShips);
                float y = startY + row * (shipHeight + spaceBetweenShips);
                enemies.add(new Enemy(texture, new Vector2(x, y)));
                }
            }
        }

        public void draw(SpriteBatch batch) {
            for (Enemy enemy : enemies) {
                enemy.draw(batch, 0.8f);
            }
        }

        public List<Enemy> getEnemies(){
        return enemies;
    }





}
