package io.github.phucfix.bombermangame.map;

import com.badlogic.gdx.physics.box2d.*;

public class CollisionDetecter implements ContactListener {
    /**
     * beginContact is automatically triggered, when box2d detects a contact between two objects,
     * then we are storing the fixtures of the objects involved in Collision,
     * then we check whether the objects are of class enemy and player and then
     * we want to set the isDead attribute to true if the object is a player, leading to further consequences in player class.
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        System.out.println("Contact detected!");

        if((fixtureA.getBody().getUserData() instanceof Player) && fixtureB.getBody().getUserData() instanceof Enemy) {
            Player player = (Player) fixtureA.getBody().getUserData();
            player.setDead(true);
        } else if ((fixtureB.getBody().getUserData() instanceof Player) && fixtureA.getBody().getUserData() instanceof Enemy){
            Player player = (Player) fixtureB.getBody().getUserData();
            player.setDead(true);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
