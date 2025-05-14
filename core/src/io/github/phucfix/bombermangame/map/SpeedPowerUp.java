package io.github.phucfix.bombermangame.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

import io.github.phucfix.bombermangame.texture.Textures;

public class SpeedPowerUp extends ConcurrentBombPowerUp{

    private boolean isPowerUpTaken;


    public SpeedPowerUp(World world, float x, float y) {
        super(world, x, y);
        this.isPowerUpTaken = false;

    }

    @Override
    public TextureRegion getCurrentAppearance() {
        if(!isPowerTaken()) {
            return Textures.SPEEDPOWERUP;
        }
        else{
            return null;
        }
    }

    public boolean isPowerTaken() {
        return isPowerUpTaken;
    }

    public void setPowerTaken(boolean powerTaken) {
        this.isPowerUpTaken= powerTaken;
    }


}
