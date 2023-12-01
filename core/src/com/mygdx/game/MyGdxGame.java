package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Level_maker.Level_maker;
import com.mygdx.game.Level_maker.Player;
import com.mygdx.game.Menu.MainMenu;


public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;

    MainMenu mainMenu;



    @Override
    public void create() {
        batch = new SpriteBatch();
        mainMenu = new MainMenu();
        mainMenu.create();

    }


    @Override
    public void render() {
        batch.begin();
        mainMenu.render();

        batch.end();


    }

    @Override
    public void dispose() {
        mainMenu.dispose();
        batch.dispose();
        mainMenu.dispose();


    }
}
