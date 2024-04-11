package com.svalero.spaceinvaders.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.svalero.spaceinvaders.Utils.FontUtils;

public class WinnerGameScreen implements Screen {

    private Stage stage;

    @Override
    public void show() {
        if (!VisUI.isLoaded()) {
            VisUI.load();
        }

        stage = new Stage();

        Texture backgroundTexture = new Texture(Gdx.files.internal("background/game.png"));
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        VisTable table = new VisTable(true);
        table.setFillParent(true);
        stage.addActor(table);

        // Configuración de estilo de texto
        BitmapFont font = FontUtils.generateFont(24);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        // Label con el mensaje de victoria
        Label winnerLabel = new Label("¡¡HAS DERROTADO A LA FLOTA ENEMIGA!!", labelStyle);
        table.add(winnerLabel).center().padBottom(50).row();

        // Botón para volver al menú principal
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;

        VisTextButton mainMenuButton = new VisTextButton("MAIN MENU");
        mainMenuButton.setStyle(textButtonStyle);
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Ir al menú principal
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });

        table.add(mainMenuButton).center().width(300).height(150).pad(10);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dibujar la UI en la pantalla
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        if (VisUI.isLoaded()) {
            VisUI.dispose();
        }
    }
}
