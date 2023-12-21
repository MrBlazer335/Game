package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Level_maker.Level_Wrapper;
import com.mygdx.game.MyGdxGame;

import static com.badlogic.gdx.scenes.scene2d.Touchable.enabled;

public class Victory implements Screen {
    private final MyGdxGame game;
    private final Stage stage;
    private final Skin skin;
    public Victory(final MyGdxGame game){
        this.game = game;
        stage = new Stage(new StretchViewport(1024,1024));
        skin = new Skin(Gdx.files.internal("victorySkin.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setTouchable(enabled);
        table.setFillParent(true);

        Stack stack = new Stack();

        Image image = new Image(skin, "final_screen");
        stack.addActor(image);

        Table table1 = new Table();
        table1.align(Align.bottom);

        Button button = new Button(skin);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               Gdx.app.exit();

            }
        });
        table1.add(button).align(Align.bottom).size(45.0f);
        stack.addActor(table1);
        table.add(stack);
        stage.addActor(table);

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
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

    @Override
    public void dispose() {
    stage.dispose();
    stage.dispose();
    }
}
