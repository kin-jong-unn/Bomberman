package io.github.phucfix.bombermangame.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.phucfix.bombermangame.texture.Drawable;
import io.github.phucfix.bombermangame.texture.Animations;


public class ExplosionSegment implements Drawable {

    private final int x;
    private final int y;
    private float elapsedTime;
    private final int directionX;
    /// Direction in X (1 for right, -1 for left, 0 for no movement)
    private final int directionY;
    /// Direction in Y (1 for up, -1 for down, 0 for no movement)
    private final boolean isEnd;

    /// Whether this segment is at the end

    public ExplosionSegment(int x, int y, int directionX, int directionY, boolean isEnd) {
        this.x = x;
        this.y = y;
        this.elapsedTime = 0;
        this.directionX = directionX;
        this.directionY = directionY;
        this.isEnd = isEnd;
    }


    public void tick(float frameTime) {
        this.elapsedTime += frameTime;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        // Determine the appropriate animation based on the segment type
        TextureRegion explosionAnimation = null;

        // If this is the end of the explosion chain, use the corresponding end animation
        if (isEnd) {
            if (directionY == 1) {
                explosionAnimation = Animations.EXPLOSION_TOP_END.getKeyFrame(elapsedTime, false);
            } else if (directionY == -1) {
                explosionAnimation = Animations.EXPLOSION_BOTTOM_END.getKeyFrame(elapsedTime, false);
            } else if (directionX == 1) {
                explosionAnimation = Animations.EXPLOSION_RIGHT_END.getKeyFrame(elapsedTime, false);
            } else if (directionX == -1) {
                explosionAnimation = Animations.EXPLOSION_LEFT_END.getKeyFrame(elapsedTime, false);
            }
        }
        else {
            // Regular explosion segment (not at the end of the chain)
            if (directionY == 1 || directionY == -1) {
                explosionAnimation = Animations.EXPLOSION_VERTICAL.getKeyFrame(elapsedTime, false);
            } else if (directionX == 1 || directionX == -1) {
                explosionAnimation = Animations.EXPLOSION_HORIZONTAL.getKeyFrame(elapsedTime, false);
            }
        }

        // If the animation has finished, return null (indicating the segment is gone)
        if(explosionAnimation !=null && Animations.EXPLOSION_CENTER.isAnimationFinished(elapsedTime)){
            return null;
        }
        return explosionAnimation;
    }


    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void destroy() {
    }
}
