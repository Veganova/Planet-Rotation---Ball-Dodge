package com.veganova.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import com.veganova.gameworld.GameRenderer;
import com.veganova.gameworld.GameWorld;
import com.veganova.helpers.InputHandler;


public class GameScreen implements Screen{

	private GameRenderer renderer;
	private GameWorld world;
	private float runTime = 0;
	public GameScreen()
	{
		Gdx.app.log("GameScreen", "Attached");
		world = new GameWorld(); // Q: WHY CANT THESE BE MADE WHERE VARIABLES ARE DECLARED?
		renderer = new GameRenderer(world); 
		Gdx.input.setInputProcessor(new InputHandler(world.getBall()));
	}
	@Override
    public void render(float delta) {
		runTime += delta;
		world.update(delta);
		 //FPS 
		//System.out.println(1.0/delta);
		renderer.render(runTime);
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");     
    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");        
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");       
    }

    @Override
    public void dispose() {
        // Leave blank
    }

}
