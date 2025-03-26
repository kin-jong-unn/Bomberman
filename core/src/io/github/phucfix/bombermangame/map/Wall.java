package io.github.phucfix.bombermangame.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

import io.github.phucfix.bombermangame.texture.Textures;

public class Wall extends Chest{
    /**
     * Wall has hitbox.
     * This constructor creat wall at the given position
     *
     * @param world The box2D world to add the wall's hitbox to
     * @param x     The x position
     * @param y     The y position
     */
    public Wall(World world, float x, float y) {
        super(world, x, y);
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.WALL;
    }
}
