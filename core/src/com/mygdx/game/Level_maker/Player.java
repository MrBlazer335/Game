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

    boolean onTheGround = true;
    int jumpCounter = 0;
    PolygonShape playerShape;
    FixtureDef fixtureDef;
    private final Body body;
    public float position_x = 200;
    public float position_y = 280;
    public float PlayersSpeed = 50.0f;
    private TextureAtlas RunningPlayer;
    private TextureAtlas IdlePlayer;
    private TextureAtlas BIdlePlayer;
    private TextureAtlas JumpingPlayer;
    private TextureAtlas DoubleJump;
    private TextureAtlas FallingPlayer;
    private TextureAtlas BRunningPlayer;


    Animation<TextureRegion> animation;

    enum Facing {LEFT, RIGHT}

    Facing facing = Facing.RIGHT;

    enum Player_state {Running, Staying, Jumping, Falling, DoubleJumping}

    Player_state CurrentState = Player_state.Staying;
    float elapsedTime;
    InputSystem inputSystem;

    public Player(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position_x, position_y);


        body = world.createBody(bodyDef);

        RunningPlayer = new TextureAtlas("Textures/player/Run_Animation/RunningFrog.atlas");

        BRunningPlayer = new TextureAtlas("Textures/player/Run_Animation/BRunningPlayer.atlas");

        JumpingPlayer = new TextureAtlas("Textures/player/Idle_Animation/Jumping.atlas");

        DoubleJump = new TextureAtlas("Textures/player/Idle_Animation1/DoubleJump.atlas");

        IdlePlayer = new TextureAtlas("Textures/player/Idle_Animation/Idle_Animation.atlas");

        BIdlePlayer = new TextureAtlas("Textures/player/Idle_Animation/BIdle_Animation.atlas");

        FallingPlayer = new TextureAtlas("Textures/player/Idle_Animation/FallingPlayer.atlas");


        inputSystem = new InputSystem();
        Gdx.input.setInputProcessor(inputSystem);


        playerShape = new PolygonShape();
        float width = 32;
        float height = 32;
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


        boolean ontheGround = true;
        if (vel.y > -0.1f && vel.y < 0.1f) {
            jumpCounter = 0;

        } else {
            ontheGround = false;
        }

        /*
        if (Gdx.input.isKeyPressed(Input.Keys.A) && vel.x > -MAX_VELOCITY) {
            this.body.applyLinearImpulse(-2.80f * PlayersSpeed, 0, pos.x, pos.y, true);
            CurrentState = Player_state.Running;
            facing = Facing.LEFT;

        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && vel.x < MAX_VELOCITY) {
            this.body.applyLinearImpulse(2.80f * PlayersSpeed, 0, pos.x, pos.y, true);
            CurrentState = Player_state.Running;
            facing = Facing.RIGHT;
        } else {
            // Если ни одна из клавиш не нажата, установите линейный импульс в противоположное направление, чтобы остановить движение.
            float stopImpulse = -vel.x * this.body.getMass(); // Примените импульс, противоположный текущей скорости
            this.body.applyLinearImpulse(stopImpulse, 0, pos.x, pos.y, true);
            CurrentState = Player_state.Staying;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) && vel.y < MAX_VELOCITY) {
            this.body.setLinearVelocity(0f,6.29f);
            this.body.applyLinearImpulse(0f, 10.80f, pos.x, pos.y, true);
            System.out.println(jumpCounter);
            jumpCounter += 1;
            if (jumpCounter == 1) {
                CurrentState = Player_state.Jumping;
            } else {
                CurrentState = Player_state.DoubleJumping;
                jumpCounter = 0;
            }


        }
        if (this.body.getLinearVelocity().len() == 0) {
            CurrentState = Player_state.Staying;
        }
        if (vel.y < -5) {
            CurrentState = Player_state.Falling;
        }
        System.out.println(this.body.getMass());

         */

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            facing = Facing.LEFT;
            this.body.applyLinearImpulse(-PlayersSpeed, vel.y, pos.x, pos.y, true);
            CurrentState = Player_state.Running;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            facing = Facing.RIGHT;
            this.body.applyLinearImpulse(PlayersSpeed, vel.y, pos.x, pos.y, true);
            CurrentState = Player_state.Running;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && CurrentState != Player_state.DoubleJumping || Gdx.input.isKeyJustPressed(Input.Keys.UP) ) {
            this.body.applyLinearImpulse(vel.x, 1500f, pos.x, pos.y, true);
            CurrentState = Player_state.Jumping;
            jumpCounter++;
            System.out.println(jumpCounter);
        }
            else if(jumpCounter == 2){
            CurrentState = Player_state.DoubleJumping;
                jumpCounter = 0;

            }


        if (vel.y < -2.5f && !ontheGround) {
            CurrentState = Player_state.Falling;
        }
        if (this.body.getLinearVelocity().

                len() == 0) {
            CurrentState = Player_state.Staying;
        }


        elapsedTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (CurrentState.equals(Player_state.Running) && facing.equals(Facing.RIGHT) && ontheGround) {
            animation = new Animation<TextureRegion>(1 / 10f, RunningPlayer.getRegions());
        }
        if (CurrentState.equals(Player_state.Running) && facing.equals(Facing.LEFT) && ontheGround) {
            animation = new Animation<TextureRegion>(1 / 10f, BRunningPlayer.getRegions());
        }
        if (CurrentState.equals(Player_state.Jumping)) {
            animation = new Animation<TextureRegion>(1 / 10f, JumpingPlayer.getRegions());

        }
        if (CurrentState.equals(Player_state.DoubleJumping)) {
            animation = new Animation<TextureRegion>(1 / 10f, DoubleJump.getRegions());
        }
        if (CurrentState.equals(Player_state.Falling)) {
            animation = new Animation<TextureRegion>(1 / 10f, FallingPlayer.getRegions());
        }
        if (CurrentState.equals(Player_state.Staying) && facing.equals(Facing.RIGHT)) {
            animation = new Animation<TextureRegion>(1 / 10f, IdlePlayer.getRegions());
        }
        if (CurrentState.equals(Player_state.Staying) && facing.equals(Facing.LEFT)) {
            animation = new Animation<TextureRegion>(1 / 10f, BIdlePlayer.getRegions());
        }
        batch.draw(animation.getKeyFrame(elapsedTime, true), position_x, position_y);

    }

    public void dispose() {
        playerShape.dispose();
        RunningPlayer.dispose();
        FallingPlayer.dispose();
        JumpingPlayer.dispose();
        IdlePlayer.dispose();
        DoubleJump.dispose();

    }
}
