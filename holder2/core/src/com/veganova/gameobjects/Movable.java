package com.veganova.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Veganova on 5/6/2016.
 */
public interface Movable {


    public void update(float delta);

    public float changeFriction(float earthSlow);

    public void boundValues();

    public void draw(SpriteBatch batcher);

    public float getX() ;
    public void setX(float x) ;

    public float getY();
    public void setY(float y);


    public float getdx();
    public void setdx(float dx) ;


    public float getdy();
    public void setdy(float dy);

    public float getm();

    public void setm(float M);

    public float getRadius();
    public float getr();
    public void setr(float R);

    public float getdr();

    public void setdr(float DR);
    public float getRotation();

    public float getAngle();
    public void setAngle(float angle);
    public float getdAngle();
    public void setdAngle(float dangle);
    public float getddAngle();
    public void setddAngle(float ddangle);
    public float getMaxdr();

    public void setMaxdr(float maxdr);
    public float getddr();
    public void setddr(float ddr);
    public String getType();

    public void setExploded(boolean exp);
    public boolean getExploded();
    public int getConsCol();

    public void setConsCol(int consCol);
    //getter setters
}
