package com.svalero.spaceinvaders.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svalero.spaceinvaders.domain.Player;



public class HudUtils {

    private Player player;
    private Stage stage;
    private Label livesLabel;
    private Label scoreLabel;
    private Label levelLabel;

    public HudUtils(Viewport viewport, Player player) {
        this.player = player;
        stage = new Stage(viewport);

        livesLabel = new Label("Lives: " + player.lives, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesLabel.setPosition(20, Gdx.graphics.getHeight() - 20);
        stage.addActor(livesLabel);

        scoreLabel = new Label("Score: " + player.score, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel.setPosition(20, Gdx.graphics.getHeight() - 50);
        stage.addActor(scoreLabel);

        levelLabel = new Label("Level: " + player.level, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel.setPosition(20, Gdx.graphics.getHeight() - 80);
        stage.addActor(levelLabel);
    }

    public void update(Player player) {
        livesLabel.setText("Lives: " + player.lives);
        scoreLabel.setText("Score: " + player.score);
        levelLabel.setText("Level: " + player.level);
    }

    public void render() {
        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
}