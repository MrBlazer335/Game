package com.mygdx.game.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Level_maker.Player;

public class Movement extends InputAdapter {
   private enum Facing {LEFT,RIGHT}

    private final Body body;
    public Movement(Player player){
        this.body = player.getBody();
    }
    public void start(){
        Vector2 vel = this.body.getLinearVelocity();
        Vector2 pos = this.body.getPosition();
        float playerSpeed = 50f;
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            Facing facing = Facing.LEFT;

            this.body.applyForceToCenter(-100 * body.getMass(), 10f ,true);
            Gdx.app.log("New Movement","Its Working!");
        }
    }

}