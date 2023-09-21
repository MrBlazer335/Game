package com.mygdx.game.Level_maker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Level_maker {
    OrthographicCamera camera;
    OrthogonalTiledMapRenderer mapRender;
    TiledMap map;
    public Level_maker(String level, World world){

        map = new TmxMapLoader().load(level);
        mapRender = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (level.equals("test.tmx")){
            BodyDef groundBodyDef = new BodyDef();
// Set its world position
            groundBodyDef.position.set(new Vector2(0, 260));

// Create a body from the definition and add it to the world
            Body groundBody = world.createBody(groundBodyDef);

// Create a polygon shape
            PolygonShape groundBox = new PolygonShape();
// Set the polygon shape as a box which is twice the size of our view port and 20 high
// (setAsBox takes half-width and half-height as arguments)
            groundBox.setAsBox(camera.viewportWidth, 10.0f);
// Create a fixture from our polygon shape and add it to our ground body
            groundBody.createFixture(groundBox, 0.0f);
// Clean up after ourselves
            groundBox.dispose();
        }
    }
}
