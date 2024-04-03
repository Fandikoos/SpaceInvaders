package com.svalero.spaceinvaders.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;
import com.svalero.spaceinvaders.Utils.FontUtils;
import com.svalero.spaceinvaders.manager.MusicManager;
import com.svalero.spaceinvaders.Utils.PreferencesUtils;

public class PreferenceScreen implements Screen {
    Stage stage;
    Preferences prefs;

    private void loadScreen() {
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
        table.center();

        VisLabel title = new VisLabel("SETTINGS");
        title.setFontScale(2.5f);
        title.setColor(Color.WHITE);
        title.setStyle(new Label.LabelStyle(FontUtils.generateFont(24), Color.WHITE));


        final VisCheckBox checkSound = new VisCheckBox("SOUND");
        checkSound.setChecked(prefs.getBoolean("sound"));
        checkSound.setColor(Color.WHITE);
        checkSound.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                prefs.putBoolean("sound", checkSound.isChecked());
            }
        });

        VisLabel difficultyLabel = new VisLabel("-- DIFFICULTY --");
        difficultyLabel.setColor(Color.WHITE);
        difficultyLabel.setStyle(new Label.LabelStyle(FontUtils.generateFont(24), Color.WHITE)); // Aplicar estilo de fuente

        String[] resolutionsArray = {"LOW", "MEDIUM", "HIGH"};
        final VisList difficultyList = new VisList();
        difficultyList.setItems(resolutionsArray);
        difficultyList.setColor(Color.WHITE);
        //difficultyList.setStyle(new List.ListStyle(FontUtils.generateFont(24), Color.WHITE, Color.WHITE, new TextureRegionDrawable())); // Aplicar estilo de fuente
        difficultyList.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                switch (difficultyList.getSelectedIndex()) {
                    case 0:
                        prefs.putString("difficulty", "low");
                        break;
                    case 1:
                        prefs.putString("difficulty", "medium");
                        break;
                    case 2:
                        prefs.putString("difficulty", "high");
                        break;
                    default:
                }
            }
        });

        VisTextButton exitButton = new VisTextButton("MAIN MENU");
        exitButton.setColor(Color.WHITE); // Cambio de color para que sea visible en el fondo
        exitButton.setStyle(new TextButton.TextButtonStyle(null, null, null, FontUtils.generateFont(24))); // Aplicar estilo de fuente
        exitButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                prefs.flush();
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });

        table.row().height(150);
        table.add(title).center().pad(35f);
        table.row().height(20);
        table.add(checkSound).center().pad(35f);
        table.row().height(20);
        table.add(difficultyLabel).center().pad(35f);
        table.row().height(70);
        table.add(difficultyList).center().pad(5f);
        table.row().height(70);
        table.add(exitButton).center().width(300).pad(5f);
        table.row().height(70);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private void loadPreferences() {
        prefs = Gdx.app.getPreferences("SpaceInvaders");

        if (!prefs.contains("sound"))
            prefs.putBoolean("sound", true);
    }

    @Override
    public void show() {
        loadPreferences();
        loadScreen();
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Mantenemos el fondo negro para que los componentes sean visibles
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(dt);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resize(int width, int height) {
        //stage.setViewport(width, height);
    }

    @Override
    public void resume() {
    }
}
