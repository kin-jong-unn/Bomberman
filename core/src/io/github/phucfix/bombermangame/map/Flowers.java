package io.github.phucfix.bombermangame.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.phucfix.bombermangame.texture.Animations;
import io.github.phucfix.bombermangame.texture.Drawable;
import io.github.phucfix.bombermangame.texture.Textures;

/**
 * Flowers are a static object without any special properties.
 * They do not have a hitbox, so the player does not collide with them.
 * They are purely decorative and serve as a nice floor decoration.
 */
public class Flowers implements Drawable {
    
    private final int x;
    private final int y;
    private boolean isDestroyed;
    private float elapsedTime;
    
    public Flowers(int x, int y) {
        this.x = x;
        this.y = y;
        this.isDestroyed = false;
        this.elapsedTime = 0;
    }
    
    @Override
    public TextureRegion getCurrentAppearance() {
        if (isDestroyed) {
            /// Play the destruction animation
            TextureRegion destroyFlower = Animations.FLOWER_DESTROY.getKeyFrame(this.elapsedTime, false);

            /// Check if the animation has finished
            if (Animations.FLOWER_DESTROY.isAnimationFinished(this.elapsedTime)) {
                isDestroyed = false;
                return Textures.FLOWERS; ///return null as wall is destroyed
            }
            return destroyFlower;
        }

        return Textures.FLOWERS;
    }
    
    @Override
    public float getX() {
        return x;
    }
    
    @Override
    public float getY() {
        return y;
    }

    public void destroy() {
        isDestroyed = true;
        elapsedTime = 0; /// Reset elapsed time to start animation from the beginning (0th frame)
    }

    public void tick(float frameTime) {
        if (isDestroyed && !Animations.FLOWER_DESTROY.isAnimationFinished(this.elapsedTime)) {
            this.elapsedTime += frameTime;
        }
    }
}
