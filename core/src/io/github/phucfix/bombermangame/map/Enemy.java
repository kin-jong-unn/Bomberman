package io.github.phucfix.bombermangame.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import io.github.phucfix.bombermangame.texture.Animations;
import io.github.phucfix.bombermangame.texture.Drawable;

public class Enemy implements Drawable {
    // Total time elapsed since the game startd. Using it to calc the player movement and animating it
    private float elapsedTime;

    // The box2d hit box, use for collision detection
    private final Body hitbox;

    public Enemy(World world, float x, float y) {
        this.hitbox = createHitbox(world, x, y);
    }


    /**
     * Create box2d body
     * Used to move around and detect collision with the bodies
     * @param world The Box2D world to add the body to.
     * @param startX The initial X position.
     * @param startY The initial Y position.
     * @return The created body.

     */
    private Body createHitbox(World world, float startX, float startY) {
        // Body box is like a bluee print for the movement properties of body
        BodyDef bodyDef = new BodyDef();
        // Dynamic bodies are affected by forces and colicsions
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set the initial position
        bodyDef.position.set(startX, startY);
        // Create body in the world using the body def
        Body body = world.createBody(bodyDef);
        // Now give the body a shape so the physics engine knows how to collide it
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.475f);
        // Attach the shape to the body as a fixture
        // Bodies can have multi fixtures, but we only need one
        body.createFixture(circleShape, 1f);
        // Done with the shape so free it
        circleShape.dispose();
        // Set the player as the user data of the body so we can look up the player from the body later
        body.setUserData(this);
        return body;
    }

    /*
    Move the object next frame
     */
    public void tick(float frameTime) {
        elapsedTime += frameTime;
    }

    public TextureRegion getCurrentAppearance() {
        return Animations.ENEMY_WALK_RIGHT.getKeyFrame(this.elapsedTime, true);
    }

    @Override
    public float getX() {
        return hitbox.getPosition().x;
    }

    @Override
    public float getY() {
        return hitbox.getPosition().y;
    }

    public TextureRegion demise() {
        return Animations.CHARACTER_DEMISE.getKeyFrame(this.elapsedTime, false);
    }
}
