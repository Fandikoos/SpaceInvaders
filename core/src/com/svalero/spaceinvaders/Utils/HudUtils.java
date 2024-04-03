package com.svalero.spaceinvaders.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HudUtils {

    private Stage stage;
    private Viewport viewport;
    private BitmapFont font;
    private int lives;
    private int score;
    private int level;
    private Texture heartTexture;
    private Texture scoreTexture;

    public HudUtils(Viewport viewport){
        this.viewport = viewport;
        this.stage = new Stage(viewport);
        this.font = FontUtils.generateFont(12);
        this.lives = 3;
        this.score = 0;
        this.level = 1;

        this.heartTexture = new Texture("hud/heart.png");
        this.scoreTexture = new Texture("hud/score.png");
    }

    public void draw(SpriteBatch batch){
        batch.begin();
        float y = Gdx.graphics.getHeight() - 10;
        float x = 10;

        for (int i = 0; i <lives; i++){
            batch.draw(heartTexture, x + i * (heartTexture.getWidth() + 5), y - heartTexture.getHeight());
        }
        float averageY = (10 + heartTexture.getHeight() + 100) / 2f;

        batch.draw(scoreTexture, x, y - heartTexture.getHeight() - scoreTexture.getHeight());
        float scoreY = y - heartTexture.getHeight() - (scoreTexture.getHeight() - font.getLineHeight()) / 2;
        font.draw(batch, Integer.toString(score), x + scoreTexture.getWidth() + 5, scoreY);

        font.draw(batch, "Nivel actual: " + level, x, y - heartTexture.getHeight() - scoreTexture.getHeight() - font.getLineHeight());

        batch.end();
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void dispose() {
        stage.dispose();
        heartTexture.dispose();
        scoreTexture.dispose();
        font.dispose();
    }

    public Matrix4 getProjectionMatrix() {
        return stage.getCamera().combined;
    }
}
