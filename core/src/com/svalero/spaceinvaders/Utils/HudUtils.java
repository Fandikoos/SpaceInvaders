package com.svalero.spaceinvaders.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.svalero.spaceinvaders.domain.Player;

public class HudUtils {

    public int lives;
    public int score;
    private int level;
    BitmapFont font;
    private Texture heart;
    private Texture coin;


    public HudUtils() {
        level = 1;

        font = new BitmapFont();
        heart = new Texture(Gdx.files.internal("hud/heart.png"));
        coin = new Texture(Gdx.files.internal("hud/score.png"));

    }

    public void render(SpriteBatch batch) {

        // Dibujar las vidas del jugador
        batch.draw(heart, 10, Gdx.graphics.getHeight() - heart.getHeight() - 10);
        font.draw(batch, " x " + lives, 10 + heart.getWidth() + 5, Gdx.graphics.getHeight() - heart.getHeight() / 2 + font.getLineHeight() / 2 - 10);

        // Dibujar la puntuación del jugador
        GlyphLayout layout = new GlyphLayout(font, Integer.toString(score));
        float coinX = 10;
        float coinY = Gdx.graphics.getHeight() - heart.getHeight() - coin.getHeight() - 10;
        float scoreX = coinX + coin.getWidth() + 5;
        float scoreY = coinY + coin.getHeight() / 2 + layout.height / 2;
        batch.draw(coin, coinX, coinY);
        font.draw(batch, Integer.toString(score), scoreX, scoreY);

        // Dibujar el nivel
        layout.setText(font, "Nivel: " + level);
        float levelX = scoreX + layout.width + 20; // Desplazamiento para dejar espacio entre la puntuación y el nivel
        float levelY = scoreY; // Mismo nivel vertical que la puntuación para que quede alineado
        font.draw(batch, "Nivel: " + level, levelX, levelY);

    }

    public void dispose() {
        heart.dispose();
        coin.dispose();
        font.dispose();
    }
}
