package com.svalero.spaceinvaders;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.svalero.spaceinvaders.Utils.HudUtils;
import com.svalero.spaceinvaders.screen.BossScreen;
import com.svalero.spaceinvaders.screen.MainMenuScreen;

public class SpaceInvaders extends Game {

	@Override
	public void create () {
		((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
		//((Game) Gdx.app.getApplicationListener()).setScreen(new BossScreen());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
