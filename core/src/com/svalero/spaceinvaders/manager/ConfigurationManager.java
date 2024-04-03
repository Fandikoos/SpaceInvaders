package com.svalero.spaceinvaders.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ConfigurationManager {

    private static Preferences prefs = Gdx.app.getPreferences("SpaceInvaders");

    public static boolean isSoundAlreadyConfigured() {
        return prefs.contains("sound");
    }

    public static boolean isSoundEnabled() {
        return prefs.getBoolean("sound");
    }

    public static void enableSound() {
        prefs.putBoolean("sound", true);
        prefs.flush();
    }



}
