package com.mygdx.game.Level_maker.Collectables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.*;

public class Apple {
    Body body;
    float elapsedTime;
    Animation<TextureRegion> animation;

    public Apple(World world) {
        TextureAtlas textureRegion = new TextureAtlas(Gdx.files.internal("Textures/Collectables/Apple.atlas"));
        animation = new Animation<TextureRegion>(1 / 20f, textureRegion.getRegions());
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        int positon_x = 1013;
        int position_y = 138;
        bodyDef.position.set(1013, 138);

        body = world.createBody(bodyDef);


        CircleShape circle = new CircleShape();
        circle.setRadius(5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        body.createFixture(fixtureDef);


        elapsedTime += Gdx.graphics.getDeltaTime();


    }

    public void render(SpriteBatch batch, float cameraX, float cameraY) {
        float adjustedX = body.getPosition().x - cameraX;
        float adjustedY = body.getPosition().y - cameraY;
        batch.draw(animation.getKeyFrame(elapsedTime, true), adjustedX, adjustedY);
    }
}
