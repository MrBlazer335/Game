package com.mygdx.game.Level_maker.EndGoal;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import sun.jvm.hotspot.ui.FindInHeapPanel;

public class Finish {
    Body body;
    BodyDef endBlock;
    public Finish(){
        endBlock = new BodyDef();
        endBlock.position.set(300,300);
    }
}
