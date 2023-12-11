package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Level_maker.DeathScene;
import com.mygdx.game.Menu.MainMenu;


public class MyGdxGame extends Game {
    SpriteBatch batch;

    MainMenu mainMenu;
    DeathScene deathScene;


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

    }
}
