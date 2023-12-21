package com.mygdx.game.Level_maker.Collectables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public class Apple {
    Body body;
    float elapsedTime;
    Animation<TextureRegion> animation;
    TextureAtlas textureRegion = new TextureAtlas(Gdx.files.internal("Textures/Collectables/Apple.atlas"));
    World world;


    public Apple(World world) {


        this.world = world;

        animation = new Animation<TextureRegion>(1 / 20f, textureRegion.getRegions());





        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;


        bodyDef.position.set(0,0);
        body = world.createBody(bodyDef);
        body.setUserData("Apples");

        CircleShape circle = new CircleShape();
        circle.setRadius(5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);


    }

    public void render(SpriteBatch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(elapsedTime, true), body.getPosition().x - 15, body.getPosition().y - 15);
    }

}

