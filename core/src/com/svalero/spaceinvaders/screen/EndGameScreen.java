package com.svalero.spaceinvaders.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.svalero.spaceinvaders.Utils.FontUtils;
import com.svalero.spaceinvaders.Utils.PreferencesUtils;

public class EndGameScreen implements Screen {

    private Stage stage;
    private Preferences prefs;

    @Override
    public void show() {
        if (!VisUI.isLoaded()) {
            VisUI.load();
        }

        stage = new Stage();
        prefs = PreferencesUtils.getPrefs();

        Texture backgroundTexture = new Texture(Gdx.files.internal("background/game.png"));
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        VisTable table = new VisTable(true);
        table.setFillParent(true);
        stage.addActor(table);

        // Configuración de estilo de texto
        BitmapFont font = FontUtils.generateFont(24);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;

        VisTextButton retryButton = new VisTextButton("TRY AGAIN");
        retryButton.setStyle(textButtonStyle);
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Volver a intentar
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });

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

        // Añade filas a la tabla y añade los componentes
        table.row().padBottom(20);
        table.add(retryButton).center().width(300).height(150).pad(10);
        table.row().padBottom(20);
        table.add(mainMenuButton).center().width(300).height(150).pad(10);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Pinta la UI en la pantalla
        stage.act(dt);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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
    }
}
