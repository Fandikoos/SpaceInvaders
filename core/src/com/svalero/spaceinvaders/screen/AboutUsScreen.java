package com.svalero.spaceinvaders.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.svalero.spaceinvaders.Utils.FontUtils;
import com.svalero.spaceinvaders.Utils.MusicManager;
import com.svalero.spaceinvaders.Utils.PreferencesUtils;

public class AboutUsScreen implements Screen {
    private Stage stage;
    private Music backgroungMusic;
    Preferences prefs;
    @Override
    public void show() {
        if (!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();
        prefs = PreferencesUtils.getPrefs();

        if (prefs.getBoolean("sound")){
            MusicManager.playMenuMusic();
        } else {
            MusicManager.stopMenuMusic();
        }

        Texture backgroundTexture = new Texture(Gdx.files.internal("background/fondo_menu.jpg"));
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        VisTable table = new VisTable(true);
        table.setFillParent(true);
        stage.addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = FontUtils.generateFont(24);
        labelStyle.fontColor = Color.WHITE;

        VisLabel titleLabel = new VisLabel("About Us");
        titleLabel.setFontScale(2.0f);
        titleLabel.setStyle(labelStyle);
        table.add(titleLabel).expandX().top().padTop(50).row();

        String aboutText = "¡Hola! Somos el equipo de desarrollo de este increíble juego.\n" +
                "Estamos comprometidos a brindarte la mejor experiencia de juego posible.\n" +
                "¡Esperamos que disfrutes jugando tanto como nosotros disfrutamos creando este juego!\n\n" +
                "¡Gracias por tu apoyo!";

        VisLabel aboutLabel = new VisLabel(aboutText);
        aboutLabel.setStyle(labelStyle);
        aboutLabel.setWrap(true);
        aboutLabel.setAlignment(Align.center);
        table.add(aboutLabel).expand().fillX().pad(20).row();

        VisTextButton backButton = new VisTextButton("BACK TO MAIN MENU");
        backButton.setColor(Color.WHITE);
        backButton.setStyle(new TextButton.TextButtonStyle(null, null, null, FontUtils.generateFont(24)));
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });

        table.row().height(150);
        table.add(backButton).center().width(300).height(70).padTop(20);
        table.row().height(70);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(dt);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
