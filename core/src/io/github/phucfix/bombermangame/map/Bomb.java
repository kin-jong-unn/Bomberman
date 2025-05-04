package io.github.phucfix.bombermangame.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import io.github.phucfix.bombermangame.texture.Animations;
import io.github.phucfix.bombermangame.texture.Drawable;

public class Bomb implements Drawable {
    private final float x;
    private final float y;
    private float elapsedTime;
    private final Body hitbox;
    private final float bombTimer;

    public static final int smallBombRadius = 1;
    public static final int bigBombRadius = 2;

    private boolean increasedBombRadius = false;

    public Bomb(World world, float x, float y) {
        this.x = x;
        this.y = y;
        this.hitbox = createHitbox(world, x, y);
        this.elapsedTime = 0;
        this.bombTimer = 3;
    }

    private Body createHitbox(World world,float x, float y) {
        // BodyDef is like a blueprint for the movement properties of the body.
        BodyDef bodyDef = new BodyDef();
        // Static bodies never move, but static bodies can collide with them.
        bodyDef.type = BodyDef.BodyType.StaticBody;
        // Set the initial position of the body.
        bodyDef.position.set(x,y);
        // Create the body in the world using the body definition.
        Body body = world.createBody(bodyDef);
        // Now we need to give the body a shape so the physics engine knows how to collide with it.
        // We'll use a polygon shape for the chest.
        PolygonShape box = new PolygonShape();
        // Make the polygon a square with a side length of 1 tile.
        box.setAsBox(0.4f, 0.4f);
        // Attach the shape to the body as a fixture.
        body.createFixture(box, 1.0f).setSensor(false);
        // We're done with the shape, so we should dispose of it to free up memory.
        box.dispose();
        // Set the chest as the user data of the body so we can look up the chest from the body later.
        body.setUserData(this);
        return body;
    }

    public void tick(float frameTime) {
        this.elapsedTime += frameTime;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        /// If the bomb has exploded, show the explosion animation.
        if (elapsedTime >= bombTimer) {
            destroy(); // Deactivate the bomb's hitbox when the bomb explodes.
            // Show the explosion animation
            return Animations.BOMB_BLAST.getKeyFrame(this.elapsedTime - bombTimer, false);
        }
        /// Shows the ticking animation, looping as long as the bomb is ticking
        else if (elapsedTime < bombTimer) {
            return Animations.BOMB_TICKING.getKeyFrame(this.elapsedTime, true);
        }

        /// null when no bomb is planted
        return null;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    public void destroy(){
        hitbox.setActive(false);
    }

    /// Used to solidify the bomb as soon as the player is outside the bomb grid
    public void setSensor(boolean isSensor) {
        for (Fixture fixture : hitbox.getFixtureList()) {
            fixture.setSensor(isSensor);
        }
    }

    public int getBombRadius() {
        if(isIncreasedBombRadius()) {
            return bigBombRadius;
        } else {
            return smallBombRadius;
        }
    }

    public boolean isIncreasedBombRadius() {
        return increasedBombRadius;
    }

    public void setIncreasedBombRadius(boolean increasedBombRadius) {
        this.increasedBombRadius = increasedBombRadius;
    }
}
