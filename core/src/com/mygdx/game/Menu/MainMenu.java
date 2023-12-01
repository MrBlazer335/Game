package com.mygdx.game.Menu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Level_maker.Level_Wrapper;

public class MainMenu extends ApplicationAdapter {
    Level_Wrapper levelWrapper;
    private Skin skin;

    private Stage stage;


    public void create() {
        stage = new Stage(new ScreenViewport());
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
                stage.dispose();
                skin.dispose();



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
                Gdx.app.exit();
            }
        });
    }

    public void render() {
        levelWrapper = new Level_Wrapper();
        levelWrapper.render();
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
