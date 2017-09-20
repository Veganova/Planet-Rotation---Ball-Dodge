package com.veganova.ballgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.veganova.helpers.AssetLoader;
import com.veganova.screens.GameScreen;

public class ballGame extends Game  {

	@Override
	public void create() {
		Gdx.app.log("ballGame", "created");
		AssetLoader.load();
		setScreen(new GameScreen());
	}
	
	@Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
	
}

