package com.mygdx.game.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.mygdx.game.Level_maker.Player;

public class Movement extends InputAdapter {
    private final Image leftArrowUP = new Image(new Texture(Gdx.files.internal("Textures/Buttons/blue-!arrowleft.png")));
    private final Image leftArrowDOWN = new Image(new Texture(Gdx.files.internal("Textures/Buttons/blue-!arrowleft-pushed.png")));
    private final Image rightArrowUP = new Image(new Texture(Gdx.files.internal("Textures/Buttons/blue-!arrowright.png")));
    private final Image rightArrowDOWN = new Image(new Texture(Gdx.files.internal("Textures/Buttons/blue-!arrowright-pushed.png")));

    private Button leftButton;
    private Button rightButton;

    private Button.ButtonStyle leftStyleButton;

    private Button.ButtonStyle rightStyleButton;

    private enum Facing {LEFT, RIGHT}


    private Player playerControlling;

    private int jumpCounter = 0;
    private final Body body;

    private Stage stage;


    public Movement(Player player, SpriteBatch batch) {
        this.body = player.getBody();
        playerControlling = player;
        //Camera for buttons
        OrthographicCamera uiCamera = new OrthographicCamera(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/4);
        uiCamera.position.set(0, 0, 0);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        leftStyleButton = new Button.ButtonStyle();

        leftStyleButton.up = leftArrowUP.getDrawable();
        leftStyleButton.down = leftArrowDOWN.getDrawable();

        leftButton = new Button(leftStyleButton);
        leftButton.setSize(uiCamera.viewportWidth/2,uiCamera.viewportHeight/2);
        leftButton.setPosition(uiCamera.position.x, uiCamera.position.y);
        stage.addActor(leftButton);

        rightStyleButton = new Button.ButtonStyle();
        rightStyleButton.up = rightArrowUP.getDrawable();
        rightStyleButton.down = rightArrowDOWN.getDrawable();

        rightButton = new Button(rightStyleButton);
        rightButton.setSize(uiCamera.viewportWidth/2, uiCamera.viewportHeight/2);
        rightButton.setPosition((uiCamera.position.x + uiCamera.viewportWidth)/2, uiCamera.position.y);
        stage.addActor(rightButton);
    }

    public void movement() {

        Vector2 vel = this.body.getLinearVelocity();
        Vector2 pos = this.body.getPosition();
        if (vel.y==0){
            playerControlling.onTheGround = true;
        }

        float playersSpeed = 50f;
         if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerControlling.facing = Player.Facing.LEFT;
            this.body.applyLinearImpulse(-playersSpeed, 0, pos.x, pos.y, true);
            playerControlling.setCurrentState(Player.Player_state.Running);

        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerControlling.facing = Player.Facing.RIGHT;
            this.body.applyLinearImpulse(playersSpeed, 0, pos.x, pos.y, true);
            playerControlling.setCurrentState(Player.Player_state.Running);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && playerControlling.onTheGround || Gdx.input.isKeyJustPressed(Input.Keys.UP) && playerControlling.onTheGround ||  Gdx.input.isKeyJustPressed(Input.Keys.BUTTON_A) && playerControlling.onTheGround) {
            this.body.applyLinearImpulse(0, 2500f, pos.x, pos.y, true);
            playerControlling.CurrentState = Player.Player_state.Jumping;
            playerControlling.jumpCounter++;
            playerControlling.getJump.play(0.1f);
        }else  if (Gdx.input.isKeyJustPressed(Input.Keys.W) && playerControlling.jumpCounter == 1 || Gdx.input.isKeyJustPressed(Input.Keys.UP) && playerControlling.jumpCounter == 1 || Gdx.input.isKeyJustPressed(Input.Keys.BUTTON_A) && playerControlling.jumpCounter == 1) {
            this.body.setLinearVelocity(vel.x,0);
            this.body.applyLinearImpulse(0, 2000f, pos.x, pos.y, true);
            playerControlling.CurrentState = Player.Player_state.DoubleJumping;

            playerControlling.jumpCounter = 0;
            playerControlling.getJump.play(0.1f);

        }
    }


    public void render() {
        movement();
        SpriteBatch uiBatch = new SpriteBatch();
        System.out.println(playerControlling.facing);
        uiBatch.begin();
        stage.draw();
        uiBatch.end();


    }


}

