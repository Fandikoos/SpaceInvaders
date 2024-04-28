package com.svalero.spaceinvaders.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.svalero.spaceinvaders.Utils.FontUtils;
import com.svalero.spaceinvaders.Utils.HudUtils;
import com.svalero.spaceinvaders.Utils.PreferencesUtils;
import com.svalero.spaceinvaders.domain.Player;
import com.svalero.spaceinvaders.manager.*;


public class GameScreen implements Screen {
    SpriteBatch batch;
    Preferences prefs;
    SpriteManager spriteManager;
    RenderManager renderManager;
    Player player;
    HudUtils hud;
    Stage stage;


    @Override
    public void show() {
        ResourceManager.loadAllResources();
        while (!ResourceManager.update()) {}

        MusicManager.stopMenuMusic();
        player = new Player("ship", new Vector2(Gdx.graphics.getWidth() / 2, 60), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hud = new HudUtils(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), player);
        spriteManager = new SpriteManager(player, hud, this); // Con el this el sprite manager tiene acceso al metodo de swithcToBossScreen
        renderManager = new RenderManager(spriteManager, hud);
        prefs = PreferencesUtils.getPrefs();

        if (prefs.getBoolean("sound")){
            MusicManager.playGameMusic();
        } else {
            MusicManager.stopGameMusic();
        }

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float dt) {

        spriteManager.update(dt);
        renderManager.draw(dt);
        hud.update(player);
        
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

    // Metodo para cambiar a la pantalla del Boss y pdoer mandarle el jugador actual, el hud actual, el sprite manager actual y el render manager actual
    // importante especialmente para poder pasar la informacion del hud al siguiente niven y que asi no se reinicie la info del hud
    public void switchToBossScreen(){
        ((Game) Gdx.app.getApplicationListener()).setScreen(new BossScreen(player, hud, spriteManager, renderManager));
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
    }
}