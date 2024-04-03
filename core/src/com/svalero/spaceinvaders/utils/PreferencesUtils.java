package com.svalero.spaceinvaders.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferencesUtils {

    private static Preferences prefs;

    public static Preferences getPrefs(){
        if (prefs == null){
            prefs = Gdx.app.getPreferences("SpaceInvaders");
        }
        return prefs;
    }
}
