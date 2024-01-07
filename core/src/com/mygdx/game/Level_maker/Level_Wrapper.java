package com.mygdx.game.Level_maker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Level_maker.Collectables.Items;
import com.mygdx.game.Level_maker.EndGoal.Finish;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.UI.DeathScene;
import com.mygdx.game.UI.Victory;

public class Level_Wrapper implements Screen {
    final MyGdxGame game;
    Music music;
    SpriteBatch batch;
    OrthographicCamera camera;
    OrthogonalTiledMapRenderer mapRender;
    MapObjects mapObjects;
    MapObjects Danger;
    Box2DDebugRenderer debugRenderer;
    TiledMap map;
    Level_maker level;
    Player player;
    Items items;
    Finish finish;
    private final float[][] appleCoordinates = {{395, 252}, {583, 280}, {781, 251}, {1046, 331}, {1184, 315}, {1458, 379}, {1595, 379}, {1632, 640}, {1348, 500}, {1200, 720}, {1031, 715}, {826, 756},
            {874, 811}, {710, 779}, {710, 859}, {588, 907}, {594, 923}, {619, 987}, {699, 971}, {544, 1034}, {428, 1067}, {376, 1003}, {366, 1131}, {317, 1162}, {286, 1227}};


    public Level_Wrapper(final MyGdxGame game) {


        this.game = game;
        Gdx.graphics.setContinuousRendering(true);
        music = Gdx.audio.newMusic(Gdx.files.internal("audio/The Empire Of Toads.mp3"));

        music.setVolume(0.000000005f);
        music.setLooping(true);


        batch = new SpriteBatch();
        level = new Level_maker("LEVEL_1.tmx");
        finish = new Finish(level.getWorld(), 165, 208);


        items = new Items(level, batch, appleCoordinates);
        player = new Player(level.getWorld(), 320, 208);
        map = new TmxMapLoader().load("LEVEL_1.tmx");
        mapObjects = map.getLayers().get("physics").getObjects();
        Danger = map.getLayers().get("Damage").getObjects();
        level.parseTiledObjectLayer(mapObjects, "Physics");
        level.parseTiledObjectLayer(Danger, "Damage");
        debugRenderer = new Box2DDebugRenderer();
        mapRender = new OrthogonalTiledMapRenderer(map);
        camera = level.getCamera();


        camera.setToOrtho(false, 1200, 800);
        camera.update();

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        music.play();
        batch.begin();
        player.render(batch);
        mapRender.setView(camera);

        camera.position.set(player.CameraCordsX(), player.CameraCordsY(), 0);
        camera.zoom = 0.25f;
        mapRender.render();
        finish.render(batch);

        finish.end();
        camera.update();
        items.render();

        Gdx.app.log("Coordinates", finish.getCoordinates().toString());

        debugRenderer.render(level.getWorld(), camera.combined);
        level.getWorld().step(1 / 15f, 6, 2);
        batch.end();
        if (player.getHealth() == 0) {
            music.stop();
            dispose();
            game.setScreen(new DeathScene(game));
        }
        if (items.allApples() && finish.end()) {
            music.stop();
            dispose();
            game.setScreen(new Victory(game));

        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
