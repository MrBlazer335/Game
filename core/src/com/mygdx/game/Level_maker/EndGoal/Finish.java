package com.mygdx.game.Level_maker.EndGoal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import sun.jvm.hotspot.ui.FindInHeapPanel;

public class Finish {
    private int position_x;
    private int position_y;
    private boolean isItReached;
    private Body endBody;
    private BodyDef endBlock;
    private final World world;
    private SpriteBatch batch;
    private Texture texture;
    public Finish(World world){
        position_x = 70;
        position_y = 48;
        this.world = world;
        texture = new Texture(Gdx.files.internal("Textures/Png Files/End (Idle).png"));
        endBlock = new BodyDef();
        endBlock.type = BodyDef.BodyType.StaticBody;

        endBlock.position.set(position_x,position_y);
        endBody = world.createBody(endBlock);
        PolygonShape endBlock = new PolygonShape();
        endBlock.setAsBox(17,1);
        FixtureDef fixtureDef = new FixtureDef();

        endBody.setUserData("Finish");
        fixtureDef.shape = endBlock;
        fixtureDef.isSensor = true;
        endBody.createFixture(fixtureDef);

    }
    public void render(SpriteBatch batch){
        this.batch = batch;
        batch.draw(texture,position_x-32,position_y);
    }
    public boolean end(){
        if (endBody.getUserData().equals(0)){
            isItReached = true;
        }
        return isItReached;
    }
}
