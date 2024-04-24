package com.svalero.spaceinvaders.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svalero.spaceinvaders.domain.Player;


public class HudUtils {

    private Player player;
    private Stage stage;
    private Label livesLabel;
    private Label scoreLabel;
    private Image livesImage;
    private Image scoreImage;

    public HudUtils(Viewport viewport, Player player) {
        this.player = player;
        stage = new Stage(viewport);

        livesLabel = new Label("Lives: " + player.lives, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesLabel.setPosition(50, Gdx.graphics.getHeight() - 60);
        stage.addActor(livesLabel);

        scoreLabel = new Label("Score: " + player.score, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel.setPosition(50, Gdx.graphics.getHeight() - 100);
        stage.addActor(scoreLabel);


        TextureRegion heartImage = new TextureRegion(new Texture(Gdx.files.internal("hud/heart.png")));
        livesImage = new Image(heartImage);
        livesImage.setPosition(getPositionForImage(livesLabel, livesImage), livesLabel.getY() - 15);
        stage.addActor(livesImage);

        TextureRegion coinImage = new TextureRegion(new Texture(Gdx.files.internal("hud/score.png")));
        scoreImage = new Image(coinImage);
        scoreImage.setPosition(getPositionForImage(scoreLabel, scoreImage) - 10, scoreLabel.getY() - 25);
        stage.addActor(scoreImage);
    }

    public void update(Player player) {
        if (player != null){
            this.player = player;
            livesLabel.setText("Lives: " + player.lives);
            scoreLabel.setText("Score: " + player.score);
        }
    }

    public void render() {
        stage.act();
        stage.draw();
    }

    private float getPositionForImage(Label label, Image image) {
        float labelX = label.getX() + label.getWidth();
        return labelX + 10; // Espacio adicional entre la etiqueta y la imagen
    }

    public void dispose() {
        stage.dispose();
    }


}