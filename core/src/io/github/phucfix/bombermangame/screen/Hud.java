package io.github.phucfix.bombermangame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import io.github.phucfix.bombermangame.BombermanGame;
import io.github.phucfix.bombermangame.audio.MusicTrack;
import io.github.phucfix.bombermangame.map.Bomb;


/**
 * A Heads-Up Display (HUD) that displays information on the screen.
 * It uses a separate camera so that it is always fixed on the screen.
 */
public class Hud {

    /** The SpriteBatch used to draw the HUD. This is the same as the one used in the GameScreen. */
    private final SpriteBatch spriteBatch;
    /** The font used to draw text on the screen. */
    private final BitmapFont font;
    /** The camera used to render the HUD. */
    private final OrthographicCamera camera;

    private final BombermanGame game;

    private boolean enemyClearSoundPlayed;

    private static int remainingTime;
    private static boolean timerPaused;
    private float elapsedTime;
    private static final int TOTAL_TIME = 200; // Total time in seconds

    
    public Hud(SpriteBatch spriteBatch, BitmapFont font, BombermanGame game) {
        this.spriteBatch = spriteBatch;
        this.font = font;
        this.camera = new OrthographicCamera();
        this.game = game;
        this.enemyClearSoundPlayed = false;
        this.elapsedTime =0;
        timerPaused = false;
    }
    
    /**
     * Renders the HUD on the screen.
     * This uses a different OrthographicCamera so that the HUD is always fixed on the screen.
     */
    public void render(float frameTime) {
        // Render from the camera's perspective
        if (!timerPaused) {
            elapsedTime += frameTime;
        }
        int remainingTime = (int) (TOTAL_TIME - elapsedTime);

        spriteBatch.setProjectionMatrix(camera.combined);
        // Start drawing
        spriteBatch.begin();
        // Draw the HUD elements
        font.setColor(Color.WHITE);
        font.draw(spriteBatch, "Press Esc to Pause!", 10, 30);
        font.setColor(Color.GOLD);
        font.draw(spriteBatch, "Bomb Blast Radius: "+ Bomb.getCurrentBombRadius(), 10, Gdx.graphics.getHeight() - 10);

        font.draw(spriteBatch, "Max Concurrent Bombs: "+ Bomb.getMaxConcurrentBombs(),10, Gdx.graphics.getHeight() - 45);

        font.draw(spriteBatch, "Remaining Enemies: "+ game.getMap().getRemainingEnemies(),10, Gdx.graphics.getHeight() - 80);

        font.draw(spriteBatch, "Current Speed: "+ game.getMap().getPlayer().getPlayerSpeed(),10, Gdx.graphics.getHeight() - 113);

        if(game.getMap().getRemainingEnemies()==0 && !isEnemyClearSoundPlayed()){
            MusicTrack.ENEMIES_CLEAR.play();
            MusicTrack.LEVEL_THEME.stop();
            MusicTrack.LEVEL_THEME2.play();
            enemyClearSoundPlayed = true;

        }

        font.setColor(Color.GREEN);
        if(remainingTime == 70){
            MusicTrack.LEVEL_THEME.stop();
            MusicTrack.LEVEL_THEME.play();
        }

        if(remainingTime < 70 && remainingTime >20){
            font.setColor(Color.GOLD);
        } else if(remainingTime <= 20 && remainingTime > 0){
            font.setColor(Color.RED);
        } else if (remainingTime == 0) {
            game.goToLostScreen();
        }
        font.draw(spriteBatch, "Remaining Time : " + remainingTime, Gdx.graphics.getWidth()/2f - 170, Gdx.graphics.getHeight() - 10);

        if (remainingTime <= 0) {
            game.goToLostScreen();
        }

        // Finish drawing
        spriteBatch.end();
    }
    
    /**
     * Resizes the HUD when the screen size changes.
     * This is called when the window is resized.
     * @param width The new width of the screen.
     * @param height The new height of the screen.
     */
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    public boolean isEnemyClearSoundPlayed() {
        return enemyClearSoundPlayed;
    }

    public void setEnemyClearSoundPlayed(boolean enemyClearSoundPlayed) {
        this.enemyClearSoundPlayed = enemyClearSoundPlayed;
    }

    public static int getRemainingTime() {
        return remainingTime;
    }

    public static void setRemainingTime(int remainingTime) {
        Hud.remainingTime = remainingTime;
    }

    public static boolean isTimerPaused() {
        return timerPaused;
    }

    public static void setTimerPaused(boolean timerpaused) {
        Hud.timerPaused = timerpaused;
    }
}
