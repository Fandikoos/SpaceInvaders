package com.svalero.spaceinvaders.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.ArrayList;
import java.util.List;

public class EnemyFleet {
    private List<Enemy> enemies;
    private float speedEnemiesX = 90; //Velocidad horizontal que lleva la flota
    private boolean moveRight = true; //Controlamos la dirección de movimiento
    private float screenWidth = Gdx.graphics.getWidth();
    private float fleetWidth;
    private float limitFleetWidthX = 120;

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

        public void update(float delta){
            if (moveRight){
                moveRightPosition(delta);
            } else {
                moveLeftPosition(delta);
            }
            checkBounds(screenWidth);
        }

        private void moveRightPosition(float delta){
            float maxEnemyX = Float.MIN_VALUE;
            for (Enemy enemy : enemies){
                enemy.move(speedEnemiesX * delta, 0);
                if (enemy.getPosition().x > maxEnemyX){
                    maxEnemyX = enemy.getPosition().x;
                }
            }
            fleetWidth = maxEnemyX + enemies.get(0).getTexture().getWidth();
        }

        private void moveLeftPosition(float delta){
            float minEnemyX = Float.MAX_VALUE;
            for (Enemy enemy : enemies){
                enemy.move(-speedEnemiesX * delta, 0);
                if (enemy.getPosition().x < minEnemyX){
                    minEnemyX = enemy.getPosition().x;
                }
                //Reflejamos el ancho total que tiene la flota de naves enemigas
                fleetWidth = enemies.get(0).getPosition().x + enemies.get(0).getTexture().getWidth();
            }

        }

        public void checkBounds(float screenWidth){
            if (fleetWidth >= screenWidth){
                moveRight = false; //Cambiar de direccion hacia la izquierda
            }
            if (fleetWidth <= limitFleetWidthX){
                moveRight = true; //Cambiar de direccion hacia la derecha
            }
        }


        public List<Enemy> getEnemies(){
            return enemies;
        }


}
