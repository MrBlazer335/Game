package com.mygdx.game.Level_maker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.ray3k.stripe.scenecomposer.SceneComposerStageBuilder;

public class DeathScene implements Screen {
    final MyGdxGame game;
    private Skin skin;

    private Stage stage;


   public DeathScene(final MyGdxGame game){
       this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("deathSkin.json"));
        SceneComposerStageBuilder builder = new SceneComposerStageBuilder();
        builder.build(stage,skin,Gdx.files.internal("death.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        Label label = new Label("you diedOOO", skin);
        table.add(label).expand();

        table.row();
        Button button = new Button(skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level_Wrapper(game));

            }
        });
        table.add(button);
        stage.addActor(table);
        stage.addAction(Actions.fadeIn(1));
    }
    public void show() {
        stage.getRoot().setVisible(true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
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
    }
}
