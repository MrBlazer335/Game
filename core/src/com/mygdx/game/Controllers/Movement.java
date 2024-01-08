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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.mygdx.game.Level_maker.Player;

public class Movement extends ApplicationAdapter {
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
        float playerSpeed =50f;
        if (vel.y==0){
            playerControlling.onTheGround = true;
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

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

}

