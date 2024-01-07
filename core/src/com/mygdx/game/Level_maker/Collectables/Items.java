package com.mygdx.game.Level_maker.Collectables;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Level_maker.Level_maker;

import java.util.ArrayList;
import java.util.Iterator;

public class Items {
    private boolean empty;
    private final SpriteBatch batch;
    private final ArrayList<Apple> apples = new ArrayList<>();
    private final Level_maker level;

    private float[][] coordinates;


    public Items(Level_maker level, SpriteBatch batch,float[][] coordinates) {
        this.level = level;
        this.batch = batch;
        for (float[] coordinate : coordinates){
            Apple object = new Apple(level.getWorld());
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


