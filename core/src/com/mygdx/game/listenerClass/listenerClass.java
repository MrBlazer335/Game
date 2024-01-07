package com.mygdx.game.listenerClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class listenerClass implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();


        if (fixtureB.getBody().getUserData() == "Spikes") {
            fixtureA.getBody().setUserData(1);
        }
        if (fixtureB.getBody().getUserData() == "Apples") {

            fixtureA.getBody().setUserData("Apple");
            fixtureB.getBody().setUserData("DESTROY");
        }
        if (fixtureB.getBody().getUserData().equals("Block")) {

        }
        if (fixtureA.getBody().getUserData().equals("Finish")) {
            fixtureA.getBody().setUserData(0);
        }

    }


    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        fixtureA.getBody().setUserData(0);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
