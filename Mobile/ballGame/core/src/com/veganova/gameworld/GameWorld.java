package com.veganova.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;

import com.veganova.gameobjects.Ball;


public class GameWorld {

	//private Circle circle = new Circle(5,20,2);
	private Ball ball;
	public GameWorld()
	{
		ball = new Ball(10,400,16);
	}
	public void update(float delta) {
		 //Gdx.app.log("GameWorld", "update");
		 ball.update(delta);
		
		
	}
	
	public Ball getBall()
	{
		return ball;
	}

}
