package com.mygdx.game.Level_maker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class Player extends InputAdapter {

    private static final float MAX_VELOCITY = 50f;
    PolygonShape playerShape;
    FixtureDef fixtureDef;
    private Body body;
    private float width = 32;
    private float height = 32;
    public float position_x = 200;
    public float position_y = 300;
    float PlayersSpeed = 20.0f;
    Animation animation;

    TextureRegion[] animationFramers;

    Texture img;
    float elapsedTime;


    public Player(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position_x, position_y);

        img = new Texture("Textures/player/Run (32x32).png");
        body = world.createBody(bodyDef);
        TextureRegion[][] tmpFrames = TextureRegion.split(img, 32, 32);
        animationFramers = new TextureRegion[4];
        int index = 0;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.println("i = " + i + ", j = " + j);
                animationFramers[index++] = tmpFrames[i][j];
            }
        }
        animation = new Animation(1f/4f,animationFramers);
        playerShape = new PolygonShape();
        playerShape.setAsBox(width / 2, height / 2);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = playerShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.2f;
        body.createFixture(fixtureDef);


    }

    public void render(SpriteBatch batch) {
        position_x = body.getPosition().x;
        position_y = body.getPosition().y;

        Vector2 vel = this.body.getLinearVelocity();
        Vector2 pos = this.body.getPosition();
        if (Gdx.input.isKeyJustPressed(Input.Keys.A) && vel.x > -MAX_VELOCITY) {
            this.body.applyLinearImpulse(-2.80f * PlayersSpeed, 0, pos.x, pos.y, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && vel.x < MAX_VELOCITY) {
            this.body.applyLinearImpulse(2.80f * PlayersSpeed, 0, pos.x, pos.y, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) && vel.y < MAX_VELOCITY) {
            this.body.applyLinearImpulse(0, 1000.80f, pos.x, pos.y, true);
            System.out.println(position_x + " " + position_y);
        }
          /*if (Gdx.input.isKeyPressed(Input.Keys.S)) {
          *    position_y -= 1f;
        } */
        elapsedTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(animation.getKeyFrame(elapsedTime,true),0,0);
        batch.end();
    }

    public void dispose() {
        playerShape.dispose();

    }
}
