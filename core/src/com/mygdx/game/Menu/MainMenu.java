package com.mygdx.game.Menu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Level_maker.Level_Wrapper;
import com.mygdx.game.MyGdxGame;

public class MainMenu implements Screen {
    final MyGdxGame game;
    Boolean start_the_game = false;
    Level_Wrapper levelWrapper;
    private Skin skin;

    private Stage stage;

    public MainMenu(final MyGdxGame game) {
        this.game = game;
        levelWrapper = new Level_Wrapper(game);
        stage = new Stage(new StretchViewport(600,600));
        skin = new Skin(Gdx.files.internal("skin.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        final Stack stack = new Stack();
        stack.setName("first");

        Image image = new Image(skin, "MainMenu");
        stack.addActor(image);

        Label label = new Label("FROG", skin, "title");
        label.setAlignment(Align.top);
        stack.addActor(label);

        final Table table1 = new Table();
        table1.padLeft(0.0f);
        table1.padRight(0.0f);
        table1.padTop(15.0f);
        table1.padBottom(16.0f);
        table1.align(Align.bottom);

        TextButton textButton = new TextButton("PLAY", skin);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(Actions.fadeOut(1));
                start_the_game = true;

            }
        });
        table1.add(textButton);

        table1.row();
        textButton = new TextButton("EXIT", skin);
        table1.add(textButton).spaceTop(15.0f);
        stack.addActor(table1);
        table.add(stack);
        stage.addActor(table);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!start_the_game.equals(true)) {
                    Gdx.app.exit();
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (start_the_game.equals(true)) {
            game.setScreen(new Level_Wrapper(game));
        }
        stage.act();
        stage.draw();
    }

    @Override
    public void show() {

    }


    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

    public void dispose() {
        stage.dispose();
        skin.dispose();
        levelWrapper.dispose();
    }
}
