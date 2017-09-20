package com.veganova.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Ball {

	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;

	private float x, y ,dx, dy, accelx, accely;
	private float rotation; // For handling bird rotation
	private float radius;//32 pixels x 32 pixels image
	private float angle;
	public Ball(float x, float y, float r) {
        radius = r;
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
        this.accelx = 0;
        this.accely = -5;
    }
	
	public void update(float delta) {

        //velocity.add(acceleration.cpy().scl(delta));
        dx += accelx * delta;
        dy += accely * delta;

        if (dy > 200) {
            dy = 200;
        }
        //position.add(velocity.cpy().scl(delta));
        x += dx*delta;
        y += dy*delta;
    }

    public void onClick() {
        dy = 10;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public float getRotation() {
        return rotation;
    }
    
    public float getAngle(){
    	return angle;
    }
	
	
}
	