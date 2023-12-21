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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Level_maker.Level_Wrapper;
import com.mygdx.game.UI.MainMenu;
import com.mygdx.game.MyGdxGame;

public class DeathScene implements Screen {
    final MyGdxGame game;
    private Skin skin;

    private Stage stage;


   public DeathScene(final MyGdxGame game) {
       this.game = game;
       stage = new Stage(new StretchViewport(600,700));
       skin = new Skin(Gdx.files.internal("deathSkin.json"));
       Gdx.input.setInputProcessor(stage);

       Table table = new Table();
       table.setFillParent(true);

       Stack stack = new Stack();

       Image image = new Image(skin, "R");
       image.setScaling(Scaling.fill);
       stack.addActor(image);

       Label label = new Label("you died", skin);
       label.setAlignment(Align.top);
       stack.addActor(label);

       Table table1 = new Table();
       table1.align(Align.bottom);

       Button button = new Button(skin);
       button.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               game.setScreen(new Level_Wrapper(game));
               dispose();

           }
       });
       button.setName("Restart");
       table1.add(button).spaceRight(40.0f).height(40.0f).minWidth(40.0f).maxWidth(40.0f).prefWidth(45.0f);

       button = new Button(skin, "close");
       button.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               game.setScreen(new MainMenu(game));
               dispose();

           }
       });
       button.setName("Close");
       table1.add(button).size(45.0f);
       stack.addActor(table1);
       table.add(stack);
       stage.addActor(table);



//
//
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
