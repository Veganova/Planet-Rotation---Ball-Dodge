package com.veganova.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.veganova.gameobjects.Ball;
import com.veganova.gameobjects.Movable;
import com.veganova.gameobjects.Planet;

public class InputHandler implements InputProcessor {
    private Ball myball;
    private Planet myplanet;


    public InputHandler(Movable ball, Planet planet){
        myball = (Ball) ball;
        myplanet = planet;
    }
    @Override
    public boolean keyDown(int keycode) {
        // TODO Auto-generated method stub
        //22 = right
        //21 = left
        if(keycode == 62)//spacebar
            myball.onPress();

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
//        if(keycode == 74)//j
//        {
            myplanet.TEMP_DEBUG = false;


        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        if(character=='h') { //debugging tools
            double angle = Math.atan2(-Gdx.input.getY()+360, Gdx.input.getX()-240);
            System.out.println( "-----------------\n" +
                    "X: " +(Gdx.input.getX()-240) + ", Y:  " + (360.0-Gdx.input.getY()) + "\n" +
                    "Angle: " + ((angle * 180.0 / Math.PI)+360.0) % 360 + " Radius: " + myball.getr() +
                    "\n-----------------\n");
        }
        else if(character =='j')
        {
            System.out.println("-----------------\n" +
                    "Ball Angle: " + myball.getAngle()*180.0/Math.PI +"\n" +
                    "Planet 1st Stub Angle: " + myplanet.getAngle()+
                    "\n-----------------\n");
            myplanet.TEMP_DEBUG = true;
        }
        else if(character == 's')
            myball.setdAngle(0);

        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;// Return true to say we handled the touch.
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }

}
