package com.mygdx.game.Level_maker.Collectables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Level_maker.Level_maker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Items {

    SpriteBatch batch;
    ArrayList<Apple> apples = new ArrayList<>();
    Level_maker level;
    Apple object;


    public Items(int amount, Level_maker level, SpriteBatch batch) {
        this.level = level;
        this.batch = batch;
        for (int i = 1; i <= amount; i++) {
            object = new Apple(level.getWorld());
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

    public void render() {
        if (!apples.isEmpty()) {
            for (Apple a : apples) {
                a.render(batch);
            }
            toDestroy();
            Gdx.app.log("Amount", "Amount = " + apples.size());
        }
    }
}


