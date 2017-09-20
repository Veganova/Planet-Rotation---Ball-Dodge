package com.veganova.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;

import com.veganova.gameobjects.Ball;
import com.veganova.gameobjects.Comet;
import com.veganova.gameobjects.Movable;
import com.veganova.gameobjects.Planet;

import java.util.ArrayList;
import java.util.List;


public class GameWorld {

    //private Circle circle = new Circle(5,20,2);
    private Ball ball;
    private Movable comet;
    private Planet earth;
    private List<Movable> allObjects;
    private List<Comet> comets;
    private CometHelper spawner;
    public boolean initialclick = false;
    private long initial_time;
    private long timer;

    public GameWorld()
    {
        initial_time = System.currentTimeMillis();
        timer = System.currentTimeMillis();
        allObjects = new ArrayList<Movable>();
        comets = new ArrayList<Comet>();
        earth = new Planet(Gdx.graphics.getWidth()/2.0f,Gdx.graphics.getHeight()/2.0f, 127.0f, 0.46f);

        ball = new Ball(10,400,16, earth.getX(), earth.getY());
        allObjects.add(ball);

        //comet = new Comet(10,500,16, earth.getX(), earth.getY());
         spawner = new CometHelper(ball,earth);
        //  ball2 = new Ball(30,100,16, earth.getX(), earth.getY());
    }

    public void update(float delta) {
       //System.out.println(1/delta); FPS
        //System.out.println(earth.getX() + " " + earth.getY());
        if(initialclick!=true)
        {
            initial_time = System.currentTimeMillis();timer = System.currentTimeMillis();
            initialclick =  Gdx.input.isTouched();
        }
        else {
            if (ball.getLives() > 0) {
                updateIterator(delta);
                earth.update(delta);
                gravityIterator();
                frictionIterator();
                stubCollisionIterator(delta);
                ballcometcollision();
                timer = System.currentTimeMillis();
                comets = spawner.collided(comets); //the bounce code is setting it to false;
                if (comets.size() < 10 + this.getTimer() / 4) {
                    Comet newcomet = spawner.randomSpawn(400, 500, ball.getAngle());
                    comets.add(newcomet);
                    allObjects.add(newcomet);
                }
            } else {
                comets.clear();
            }
        }


    }

    public void ballcometcollision()
    {
        if(comets.size()!=0) {
            int counters = 0;
            for (Comet comet : comets) {
                if (distance(ball.getX(), ball.getY(), comet.getX(), comet.getY()) <= (ball.getRadius() + comet.getRadius())) {
                    //if collision
                    comet.setExploded(true);
                    ball.minusLife();

                     //to erase exploded comets
                }

                counters++;
            }

        }
    }


    public float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt( (x2-x1)*(x2-x1) +(y2-y1)*(y2-y1));
    }
    private void updateIterator(float delta){
        for(Movable object : allObjects) {
            object.update(delta);
        }
    }

    private void gravityIterator()
    {
        for(Movable object : allObjects) {
            if (object.getType().equals("ball")) {
                earth.applyGravity(object);
            } else if (object.getType().equals("comet")) {
                object.setExploded(earth.applyGravity(object));

            }
        }
    }

    private void frictionIterator()//off and on earth
    {
        for(Movable object : allObjects)
        {
            if(object.getType().equals("ball")) {
                earth.applyFriction(object);
                earth.applyDrag(object);
            }
            else if(object.getType().equals("comet"))
            {
                earth.applyFriction(object);
                earth.applyDrag(object);
            }
        }
    }
    private void stubCollisionIterator(float delta)
    {
        for(Movable object : allObjects)
        {
            if(object.getType().equals("ball"))
            {
                earth.stubCollision(object, delta);
            }
            else if(object.getType().equals("comet"))
            {
                object.setExploded(earth.stubCollision(object, delta));
            }


        }
    }
    public List getComets(){ return comets;}
    public Movable getBall()
    {
        return ball;
    }
    public Planet getEarth()
    {
        return earth;
    }

    public Movable getComet() {
        return comet;
    }
    public long  getTimer() {
        return (timer-initial_time)/1000;
    }



}
