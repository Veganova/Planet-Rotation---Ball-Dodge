package com.veganova.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
    public static Texture texture, texture2, texture3; //big file with many textureRegions
    public static TextureRegion ball, ballBig, ruler, comet, menu;
    public static Animation x;

    public static void load() {

        texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture2 = new Texture(Gdx.files.internal("data/texture3.png"));
        texture2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);// CHANGE PARAMETERS LATER
        menu = new TextureRegion(new Texture(Gdx.files.internal("data/menuscreen.png")), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ball = new TextureRegion(texture, 0, 0, 32, 32);
        ballBig = new TextureRegion(texture2, 0, 0, 308, 308);
        ruler =  new TextureRegion(new Texture(Gdx.files.internal("data/woodenruler.png")));
        ruler.flip(false,false);//this doesnt work....
        comet = new TextureRegion(new Texture(Gdx.files.internal("data/comet.png")));


        //ball.flip(false, true);

        //animations not added : http://www.kilobolt.com/day-6-adding-graphics---welcome-to-the-necropolis.html
        //search "TextureRegion" in that^
    }
    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();
    }
}
