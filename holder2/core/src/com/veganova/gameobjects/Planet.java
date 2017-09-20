package com.veganova.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Planet {
    private float slow;
    private float x, y ,dx, dy, accelx, accely, gravity;
    private float Angle, dAngle, ddAngle; // For handling planet rotation
    private float radius;
    private float stub;
    private int numStubs;
    private float m;  //mass


    public boolean TEMP_DEBUG = false;

    public Planet(float x, float y, float r, float gravity) {
        radius = r;
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
        this.accelx = 0;
        this.accely = 0;
        stub = 50;
        this.gravity = gravity;
        this.Angle = 0;
        this.dAngle = 0;
        this.ddAngle = 0;
        this.m = 1000f;//trial
        this.numStubs = 5;
        this.slow = 0.9725f; //trial
    }
    public void update(float delta) {
        onKeyPress();
        dAngle += ddAngle * delta;
        Angle += dAngle;
        Angle = (Angle + 720.0f)%360.0f;
        //slow , friction
        dAngle *= slow;//trial
       // planetCap();
    }

    public void planetCap()
    {
        if(this.dAngle>0.1f)
        {
            this.dAngle=0.099f;
        }
        else if(this.dAngle<-0.1f)
        {
            this.dAngle=-0.099f;
        }
    }



    public void bounce(float delta, Movable movable, int side)
    {
        //wp,new_wp, r1sq -->planet
        //wb,new_wb, r2sq -->planet
        //Angular momentum = Iw
        //planet's I = m*r*r
        float wp, wb, new_wp, new_wb;
        float rp, rb;
        float mp, mb;
        wp = (float) ((this.dAngle*Math.PI/180.0));
        wb = movable.getdAngle();
        rp = this.radius;// * this.radius;
        rb = movable.getr();// * movable.getr();
        mp = this.m;
        mb = movable.getm();
                //line part (maybe mutliply this by 5         //ball part
        float Ip = 1.0f/12.0f*(mp/3f)*rb*rb    +   (2*mp/3f)*rp*rp  ;//stub is 1/3 of planet mass
        float Ib = mb*rb*rb;
        new_wp = (Ib*wb + wp*Ip - Ib*(wp-wb))/(mb*rb*rb+Ip);
        new_wb = wp+new_wp-wb;
        //this.dAngle = (float) (10*new_wp*delta * 180.0/(Math.PI))*2.25f;//trial
        movable.setdAngle((float) (10 * new_wb * 0.15));//trial
    }

    public boolean bounceTop(float delta, Movable movable)
    {
        float vb = movable.getdr();
        if(Math.abs ( (double)vb) >3.0) {
            movable.setdr(-vb * 0.8f);
            return true;
        }
        else {
            movable.setddr(0f);
            movable.setdr(0f);
        }
        return false;
    }

    public boolean stubCollision(Movable movable, float delta)
    {

        double l = movable.getAngle() * 180.0/Math.PI;
        l = (l+360)%360; //make it positive angles
        double j = Math.abs((l-Angle+360)%360.0f)%(360.0/numStubs); //4/27
        if(TEMP_DEBUG) {
            System.out.println("J val: " + j);
            System.out.println("Stub num: " +Math.abs((l-Angle))/(72) );
            System.out.println("Angle: " + Angle + ". Ball Angle: " + l);
            TEMP_DEBUG = false;
        }
        if( (j  < 3.7 || j >68.3)//trial
                &&(movable.getr() > 192 && movable.getr() < 194)) { //bouncing at the top of the stub
                return (bounceTop(delta, movable)||movable.getExploded());// trial TRIAL

        }//CONSTANTLY KEEP ANGLES IN THE CORRECT RANGE!!!!!!!!!
        else if( (j  < 18.0 -movable.getr()/16.0 || j >54.0 + movable.getr()/16.0))//made this a linear function, angle is changing perheight. think about it
        {//regular bounces on the sides of the stub
          if(movable.getr()< 192 )//make this better and more robust later (so that it will work for different amount of stubs/different lengths)
            {//j<10 on the left. >60 for left. intuitive progression
                int ang = (int)((l-Angle)/72+0.5);
                if(j<10)//side
                    bounce(delta, movable,0);
                else
                    bounce(delta,movable,1);

            }
            if(movable.getr()< 194) {
                int cons = movable.getConsCol();
                cons++;
                if(cons > 1)//HAS BEEN IN A STUB FOR MORE THAN 1 FRAME. Applies to cases such as ball has no dw or code simply breaks. Safety
                {
                    pushOut(movable, (float) j);
                }
                movable.setConsCol(cons);
                return (false||movable.getExploded());
              }

            }
        else{
            movable.setConsCol(0);
        }
        return (false||movable.getExploded());
    }
    public void pushOut(Movable movable, float j)//Pass in angle of the stub. Goes witht he consCol code. If ball in, push it out
    {//minimize the use of this function --> the bounce function should handle all cases
        float dAngleBall = movable.getdAngle();
        if(j>60 && dAngleBall>=0)//ball is on right side of stub and ball is moving left
            movable.setdAngle(-dAngleBall-0.01f);
        else if(j<13 && dAngleBall <=0)//ball is on left side of stub and ball is moving right
            movable.setdAngle(-dAngleBall+0.01f);
    }
    public boolean applyFriction(Movable movable)
    {
        if(movable.getr() < 121+ 2)//friction on earth//trial
        {//taking out ddAngle from ball. doesnt make sense that it itself has an acceleration, other things apply acceleration through a Force-applied
            //movable.setddAngle(this.ddAngle/40f); //denominator experimentally determine
            movable.setdAngle(movable.getdAngle() + dAngle * 0.0005f);
            movable.setddAngle(dAngle * 0.0005f);
            movable.setdAngle(movable.getdAngle() * movable.changeFriction(slow));//friction slow //trial
            movable.setMaxdr(0.055f);//trial
            //movable.takeoff(121f);//param - surfaceR
            return true;
        }
        return false;
     }

    public void applyDrag(Movable movable)
    {
        if(movable.getr()>=123)
        {
            //off earth friction
            //movable.setddAngle(0f); taken out, see in if statement
            // movable.setdAngle(movable.getdAngle() * .9999f);//trial
            movable.setdAngle(movable.getdAngle() *0.99f* airResistance(Math.abs(movable.getdAngle())));
            movable.setMaxdr(0.085f);//trial
            movable.setddAngle(0f);
        }
    }
    public boolean applyGravity(Movable movable)//and other near surface effects -->takeoff
    {

        float G = 0.2f;
        float r = movable.getr();
        if(r > 121) //121 experimentally determined. Keep ball above surface ground
        {
            // movable.setdr((movable.getdr() - gravity));
             movable.setddr(-G * m / (movable.getr()));//*(float)(Math.sqrt(movable.getr()))));
           // movable.setdr(movable.getdr() - G * m / (movable.getr()));
        }
        else {
            if(movable.getddr()<0)
                movable.setddr(0);
            movable.setdr(0);
            return true;
        }
        return false;
    }
    public float airResistance(float movable_dAngle)
    {
        //return -(float) ( -Math.pow(Math.E, -50*(movable_dAngle-0.1f))/40.0f - 39f/40f + 2.00095 - 0.0259);
        return (float) (Math.pow(Math.E, -50*(-movable_dAngle+0.13f))/-40.0f - 39f/40f + 2.00095 - 0.026);
    }
    public void onKeyPress() {
        if( Gdx.input.isButtonPressed(Input.Buttons.LEFT)) { // for a touchscreen
            if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2.0)
                ddAngle = 4;
            else if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2.0)
                ddAngle = -4;
            else
                ddAngle = 0;
        }
        else//for keyboard
        {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                ddAngle = 4;
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                ddAngle = -4;
            else
                ddAngle = 0;
        }
        //Gdx.app.log("X " , String.valueOf(Gdx.input.getX()));
    }

    public int getSign(float x)
    {
        return (int)(x/Math.abs(x));
    }

    public float getm(){
        return m;
    }
    public void setm(float M)
    {
        this.m = M;
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

    public float getAngle() {
        return Angle;
    }

    public float getRotation(){
        return (float) (Angle/Math.PI * 180.0f);
    }
    public float getStub(){
        return stub;
    }

}
