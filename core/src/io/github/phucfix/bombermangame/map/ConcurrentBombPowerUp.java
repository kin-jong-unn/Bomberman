package io.github.phucfix.bombermangame.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import io.github.phucfix.bombermangame.texture.Drawable;
import io.github.phucfix.bombermangame.texture.Textures;

public class ConcurrentBombPowerUp extends Exit {
    private boolean powerTaken;


    public ConcurrentBombPowerUp(World world, float x, float y){
        super(world,x,y);
        this.powerTaken = false;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        if(!isPowerTaken()) {
            return Textures.CB_POWERUP;
        }
        else{
            return null;
        }
    }

    public boolean isPowerTaken() {
        return powerTaken;
    }

    public void setPowerTaken(boolean powerTaken) {
        this.powerTaken = powerTaken;
    }
}
