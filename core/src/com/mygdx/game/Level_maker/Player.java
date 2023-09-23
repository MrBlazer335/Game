package com.mygdx.game.Level_maker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.input.InputSystem;

public class Player extends InputAdapter {

    private static final float MAX_VELOCITY = 50f;
    PolygonShape playerShape;
    FixtureDef fixtureDef;
    private Body body;
    private float width = 32;
    private float height = 32;
    public float position_x = 200;
    public float position_y = 280;
    float PlayersSpeed = 20.0f;
    private TextureAtlas RunningPlayer;
    private Animation<TextureRegion> Running_animation;



    Texture img;
    float elapsedTime;
    InputSystem inputSystem;

    public Player(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position_x, position_y);

        body = world.createBody(bodyDef);

        RunningPlayer = new TextureAtlas("Textures/player/Run_Animation/RunningFrog.atlas");
        Running_animation = new Animation(1 / 10f ,RunningPlayer.getRegions());
        inputSystem = new InputSystem();
        Gdx.input.setInputProcessor(inputSystem);


        playerShape = new PolygonShape();
        playerShape.setAsBox(width / 2, height / 2);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = playerShape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0.4f;
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

        batch.draw(Running_animation.getKeyFrame(elapsedTime,true), position_x, position_y);

    }

    public void dispose() {
        playerShape.dispose();
        RunningPlayer.dispose();

    }
}
