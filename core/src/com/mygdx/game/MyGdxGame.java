package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Level_maker.Level_maker;
import com.mygdx.game.Level_maker.Player;

public class MyGdxGame extends ApplicationAdapter {
    private int Health = 3;
    SpriteBatch batch;
    OrthographicCamera camera;
    OrthogonalTiledMapRenderer mapRender;
    MapObjects mapObjects;
    MapLayer mapLayer;
    Box2DDebugRenderer debugRenderer;

    TiledMap map;

    Level_maker level;
    Player player;


    @Override
    public void create() {
        Gdx.graphics.setContinuousRendering(true);
        batch = new SpriteBatch();
        level = new Level_maker("LEVEL_1.tmx");
        player = new Player(level.getWorld());
        map = new TmxMapLoader().load("LEVEL_1.tmx");
        mapObjects = map.getLayers().get("physics").getObjects();
        level.parseTiledObjectLayer(mapObjects);
        debugRenderer = new Box2DDebugRenderer();
        mapRender = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        player.render(batch);
        mapRender.setView(camera);
        camera.position.set(player.CameraCordsX(),player.CameraCordsY(),0);
        camera.update();
        camera.zoom = 0.25f;
        mapRender.render();
        //debugRenderer.render(level.getWorld(),camera.combined);
        batch.end();
        level.getWorld().step(1 / 15f, 6, 2);

    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();


    }
}
