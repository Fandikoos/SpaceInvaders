package com.svalero.spaceinvaders.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {
    private static Music music;

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
}
