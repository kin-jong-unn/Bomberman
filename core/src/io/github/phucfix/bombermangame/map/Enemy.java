package io.github.phucfix.bombermangame.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import io.github.phucfix.bombermangame.texture.Animations;
import io.github.phucfix.bombermangame.texture.Drawable;

public class Enemy implements Drawable {
    // Total time elapsed since the game startd. Using it to calc the player movement and animating it
    private float elapsedTime;

    private boolean isDestroyed;

    // The box2d hit box, use for collision detection
    private final Body hitbox;

    public Enemy(World world, float x, float y) {
        this.hitbox = createHitbox(world, x, y);
        this.isDestroyed = false;
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
//        bodyDef.awake = true;
//        bodyDef.fixedRotation = true;
//        //bodyDef.bullet = true;
//        bodyDef.active = true;
        //        bodyDef.angularDamping = 45.0f;
        //        bodyDef.angularVelocity = 2.0f;
        // Create body in the world using the body def
        Body body = world.createBody(bodyDef);
        // Now give the body a shape so the physics engine knows how to collide it
        CircleShape circle = new CircleShape();
        circle.setRadius(0.47f);
        // Attach the shape to the body as a fixture
        // Bodies can have multi fixtures, but we only need one
        body.createFixture(circle, 1.0f);
//        enemy.setSensor(false);
        // Done with the shape so free it
        circle.dispose();
        // Set the player as the user data of the body so we can look up the player from the body later
        body.setUserData(this);
        return body;
    }

    /*
    Move the object next frame
     */
    public void tick(float frameTime) {
        elapsedTime += frameTime;
        ///These things are responsible for the movement of the enemy.
        float xVelocity = (float) Math.sin(this.elapsedTime) * 2;
        float yVelocity = (float) Math.cos(this.elapsedTime) * 2;
        this.hitbox.setLinearVelocity(xVelocity, yVelocity);
    }

    public TextureRegion getCurrentAppearance() {
        if (isDestroyed) {
            /// Play the Enemy Demise animation
            TextureRegion enemyDemise = Animations.ENEMY_DEMISE.getKeyFrame(this.elapsedTime, false);
            /// Check if the animation has finished
            if (Animations.ENEMY_DEMISE.isAnimationFinished(this.elapsedTime)) {
                return null; ///return null as wall is destroyed
            }
            return enemyDemise;
        }
        return Animations.ENEMY_ANIMATION.getKeyFrame(this.elapsedTime, true);
    }

    @Override
    public float getX() {
        return hitbox.getPosition().x;
    }

    @Override
    public float getY() {
        return hitbox.getPosition().y;
    }

    public void destroy() {
        if(!isDestroyed) {
            isDestroyed = true;
            hitbox.setActive(false); /// Deactivate the wall's hitbox when it's destroyed.
            this.elapsedTime = 0; ///resets the elapsed time such that animation starts from 0th frame
        }
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Body getHitbox() {
        return hitbox;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }


}
