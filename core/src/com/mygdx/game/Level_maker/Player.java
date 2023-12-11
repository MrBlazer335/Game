package com.mygdx.game.Level_maker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


public class Player extends InputAdapter {

    private int Health = 3;
    private int jumpCounter = 0;
    PolygonShape playerShape;
    FixtureDef fixtureDef;
    private final Body body;
    private final TextureAtlas RunningPlayer;
    private final TextureAtlas IdlePlayer;
    private final TextureAtlas BIdlePlayer;
    private final TextureAtlas JumpingPlayer;
    private final TextureAtlas BJumpingPlayer;
    private final TextureAtlas DoubleJump;
    private final TextureAtlas FallingPlayer;
    private final TextureAtlas BFallingPlayer;
    private final TextureAtlas BRunningPlayer;
    private final TextureAtlas DyingPlayer;


    Animation<TextureRegion> animation;

//Dying Part

    Animation<TextureRegion> PlayerIsDying;


    enum Facing {LEFT, RIGHT}

    Facing facing = Facing.LEFT;

    enum Player_state {Running, Staying, Jumping, Falling, DoubleJumping, Dying}

    Player_state CurrentState = Player_state.Staying;
    float elapsedTime;


    public Player(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        float position_y = 280;
        float position_x = 200;
        bodyDef.position.set(position_x, position_y);


        body = world.createBody(bodyDef);

        RunningPlayer = new TextureAtlas("Textures/player/Animation/RunningFrog.atlas");

        BRunningPlayer = new TextureAtlas("Textures/player/BAnimation/BRunningPlayer.atlas");

        JumpingPlayer = new TextureAtlas("Textures/player/Animation/Jumping.atlas");

        BJumpingPlayer = new TextureAtlas("Textures/player/BAnimation/BJumping.atlas");

        DoubleJump = new TextureAtlas("Textures/player/Animation/DoubleJump.atlas");

        IdlePlayer = new TextureAtlas("Textures/player/Animation/Idle_Animation.atlas");

        BIdlePlayer = new TextureAtlas("Textures/player/BAnimation/BIdle_Animation.atlas");

        FallingPlayer = new TextureAtlas("Textures/player/Animation/FallingPlayer.atlas");

        BFallingPlayer = new TextureAtlas("Textures/player/BAnimation/BFall.atlas");

        DyingPlayer = new TextureAtlas("Textures/player/Animation/Dying.atlas");

        PlayerIsDying = new Animation<TextureRegion>(1 / 10f, DyingPlayer.getRegions());

        playerShape = new PolygonShape();
        float width = 12;
        float height = 22;
        playerShape.setAsBox(width / 2, height / 2);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = playerShape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0.1f;
        // fixtureDef.restitution = -0.1f;
        body.createFixture(fixtureDef);
        body.setFixedRotation(true);
        body.setUserData(0);


    }

    public int getHealth() {
        return this.Health;
    }

    public void render(SpriteBatch batch) {

        TakingDamage();



        Vector2 vel = this.body.getLinearVelocity();
        Vector2 pos = this.body.getPosition();


        boolean onTheGround = true;
        if (vel.y > -0.1f && vel.y < 0.1f) {
            jumpCounter = 0;

        } else {
            onTheGround = false;
        }
        if (CurrentState != Player_state.Dying) {
            float playersSpeed = 50.0f;
            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                facing = Facing.LEFT;
                this.body.applyLinearImpulse(-playersSpeed, vel.y, pos.x, pos.y, true);
                CurrentState = Player_state.Running;

            }

            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                facing = Facing.RIGHT;
                this.body.applyLinearImpulse(playersSpeed, vel.y, pos.x, pos.y, true);
                CurrentState = Player_state.Running;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.W) && onTheGround || Gdx.input.isKeyJustPressed(Input.Keys.UP) && onTheGround) {
                this.body.applyLinearImpulse(vel.x, 1700f, pos.x, pos.y, true);
                CurrentState = Player_state.Jumping;
                jumpCounter++;
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.W) && jumpCounter == 1 || Gdx.input.isKeyJustPressed(Input.Keys.UP) && jumpCounter == 1) {
                this.body.applyLinearImpulse(vel.x, 1500f, pos.x, pos.y, true);
                CurrentState = Player_state.DoubleJumping;
                jumpCounter = 0;
            }
        }

        if (vel.y < -2.5f && !onTheGround) {
            CurrentState = Player_state.Falling;
        }
        if (this.body.getLinearVelocity().len() == 0 && CurrentState != Player_state.Dying) {
            CurrentState = Player_state.Staying;
        }


        elapsedTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (CurrentState.equals(Player_state.Running) && facing.equals(Facing.RIGHT) && onTheGround) {
            animation = new Animation<TextureRegion>(1 / 20f, RunningPlayer.getRegions());
        }
        if (CurrentState.equals(Player_state.Running) && facing.equals(Facing.LEFT) && onTheGround) {
            animation = new Animation<TextureRegion>(1 / 20f, BRunningPlayer.getRegions());
        }
        if (CurrentState.equals(Player_state.Jumping) && facing == Facing.RIGHT) {
            animation = new Animation<TextureRegion>(1 / 20f, JumpingPlayer.getRegions());
        }
        if (CurrentState.equals(Player_state.Jumping) && facing == Facing.LEFT) {
            animation = new Animation<TextureRegion>(1 / 20f, BJumpingPlayer.getRegions());
        }

        if (CurrentState.equals(Player_state.DoubleJumping)) {
            animation = new Animation<TextureRegion>(1 / 20f, DoubleJump.getRegions());
        }
        if (CurrentState.equals(Player_state.Falling) && facing == Facing.RIGHT) {
            animation = new Animation<TextureRegion>(1 / 20f, FallingPlayer.getRegions());
        }
        if (CurrentState.equals(Player_state.Falling) && facing == Facing.LEFT) {
            animation = new Animation<TextureRegion>(1 / 20f, BFallingPlayer.getRegions());
        }
        if (CurrentState.equals(Player_state.Staying) && facing.equals(Facing.RIGHT)) {
            animation = new Animation<TextureRegion>(1 / 20f, IdlePlayer.getRegions());
        }
        if (CurrentState.equals(Player_state.Staying) && facing.equals(Facing.LEFT)) {
            animation = new Animation<TextureRegion>(1 / 20f, BIdlePlayer.getRegions());
        }
        if (CurrentState.equals(Player_state.Dying)) {
            animation = new Animation<TextureRegion>(1 / 15f, DyingPlayer.getRegions());
        }
        batch.draw(animation.getKeyFrame(elapsedTime, true), pos.x - 16f, pos.y - 12.5f);
    }


    public void TakingDamage() {
        if (this.body.getUserData() == (Integer) (1)) {
            Health--;
        }
    }


    public float CameraCordsX() {
        Vector2 vector2;
        vector2 = this.body.getPosition();
        return vector2.x;
    }

    public float CameraCordsY() {
        Vector2 vector2;
        vector2 = this.body.getPosition();
        return vector2.y;
    }

}
