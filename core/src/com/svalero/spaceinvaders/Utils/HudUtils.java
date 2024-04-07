package com.svalero.spaceinvaders.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HudUtils {

    private int lives;
    private int score;
    private int level;
    BitmapFont font;
    private Texture heart;
    private Texture coin;

    public HudUtils() {
        lives = 3;
        score = 0;
        level = 1;

        font = new BitmapFont();
        heart = new Texture(Gdx.files.internal("hud/heart.png"));
        coin = new Texture(Gdx.files.internal("hud/score.png"));
    }

    public void render(SpriteBatch batch) {
        // Dibujar las vidas
        for (int i = 0; i < lives; i++) {
            batch.draw(heart, 10 + i * (heart.getWidth() + 5), Gdx.graphics.getHeight() - heart.getHeight() - 10);
        }

        // Dibujar la puntuación
        GlyphLayout layout = new GlyphLayout(font, Integer.toString(score));
        float coinX = 10;
        float coinY = Gdx.graphics.getHeight() - heart.getHeight() - coin.getHeight() - 10;
        float scoreX = coinX + coin.getWidth() + 5; // Desplazamiento para alinear el número de la puntuación a la derecha del símbolo de la moneda
        float scoreY = coinY + coin.getHeight() / 2 + layout.height / 2; // Centrar verticalmente el número de la puntuación
        batch.draw(coin, coinX, coinY);
        font.draw(batch, Integer.toString(score), scoreX, scoreY);

        // Dibujar el nivel
        layout.setText(font, "Nivel: " + level);
        float levelX = scoreX + layout.width + 20; // Desplazamiento para dejar espacio entre la puntuación y el nivel
        float levelY = scoreY; // Mismo nivel vertical que la puntuación para que quede alineaado
        font.draw(batch, "Nivel: " + level, levelX, levelY);
    }

    // Métodos para actualizar vidas, puntuación y nivel
    public void setLives(int lives) {
        this.lives = lives;
    }

    public void incrementScore(int score) {
        this.score += score;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void dispose() {
        heart.dispose();
        coin.dispose();
    }
}
