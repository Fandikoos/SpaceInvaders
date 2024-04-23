package com.svalero.spaceinvaders.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class MusicManager {
    private static Music music;
    private static Music gameGalaxy;


    public static void playMenuMusic(){
        if (music == null){
            music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/intro.wav"));
            music.setLooping(true);
            music.play();
        }
    }

    public static void stopMenuMusic(){
        if (music != null){
            music.stop();
            music.dispose();
            music = null;
        }
    }

    public static void playGameMusic(){
        if(gameGalaxy == null){
            gameGalaxy = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/Galaxy.mp3"));
            gameGalaxy.setLooping(true);
            gameGalaxy.play();
        }
    }

    public static void stopGameMusic(){
        if (gameGalaxy != null){
            gameGalaxy.stop();
            gameGalaxy.dispose();
            gameGalaxy = null;
        }
    }

}
