package com.mygdx.game.Level_maker.EndGoal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import sun.jvm.hotspot.ui.FindInHeapPanel;

public class Finish {
    Body body;
    BodyDef endBlock;
    World world;
    SpriteBatch batch;
    Texture texture;
    public Finish(World world){
        this.world = world;
        texture = new Texture(Gdx.files.internal("Textures/Png Files/End (Idle).png"));
        endBlock = new BodyDef();
        endBlock.position.set(132,80);
        Body endBody = world.createBody(endBlock);
        PolygonShape endBlock = new PolygonShape();
        endBlock.setAsBox(17,1);
        FixtureDef fixtureDef = new FixtureDef();
        endBody.setUserData("Finish");
        fixtureDef.shape = endBlock;
        fixtureDef.isSensor = false;
        endBody.createFixture(fixtureDef);

    }
    public void render(SpriteBatch batch){
        this.batch = batch;
        batch.draw(texture,100,80);
    }
}
