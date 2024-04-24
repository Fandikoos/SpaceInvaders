package com.svalero.spaceinvaders;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("SpaceInvaders");
		config.setWindowedMode(1980, 980); // Establece el tamaño de la ventana
		new Lwjgl3Application(new SpaceInvaders(), config);
	}
}
