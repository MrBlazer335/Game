package com.mygdx.game.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.GaussianBlurEffect;
import com.crashinvaders.vfx.effects.VfxEffect;
import com.mygdx.game.Controllers.Movement;
import com.mygdx.game.Level_maker.Collectables.Items;
import com.mygdx.game.Level_maker.EndGoal.Finish;
import com.mygdx.game.Level_maker.Level_maker;
import com.mygdx.game.Level_maker.Player;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.UI.DeathScene;
import com.mygdx.game.UI.Victory;


public class Second_Level implements Screen {
    final MyGdxGame game;
    Music music;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer mapRender;
    private final MapObjects mapObjects;
    private final MapObjects Danger;
    private final Box2DDebugRenderer debugRenderer;
    private final TiledMap map;
    private final Level_maker level;
    private final Player player;
    private final Items items;
    private final Movement movement;
    private final Finish finish;
    private final float[][] appleCoordinates = {{300, 250}};


    public Second_Level(final MyGdxGame game) {


        this.game = game;
        Gdx.graphics.setContinuousRendering(true);
        music = Gdx.audio.newMusic(Gdx.files.internal("audio/The Empire Of Toads.mp3"));

        music.setVolume(0.000000005f);
        music.setLooping(true);


        batch = new SpriteBatch();
        level = new Level_maker("LEVEL_1.tmx");
        finish = new Finish(level.getWorld(), 210, 208);


        items = new Items(level, batch, appleCoordinates);
        player = new Player(level.getWorld(), 1800, 208);
        map = new TmxMapLoader().load("LEVEL_2.tmx");
        mapObjects = map.getLayers().get("physics").getObjects();
        Danger = map.getLayers().get("Damage").getObjects();
        level.parseTiledObjectLayer(mapObjects, "Physics");
        level.parseTiledObjectLayer(Danger, "Damage");
        debugRenderer = new Box2DDebugRenderer();
        mapRender = new OrthogonalTiledMapRenderer(map);
        camera = level.getCamera();

        movement = new Movement(player, batch);

        camera.setToOrtho(false, 1200, 800);
        camera.update();

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);

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



        //debugRenderer.render(level.getWorld(), camera.combined);
        level.getWorld().step(1 / 15f, 6, 2);

        batch.end();
        movement.render();
        if (player.getHealth() == 0) {
            music.stop();
            dispose();
            game.setScreen(new DeathScene(game, this));
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

