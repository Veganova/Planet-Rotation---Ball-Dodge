package com.veganova.gameworld;

import com.badlogic.gdx.Gdx;
import com.veganova.gameobjects.Ball;
import com.veganova.gameobjects.Comet;
import com.veganova.gameobjects.Movable;
import com.veganova.gameobjects.Planet;

import java.util.List;

/**
 * Created by Veganova on 5/24/2016.
 */
public class CometHelper {


    private Movable ball;
    private Planet earth;
    public CometHelper(Movable ball, Planet earth){
        this.ball = ball;
        this.earth = earth;
    }



    public Comet randomSpawn(float r1, float r2, float rad)//rad is most likely ball.getAngle();
    {
        float r = (float) (Math.random()*(r2-r1)) + r1;
        float ang = rad + unrandomrandomangle();
       // System.out.println(rad + " " + ang);
        float x = (float) (Math.cos(ang) * r + earth.getX());
        float y = (float) (Math.sin(ang) * r + earth.getY());
        Comet newspawn = new Comet( x,y,ball.getRadius(), earth.getX(), earth.getY());

        float  rand_dAngle= (float) (Math.random()*0.03f);
        if(Math.random()>0.5)
           rand_dAngle = -rand_dAngle;
        newspawn.setdAngle( rand_dAngle);
        return newspawn;
    }

    public float unrandomrandomangle()
    {
        float ang = (float) (ball.getAngle()%(2*Math.PI));
        double randval = Math.random()*2;// + Math.random();
        return (float)(Math.PI*Math.pow((randval - 1),3));

    }
    public List<Comet> collided(List<Comet> comets)
    {
        for(int i =0;i<comets.size();i++)
        {
            if(comets.get(i).getExploded())
            {
                comets.remove(i);
            }
        }
        return comets;
    }

}
