package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Level_maker.Level_maker;
import com.mygdx.game.Level_maker.Player;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    OrthographicCamera camera;
    OrthogonalTiledMapRenderer mapRender;
    World world;
    TiledMap map;

    Level_maker level;
    Player player;


    @Override
    public void create() {
        batch = new SpriteBatch();
        world = new World(new Vector2(0, -5), true);
        player = new Player(world);
        level = new Level_maker("test.tmx",world);
        map = new TmxMapLoader().load("test.tmx");
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
        camera.update();
        mapRender.render();
        batch.end();
        world.step(1/60f,6,2);
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();

    }
}
