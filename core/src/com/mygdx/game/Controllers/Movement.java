package com.mygdx.game.Controllers;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Level_maker.Player;

public class Movement extends ApplicationAdapter {
    private final Image leftArrowUP = new Image(new Texture(Gdx.files.internal("Textures/Buttons/blue-!arrowleft.png")));
    private final Image leftArrowDOWN = new Image(new Texture(Gdx.files.internal("Textures/Buttons/blue-!arrowleft-pushed.png")));
    private final Image rightArrowUP = new Image(new Texture(Gdx.files.internal("Textures/Buttons/blue-!arrowright.png")));
    private final Image rightArrowDOWN = new Image(new Texture(Gdx.files.internal("Textures/Buttons/blue-!arrowright-pushed.png")));

    private final Image transparentImage = new Image(new Texture(Gdx.files.internal("Textures/Buttons/no_texture.png")));


    private Button leftButton;
    private Button rightButton;

    private Button jumpButton;

    private Button.ButtonStyle leftStyleButton;

    private Button.ButtonStyle rightStyleButton;

    private Button.ButtonStyle jumpButtonStyle;

    private enum Facing {LEFT, RIGHT}

    SpriteBatch uiBatch = new SpriteBatch();
    private Player playerControlling;

    private int jumpCounter = 0;
    private final Body body;

    private Stage stage;

    Vector2 pos;
    Vector2 vel;


    public Movement(Player player) {
        this.body = player.getBody();
        playerControlling = player;
        //Camera for buttons
        OrthographicCamera uiCamera = new OrthographicCamera(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4);
        uiCamera.position.set(0, 0, 0);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        jumpButtonStyle = new Button.ButtonStyle();
        jumpButtonStyle.up = transparentImage.getDrawable();
        jumpButtonStyle.down = transparentImage.getDrawable();
        jumpButton = new Button(jumpButtonStyle);

        jumpButton.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        jumpButton.setPosition(uiCamera.viewportWidth + uiCamera.viewportWidth / 1.3f, 0);
        jumpButton.setTouchable(Touchable.enabled);
        jumpButton.addListener(new ClickListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (jumpButton.isPressed()) {
                    if (playerControlling.jumpCounter == 0 && playerControlling.onTheGround) {
                        jumpButtonDown();
                        return true;
                    } else if (playerControlling.jumpCounter == 1) {
                        jumpButtonDownTwice();
                        return true;
                    }
                }
                return true;
            }
        });
        stage.addActor(jumpButton);

        leftStyleButton = new Button.ButtonStyle();

        leftStyleButton.up = leftArrowUP.getDrawable();
        leftStyleButton.down = leftArrowDOWN.getDrawable();

        leftButton = new Button(leftStyleButton);
        leftButton.setSize(uiCamera.viewportWidth / 1.5f, uiCamera.viewportHeight / 1.5f);
        leftButton.setPosition(uiCamera.position.x, uiCamera.position.y);
        stage.addActor(leftButton);


        rightStyleButton = new Button.ButtonStyle();
        rightStyleButton.up = rightArrowUP.getDrawable();
        rightStyleButton.down = rightArrowDOWN.getDrawable();

        rightButton = new Button(rightStyleButton);
        rightButton.setSize(uiCamera.viewportWidth / 1.5f, uiCamera.viewportHeight / 1.5f);
        rightButton.setPosition((uiCamera.position.x + uiCamera.viewportWidth*1.5f) / 2, uiCamera.position.y);
        stage.addActor(rightButton);
        pos = this.body.getPosition();
        vel = this.body.getLinearVelocity();


    }

    public void movement() {

        if (vel.y == 0) {
            playerControlling.onTheGround = true;
        }
        falling();
        staying();
        leftButtonDown();
        rightButtonDown();
//        jumpButtonDown();
//        jumpButtonDownTwice();
        playerControlling.animate();


    }


    public void render() {
        Gdx.app.log("Jump",String.valueOf(playerControlling.jumpCounter));
        movement();
        playerControlling.animate();
        uiBatch.begin();
        stage.draw();
        uiBatch.end();


    }

    public void leftButtonDown() {
        if (leftButton.isPressed()) {
            this.body.applyLinearImpulse(-30f, 0, pos.x, pos.y, true);
            playerControlling.setCurrentState(Player.Player_state.Running);
            playerControlling.setFacing(Player.Facing.LEFT);

        }
    }

    public void rightButtonDown() {
        if (rightButton.isPressed()) {
            this.body.applyLinearImpulse(30f, 0, pos.x, pos.y, true);
            playerControlling.setCurrentState(Player.Player_state.Running);
            playerControlling.setFacing(Player.Facing.RIGHT);
        }
    }

    public void jumpButtonDown() {
        if (jumpButton.isPressed()) {
            if (playerControlling.jumpCounter == 0 && playerControlling.onTheGround) {

                playerControlling.getBody().applyLinearImpulse(0, 2500f, pos.x, pos.y, true);
                playerControlling.setCurrentState(Player.Player_state.Jumping);
                playerControlling.jumpCounter++;
                playerControlling.getJump.play(0.1f);
            }
        }
    }
    public void jumpButtonDownTwice(){
            if (playerControlling.jumpCounter == 1) {
                playerControlling.getBody().setLinearVelocity(vel.x, 0);
                playerControlling.getBody().applyLinearImpulse(0, 2500f, pos.x, pos.y, true);
                playerControlling.setCurrentState(Player.Player_state.DoubleJumping);
                playerControlling.jumpCounter = 0;
                playerControlling.getJump.play(0.1f);
            }
        }


    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void staying() {
        if (this.body.getLinearVelocity().y == 0) {
            playerControlling.onTheGround = true;
            playerControlling.setCurrentState(Player.Player_state.Staying);
            playerControlling.jumpCounter = 0;

        } else {
            playerControlling.onTheGround = false;
        }
    }

    public void falling() {
        if (vel.y < -2.5f && !playerControlling.onTheGround && playerControlling.CurrentState != Player.Player_state.DoubleJumping) {
            playerControlling.setCurrentState(Player.Player_state.Falling);
        }
    }

}

