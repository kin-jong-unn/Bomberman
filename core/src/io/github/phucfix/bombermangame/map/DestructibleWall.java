package io.github.phucfix.bombermangame.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import io.github.phucfix.bombermangame.texture.Textures;

public class DestructibleWall extends IndestructibleWall {
    public DestructibleWall(World world, float x, float y) {
        super(world, x, y);
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.DESTRUCTIBLEWALL;
    }
}
