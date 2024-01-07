package com.mygdx.game.Level_maker.EndGoal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Finish {
    private final int position_x;
    private final int position_y;
    private boolean isItReached;
    private final Body endBody;
    private final Texture texture;
    public Finish(World world,int position_x,int position_y){
        this.position_x = position_x;
        this.position_y = position_y;
        texture = new Texture(Gdx.files.internal("Textures/Png Files/End (Idle).png"));
        BodyDef endBlock1 = new BodyDef();
        endBlock1.type = BodyDef.BodyType.StaticBody;

        endBlock1.position.set(position_x,position_y);
        endBody = world.createBody(endBlock1);
        PolygonShape endBlock = new PolygonShape();
        endBlock.setAsBox(17,1);
        FixtureDef fixtureDef = new FixtureDef();

        endBody.setUserData("Finish");
        fixtureDef.shape = endBlock;
        fixtureDef.isSensor = true;
        endBody.createFixture(fixtureDef);

    }
    public void render(SpriteBatch batch){
        batch.draw(texture,position_x-32,position_y);
    }
    public boolean end(){
        if (endBody.getUserData().equals(0)){
            isItReached = true;
        }
        return isItReached;
    }
    public Vector2 getCoordinates(){
        return new Vector2(position_x,position_y);
    }
}
