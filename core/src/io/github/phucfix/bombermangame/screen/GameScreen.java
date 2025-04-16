package io.github.phucfix.bombermangame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.phucfix.bombermangame.BombermanGame;
import io.github.phucfix.bombermangame.map.*;
import io.github.phucfix.bombermangame.texture.Drawable;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen implements Screen {
    
    /**
     * The size of a grid cell in pixels.
     * This allows us to think of coordinates in terms of square grid tiles
     * (e.g. x=1, y=1 is the bottom left corner of the map)
     * rather than absolute pixel coordinates.
     */
    public static final int TILE_SIZE_PX = 16;
    
    /**
     * The scale of the game.
     * This is used to make everything in the game look bigger or smaller.
     */
    public static final float SCALE = 4f;

    // for the width and height of the game window
    public static int viewWidth, viewHeight;

    private final BombermanGame game;
    private final SpriteBatch spriteBatch;
    private final GameMap map;
    private final Hud hud;
    private final OrthographicCamera mapCamera;

    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(BombermanGame game) {
        this.game = game;
        this.spriteBatch = game.getSpriteBatch();
        this.map = game.getMap();
        this.hud = new Hud(spriteBatch, game.getSkin().getFont("font"), game);
        // Create and configure the camera for the game view
        this.mapCamera = new OrthographicCamera();
        this.mapCamera.setToOrtho(false);
        /// Initialising
        viewWidth = Gdx.graphics.getWidth();
        viewHeight = Gdx.graphics.getHeight();
    }
    
    /**
     * The render method is called every frame to render the game.
     * @param deltaTime The time in seconds since the last render.
     */
    @Override
    public void render(float deltaTime) {
        // Check for escape key press to go back to the menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.goToMenu();
            ///We need to dispose the bloody screen properly. In order to load a new map properly.
            //            dispose();
        }
        
        // Clear the previous frame from the screen, or else the picture smears
        ScreenUtils.clear(Color.BLACK);
        
        // Cap frame time to 250ms to prevent spiral of death
        float frameTime = Math.min(deltaTime, 0.250f);
        
        // Update the map state
        map.tick(frameTime);
        
        // Update the camera
        updateCamera();
        
        // Render the map on the screen
        renderMap();

        // Render the HUD on the screen
        hud.render(frameTime);
    }
    
    /**
     * Updates the camera to match the current state of the game.
     * Currently, this just centers the camera at the origin.
     */
    private void updateCamera() {
        mapCamera.setToOrtho(false);
        //mapCamera.position.x = MathUtils.clamp(map.getPlayer().getX(), 10.2f,19.5f) * TILE_SIZE_PX * SCALE;
        //mapCamera.position.y = MathUtils.clamp(map.getPlayer().getY(), 6.5f,11.5f)* TILE_SIZE_PX * SCALE;

        /// Clamp is used to make it Responsive)
//        mapCamera.position.x = MathUtils.clamp(map.getPlayer().getX()* TILE_SIZE_PX * SCALE,
//                (float) viewWidth/(2),
//                map.mapWidth * TILE_SIZE_PX * SCALE- (float)viewWidth/2) ;
//        mapCamera.position.y = MathUtils.clamp(map.getPlayer().getY()* TILE_SIZE_PX * SCALE,
//                (float) viewHeight/2,
//                map.mapHeight  * TILE_SIZE_PX * SCALE - (float)viewHeight/2);
        mapCamera.update(); // This is necessary to apply the changes
    }
    
    private void renderMap() {
        // This configures the spriteBatch to use the camera's perspective when rendering
        spriteBatch.setProjectionMatrix(mapCamera.combined);
        
        // Start drawing
        spriteBatch.begin();
        
        // Render everything in the map here, in order from lowest to highest (later things appear on top)
        // You may want to add a method to GameMap to return all the drawables in the correct order
        if (!game.isUserChoosenMap()) {
            for (Flowers flowers : map.getFlowers()) {
                if (flowers != null) {
                    draw(spriteBatch, flowers);
                }
            }

            for (Wall wall : map.getWallsOfDefaultGame()) {
                if (wall != null) {
                    draw(spriteBatch, wall);
                }
            }

            for (BreakableWall breakableWall : map.getBreakableWallsOfDefaultGame()) {
                if(breakableWall != null) {
                    draw(spriteBatch, breakableWall);
                }
            }

            draw(spriteBatch, map.getChest());
            draw(spriteBatch, map.getPlayer());
        } else {
            for (Flowers flowers : map.getFlowers()) {
                if (flowers != null) {
                    draw(spriteBatch, flowers);
                }
            }

            for (Wall wall : map.getWallsOfSelectedMap()) {
                if (wall != null) {
                    draw(spriteBatch, wall);
                }
            }

            for (BreakableWall breakableWall : map.getBreakableWallsOfSelectedMap()) {
                if(breakableWall != null) {
                    draw(spriteBatch, breakableWall);
                }
            }

            for (Chest chest : map.getChests()) {
                if (chest != null) {
                    draw(spriteBatch, chest);
                }
            }

            draw(spriteBatch, map.getPlayer());
        }
        
        // Finish drawing, i.e. send the drawn items to the graphics card
        spriteBatch.end();
    }
    
    /**
     * Draws this object on the screen.
     * The texture will be scaled by the game scale and the tile size.
     * This should only be called between spriteBatch.begin() and spriteBatch.end(), e.g. in the renderMap() method.
     * @param spriteBatch The SpriteBatch to draw with.
     */
    private static void draw(SpriteBatch spriteBatch, Drawable drawable) {
        TextureRegion texture = drawable.getCurrentAppearance();
        // Drawable coordinates are in tiles, so we need to scale them to pixels
        float x = drawable.getX() * TILE_SIZE_PX * SCALE;
        float y = drawable.getY() * TILE_SIZE_PX * SCALE;
        // Additionally scale everything by the game scale
        float width = texture.getRegionWidth() * SCALE;
        float height = texture.getRegionHeight() * SCALE;
        spriteBatch.draw(texture, x, y, width, height);
    }
    
    /**
     * Called when the window is resized.
     * This is where the camera is updated to match the new window size.
     * @param width The new window width.
     * @param height The new window height.
     */
    @Override
    public void resize(int width, int height) {
        viewWidth = width;
        viewHeight = height;
        mapCamera.setToOrtho(false);
        hud.resize(width, height);
    }

    // Unused methods from the Screen interface
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

}
