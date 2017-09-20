package com.veganova.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.veganova.gameobjects.Ball;
import com.veganova.helpers.AssetLoader;

public class GameRenderer {

	private static final float rotationSpeed = 0.05f;
	
	
	private GameWorld universe;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	private Ball myBall;
	float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
	public GameRenderer(GameWorld world) {
		// TODO Auto-generated constructor stub
		universe = world;
		 float w = Gdx.graphics.getWidth();
	     float h = Gdx.graphics.getHeight();
	     System.out.println(w + ", " + h);
		cam = new OrthographicCamera();
		cam.setToOrtho(false,w , h);

       
        //cam = new OrthographicCamera(30, 30 * (h / w));
        //cam.position.set(universe.getCirc().x, universe.getCirc().x, 0);
        cam.update();
        
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);
		
		batcher = new SpriteBatch();
        // Attach batcher to camera
        batcher.setProjectionMatrix(cam.combined);
		
	}

	public void render(float runTime) {
		myBall = universe.getBall();
	    
		//CAMERA MOVEMENT CODE
		//handleInput();
		//cam.update();
	    //cam.position.set(universe.getBall().x,universe.getBall().y,0);
	    //shapeRenderer.setProjectionMatrix(cam.combined);
	    
	    Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        shapeRenderer.begin(ShapeType.Filled);
        
        shapeRenderer.setColor(0.7f, 0.34f, 0.34f, 1);
        shapeRenderer.rect(0, 0, w, h);
        //shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        shapeRenderer.end();
       
        // Begin SpriteBatch
        batcher.begin();
        batcher.enableBlending();
        batcher.draw(AssetLoader.ball.getTexture(),
        		myBall.getX(), myBall.getY(), myBall.getRadius()*2, myBall.getRadius()*2);
        batcher.draw(AssetLoader.ballBig.getTexture(),110, 110, 420, 420);
        batcher.draw(AssetLoader.ruler.getTexture(),0,0,w, (float)w/AssetLoader.ruler.getTexture().getWidth() * AssetLoader.ruler.getTexture().getHeight());//, originX, originY, width, height, scaleX, scaleY, );
       // System.out.println(Gdx.graphics.getWidth());///AssetLoader.ruler.getTexture().getWidth() * AssetLoader.ruler.getTexture().getHeight());
        //batcher.draw(AssetLoader.ruler.getTexture(), 0, 0, 0, 0, w, (float)w/AssetLoader.ruler.getTexture().getWidth() * AssetLoader.ruler.getTexture().getHeight(), 1.0, 1.0, 90.0);
       // batcher.draw(AssetLoader.ruler.getTexture(),0,0,0,0,h, ,1.0,1.0,-Math.PI/2.0);//, originX, originY, width, height, scaleX, scaleY, );
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

        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }

	}
