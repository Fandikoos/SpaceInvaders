package com.svalero.spaceinvaders.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.svalero.spaceinvaders.Utils.FontUtils;
import com.svalero.spaceinvaders.Utils.HudUtils;
import com.svalero.spaceinvaders.Utils.PreferencesUtils;
import com.svalero.spaceinvaders.domain.Boss;
import com.svalero.spaceinvaders.domain.Player;
import com.svalero.spaceinvaders.manager.*;


public class BossScreen implements Screen {

    SpriteBatch batch;
    Preferences prefs;
    SpriteManager spriteManager;
    RenderManager renderManager;
    Player player;
    Boss boss;
    HudUtils hud;
    Stage stage;

    // Constructor que toma el jugador y el HUD como parámetros
    public BossScreen(Player player, HudUtils hud, SpriteManager spriteManager, RenderManager renderManager) {
        this.player = player;
        this.hud = hud;
        this.spriteManager = spriteManager;
        this.renderManager = renderManager;
        prefs = PreferencesUtils.getPrefs();
    }

    @Override
    public void show() {
        ResourceManager.loadAllResources();
        while (!ResourceManager.update()) {}

        MusicManager.stopMenuMusic();
        prefs = PreferencesUtils.getPrefs();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float dt) {
        spriteManager.updateBoss(dt);
        renderManager.drawBossScreen(dt);

        if (spriteManager.isPaused()){
            drawPauseMenu();
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Actuar sobre el Stage
        stage.draw();
    }

    private void drawPauseMenu() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Agregar título al menú de pausa
        Label title = new Label("Paused", new Label.LabelStyle(FontUtils.generateFont(24), Color.WHITE));
        table.add(title).colspan(2).center();
        table.row().padTop(20);

        // Agregar botones para las opciones del menú de pausa
        TextButton resumeButton = new TextButton("Resume", VisUI.getSkin());
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Reanudar el juego
                spriteManager.pause = false;
                stage.clear();
            }
        });
        table.add(resumeButton).width(200).height(50).center();

        TextButton soundButton = new TextButton("", VisUI.getSkin()); // Inicialmente sin texto
        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Alternamos el estado del sonido
                boolean soundEnabled = ConfigurationManager.isSoundEnabled();
                if (soundEnabled) {
                    ConfigurationManager.disableSound();
                    MusicManager.stopGameMusic();
                    soundButton.setText("Sound Off"); // Cambiamos el texto a Sound off
                } else {
                    ConfigurationManager.enableSound();
                    MusicManager.playGameMusic();
                    soundButton.setText("Sound On"); // Cambiamos el texto a Sound on
                }
            }
        });

        // Establecemos el texto inicial del botón en funcion de las prefs
        if (ConfigurationManager.isSoundEnabled()) {
            soundButton.setText("Sound On");
        } else {
            soundButton.setText("Sound Off");
        }

        table.add(soundButton).width(200).height(50).center();

        TextButton mainMenuButton = new TextButton("Main Menu", VisUI.getSkin());
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Volver al menú principal
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });
        table.row().padTop(10);
        table.add(mainMenuButton).width(200).height(50).center();

        TextButton exitButton = new TextButton("Exit", VisUI.getSkin());
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Salir del juego
                Gdx.app.exit();
            }
        });
        table.add(exitButton).width(200).height(50).center();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
        batch.dispose();
        boss.dispose();
    }
}