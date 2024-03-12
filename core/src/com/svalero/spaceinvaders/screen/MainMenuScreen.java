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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.svalero.spaceinvaders.Utils.FontUtils;

public class MainMenuScreen implements Screen {

    private Stage stage;

    @Override
    public void show() {
        if (!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();

        Texture backgroundTexture = new Texture(Gdx.files.internal("background/fondo_menu.jpg"));
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

        VisTextButton playButton = new VisTextButton("PLAY");
        playButton.setStyle(textButtonStyle);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Ir a jugar
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });

        VisTextButton configButton = new VisTextButton("CONFIGURATION");
        configButton.setStyle(textButtonStyle);
        configButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Ir a la pantalla de configuración
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PreferenceScreen());
            }
        });

        VisTextButton quitButton = new VisTextButton("QUIT");
        quitButton.setStyle(textButtonStyle);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                VisUI.dispose();
                System.exit(0);
            }
        });

        VisTextButton aboutUs = new VisTextButton("ABOUT US");
        aboutUs.setStyle(textButtonStyle);
        aboutUs.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                VisUI.dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new AboutUsScreen());
            }
        });

        // Añade filas a la tabla y añade los componentes
        table.row().padBottom(20);
        table.add(playButton).center().width(300).height(150).pad(10);
        table.row().padBottom(15);
        table.add(configButton).center().width(300).height(100).pad(10);
        table.row().padBottom(20);
        table.add(aboutUs).center().width(300).height(100).pad(10);
        table.row().padBottom(20);
        table.add(quitButton).center().width(300).height(100).pad(10);

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
