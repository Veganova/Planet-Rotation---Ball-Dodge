package com.veganova.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.veganova.gameobjects.Ball;
import com.veganova.gameobjects.Comet;
import com.veganova.gameobjects.Movable;
import com.veganova.gameobjects.Planet;
import com.veganova.helpers.AssetLoader;

import java.util.List;

public class GameRenderer {

    private static final float rotationSpeed = 0.05f;


    private GameWorld universe;
    private OrthographicCamera cam = new OrthographicCamera();
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;
    private Movable comet;

    private Ball myBall;
    private Planet earth;
    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
    public GameRenderer(GameWorld world) {
        // TODO Auto-generated constructor stub
        universe = world;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        cam.setToOrtho(false, w, h);


        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        batcher = new SpriteBatch();
        // Attach batcher to camera
        batcher.setProjectionMatrix(cam.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
    }

    public void render(float runTime) {
        BitmapFont font = new BitmapFont();

        myBall = (Ball)universe.getBall();
        comet = universe.getComet();
        earth = universe.getEarth();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            System.out.println("CAM: " + cam.position.x + " " + cam.position.y);
            System.out.println("BALL: " + myBall.getX() + " " + myBall.getY());
        }
        //CAMERA MOVEMENT CODE
        handleInput();

//        cam.position.set(universe.getBall().getX(), universe.getBall().getY(), 0f);

//        cam.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeType.Filled);


        shapeRenderer.setColor(0.7f, 0.34f, 0.34f, 1);
        shapeRenderer.rect(cam.position.x - cam.viewportWidth/2, cam.position.y-cam.viewportHeight/2, cam.viewportWidth, cam.viewportHeight);
        //shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shapeRenderer.end();

        // Begin SpriteBatch
        batcher.begin();
        if(!universe.initialclick) {
            batcher.draw(AssetLoader.menu, 0, 0);
            font.draw(batcher, "Click to start", 180, 320);
        }
        else {
            batcher.enableBlending();
            myBall.draw(batcher);
            List<Comet> tempcomets = universe.getComets();
            for (int i = 0; i < tempcomets.size(); i++) {
                tempcomets.get(i).draw((batcher));
            }
            // comet.draw(batcher);

//        batcher.draw(AssetLoader.ball,
//                myBall.getX() - myBall.getRadius(), myBall.getY() - myBall.getRadius(), myBall.getRadius() * 2, myBall.getRadius() * 2);
            batcher.draw(AssetLoader.ballBig, earth.getX() - earth.getRadius() - earth.getStub(),
                    earth.getY() - earth.getRadius() - earth.getStub(), earth.getRadius() + earth.getStub(), earth.getRadius() + earth.getStub(), 2 * earth.getRadius() + 2 * earth.getStub(), 2 * earth.getRadius() + 2 * earth.getStub(), 1f, 1f, earth.getAngle());
            //batcher.draw(AssetLoader.comet, 100, 100, 32, 32);

            //batcher.draw(AssetLoader.ruler.getTexture(),0,0,w, (float)w/AssetLoader.ruler.getTexture().getWidth() * AssetLoader.ruler.getTexture().getHeight());//, originX, originY, width, height, scaleX, scaleY, );
            // System.out.println(Gdx.graphics.getWidth());///AssetLoader.ruler.getTexture().getWidth() * AssetLoader.ruler.getTexture().getHeight());
            //batcher.draw(AssetLoader.ruler.getTexture(), 0, 0, 0, 0, w, (float)w/AssetLoader.ruler.getTexture().getWidth() * AssetLoader.ruler.getTexture().getHeight(), 1.0, 1.0, 90.0);
            // batcher.draw(AssetLoader.ruler.getTexture(),0,0,0,0,h, ,1.0,1.0,-Math.PI/2.0);//, originX, originY, width, height, scaleX, scaleY, );
            font.draw(batcher, "Lives: " + Integer.toString(myBall.getLives()), 6, 20);
            font.draw(batcher, "Time: " + Long.toString(universe.getTimer()), Gdx.graphics.getWidth() - 150, 20);

        }
        batcher.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            cam.rotate(rotationSpeed, 0, 0, 1);
        }

        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100/cam.viewportWidth);

        //float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        //float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        //cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        //cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
        shapeRenderer.setProjectionMatrix(cam.combined);

        cam.update();
    }

}
