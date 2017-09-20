package com.veganova.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.veganova.helpers.AssetLoader;

public class Ball implements Movable{

//    private Vector2 position;
//    private Vector2 velocity;
//    private Vector2 acceleration;

    private float x, y ,dx, dy, accelx, accely, r ,dr, ddr, xorbit, yorbit; //total radius (tr) from the center of the planet --> this is just for now,
    // the centers will be changing as ball moves off the planet and maybe at other planets
    private float rotation; // For handling bird rotation
    private float radius;//32 pixels x 32 pixels image
    private float Angle, dAngle, ddAngle;
    private float m;
    private String type;



    private int consCol=0;
    private float Maxdr;
    private boolean exploded;



    private int lives = 0;
    public Ball(float x, float y, float r, float px, float py)
    {
        exploded = false;
        type = "ball";
        radius = r;
        this.lives =100;
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
        this.accelx = 0;
        this.accely = 0;
        this.xorbit = px;
        this.yorbit = py;
        this.r = (float) Math.sqrt(( (this.x -xorbit)*(this.x -xorbit) + (this.y -yorbit)*(this.y -yorbit)));
        this.dr = 0;
        this.Angle = (float) Math.atan2(y-yorbit, x - xorbit);
        this.dAngle = 0;
        this.ddAngle = 0;
        this.m = 150f; //trial
        this.Maxdr = 1f;
        this.ddr = 0f;

    }
    public void draw(SpriteBatch batcher)
    {
        batcher.draw(AssetLoader.ball, x - radius, y - radius, radius*2, radius*2);

    }
    public void update(float delta) {
        //takeoff();
        //multiply something else by delta on the angular side!!!
        dr+=ddr;
        r += dr * delta;

        //dAngle += ddAngle * delta*0.66;
        Angle += dAngle;
        Angle = (float)((Angle+2*Math.PI)%(2*Math.PI));
        this.boundValues();
       // System.out.println(dAngle);
        x = (float) (Math.cos(Angle) * r + xorbit);
        y = (float) (Math.sin(Angle) * r + yorbit);


    }

    public float changeFriction(float earthSlow)
    {
        return (Math.abs(dAngle) * (earthSlow - 0.94f)/(0.05f) + 0.94f); //trial
    }
    public void onPress() {
        x = Gdx.input.getX();
        y = Gdx.graphics.getHeight()-  Gdx.input.getY();

        this.r = (float) Math.sqrt(( (this.x -xorbit)*(this.x -xorbit) + (this.y -yorbit)*(this.y -yorbit)));
        this.dr = 0;
        this.Angle = (float)((Math.atan2(y-yorbit, x - xorbit)));

    }
    public void takeoff(float surfaceR)
    {
        //if dAngle is large enough, start lifting off?
        if(Math.abs(dAngle)>0.051 && dr>=0)//trial
        {
            // dr += 30f*  Math.pow(2.5,-((r-115)/0.3));
            //dr += 50 * Math.pow(Math.E, -(r - (118f)) / 10f);
            ddr+=40f;
        }
        else if(ddr>0)
        {
            ddr-=0.5f;
        }
        System.out.println(dAngle + " " + r + " " + ddr + " " + dAngle);
       // System.out.println(dr);
    }
    public void boundValues()
    {
        if(dAngle<-Maxdr)
            dAngle = -Maxdr;
        if(dAngle > Maxdr)
            dAngle = Maxdr;
    }
    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }


    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }


    public float getdx() {
        return dx;
    }
    public void setdx(float dx) {
        this.dx = dx;
    }


    public float getdy() {
        return dy;
    }
    public void setdy(float dy) {
        this.dy = dy;
    }

    public float getm()
    {
        return m;
    }

    public void setm(float M)
    {
        this.m = M;
    }

    public float getRadius() {
        return radius;
    }


    public float getr()
    {
        return r;
    }
    public void setr(float R)
    {
        r = R;
    }

    public float getdr()
    {
        return dr;
    }

    public void setdr(float DR)
    {
        dr = DR;
    }

    public float getRotation() {
        return rotation;
    }

    public float getAngle(){
        return Angle;
    }
    public void setAngle(float angle){
        Angle = angle;
    }
    public float getdAngle(){
        return dAngle;
    }
    public void setdAngle(float dangle){
        dAngle = dangle;
    }
    public float getddAngle(){
        return ddAngle;
    }
    public void setddAngle(float ddangle){
        ddAngle = ddangle;
    }
    public float getMaxdr() {
        return Maxdr;
    }

    public void setMaxdr(float maxdr) {
        Maxdr = maxdr;
    }
    public float getddr() {
        return this.ddr;
    }
    public void setddr(float ddr) {
        this.ddr = ddr;
    }

    @Override
    public String getType() {
        return type;
    }

    public boolean getExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }
    public int getConsCol() {
        return consCol;
    }

    public void setConsCol(int consCol) {
        this.consCol = consCol;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
    public void minusLife()
    {
        this.lives--;
    }
}
	