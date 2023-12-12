package com.mygdx.game.Level_maker.Collectables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.Random;

public class Apple {

    Body body;
    float elapsedTime;
    Animation<TextureRegion> animation;
    float position_x = 1013;
    float position_y = 338;
    World world;


    public Apple(World world) {


        this.world = world;
        TextureAtlas textureRegion = new TextureAtlas(Gdx.files.internal("Textures/Collectables/Apple.atlas"));
        animation = new Animation<TextureRegion>(1 / 20f, textureRegion.getRegions());


        Random random = new Random();


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;


        bodyDef.position.set(random.nextFloat(300, 500), random.nextFloat(138, 150));
        body = world.createBody(bodyDef);
        body.setUserData("Apples");

        CircleShape circle = new CircleShape();
        circle.setRadius(5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        body.createFixture(fixtureDef);


    }
    public void status(){
        Gdx.app.log("PLACE","Coordinates: " + this.body.getPosition());
        Gdx.app.log("INFO","STATUS: " + this.body.getUserData());
    }

    public void render(SpriteBatch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(elapsedTime, true), body.getPosition().x - 15, body.getPosition().y - 15);
        status();
    }

}

