package com.mygdx.game.Level_maker.Collectables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Level_maker.Level_maker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Items {
    private float[][] coordinates = {{316,112},{503,137},{696,91},{976,171},{1114,155},{1388,219},{1525,219},{1562,465},{1278,340},{1130,560},{961,555},{756,586},
            {804,651},{640,619},{640,699},{518,747},{454,763},{549,827},{629,811},{544,874},{428,907},{376,843},{366,971},{317,1002},{286,1067}};
    private boolean empty;
    private SpriteBatch batch;
    private ArrayList<Apple> apples = new ArrayList<>();
    private Level_maker level;
    private Apple object;


    public Items(Level_maker level, SpriteBatch batch) {
        this.level = level;
        this.batch = batch;
        for (float[] coordinate : coordinates){
            object = new Apple(level.getWorld());
            object.body.setTransform(coordinate[0],coordinate[1],0);
            apples.add(object);
        }
    }

    public void toDestroy() {
        Iterator<Apple> iterator = apples.iterator();
        while (iterator.hasNext()) {
            Apple apple = iterator.next();
            if (apple.body.getUserData().equals("DESTROY")) {
                level.getWorld().destroyBody(apple.body);
                iterator.remove();
            }
        }
    }

    public boolean allApples() {
        if (apples.isEmpty()) {
            empty = true;
        }
        return empty;
    }


    public void render() {
        if (!apples.isEmpty()) {
            for (Apple a : apples) {
                a.render(batch);
            }
            toDestroy();
        }
    }
}


