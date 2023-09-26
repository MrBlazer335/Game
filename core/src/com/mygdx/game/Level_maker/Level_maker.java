package com.mygdx.game.Level_maker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Level_maker {
    OrthographicCamera camera;
    OrthogonalTiledMapRenderer mapRender;
    TiledMap map;
    World world;

    public Level_maker(String level) {
        world = new World(new Vector2(0, -9.8f), true);
        map = new TmxMapLoader().load(level);
        mapRender = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    public void parseTiledObjectLayer(MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {

            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject, world);

            }
        }
    }


    private void createStaticBody(PolygonMapObject polygonMapObject, World world) {
        Body body;
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);

        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000.0f);
        shape.dispose();
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] WorldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; i++) {
            Vector2 current = new Vector2(vertices[i * 2] -17 , vertices[i * 2 + 1] - 17);
            WorldVertices[i] = current;
        }
        PolygonShape shape = new PolygonShape();
        shape.set(WorldVertices);
        return shape;
    }

    public World getWorld() {
        return world;
    }

}

