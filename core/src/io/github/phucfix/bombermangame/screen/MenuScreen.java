package io.github.phucfix.bombermangame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.phucfix.bombermangame.BombermanGame;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import io.github.phucfix.bombermangame.audio.MusicTrack;
import io.github.phucfix.bombermangame.map.Bomb;

/**
 * The MenuScreen class is responsible for displaying the main menu of the game.
 * It extends the LibGDX Screen class and sets up the UI components for the menu.
 */
public class MenuScreen implements Screen {

    private final Stage stage;

    /// Created the game as a attribute so that it can be used in render().
    /// To enable "ENTER to start game".
    private final BombermanGame game;

    /**
     * Constructor for MenuScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public MenuScreen(BombermanGame game) {
        this.game = game;
        GameScreen.setGameWon(false);

        var camera = new OrthographicCamera();
        camera.zoom = 1.4f; // Set camera zoom for a closer view

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements

        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        // Add a label as a title
        table.add(new Label("Bomberman Game", game.getSkin(), "title")).padBottom(80).row();

        // Create and add a button to go to the game screen
        TextButton goToGameButton = new TextButton("Start", game.getSkin());
        table.add(goToGameButton).width(220).row();
        goToGameButton.addListener(new ChangeListener() {
                                       @Override
                                       public void changed(ChangeEvent event, Actor actor) {
                                           game.resetHud();
                                           Bomb.setActiveBombs(0);
                                           Bomb.setMaxConcurrentBombs(1);
                                           Bomb.setCurrentBombRadius(1);
                                           MusicTrack.LEVEL_THEME.play();
                                           game.loadDefaultMap();
                                       }
                                   });

        TextButton loadAChallenge = new TextButton("Challenge", game.getSkin());
        table.add(loadAChallenge).width(300).row();
        loadAChallenge.addListener(new ChangeListener() {
                                       public void changed(ChangeEvent changeEvent, Actor actor) {
                                           game.resetHud();
                                           Bomb.setActiveBombs(0);
                                           Bomb.setMaxConcurrentBombs(1);
                                           Bomb.setCurrentBombRadius(1);
                                           MusicTrack.LEVEL_THEME.play();
                                           game.loadChallenge();

                                       }
                                   });

        /** (Aryan)
         * To choose a Map, the user needs a button, that button needs to open the filechooser window.
         */
        TextButton goToFileChooserButton = new TextButton("Choose your Map", game.getSkin());
        table.add(goToFileChooserButton).width(350).row();
        goToFileChooserButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.resetHud();
                ///This method will open the filechooser window
                game.loadFileChooser();
            }
        });

        // Create and add a button to view tutorial
        TextButton goToTurorial = new TextButton("Tutorial", game.getSkin());
        table.add(goToTurorial).width(350).row();
        goToTurorial.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToTutorial();
            }
        });

        Label tipLabel = new Label("Press ENTER for Quick Start", game.getSkin());
        table.add(tipLabel).padTop(40).row();
        Slider volumeSlider = new Slider(0,1,0.1f,false,game.getSkin());

        volumeSlider.setAnimateDuration(0.6f);
        volumeSlider.setVisualPercent(MusicTrack.getVolume());
        volumeSlider.addListener(new ChangeListener() {
                                     public void changed(ChangeEvent event, Actor actor) {
                                         float value = volumeSlider.getValue();
                                         MusicTrack.setVolume(value);
                                     }
        });
        // Add label for volume
        Table volumeTable = new Table();
        Label volumeLabel = new Label("Volume", game.getSkin());
        volumeLabel.setFontScale(1.1f);
        volumeTable.add(volumeLabel).padRight(40);
        volumeTable.add(volumeSlider).width(300);
        table.add(volumeTable).padTop(20).row();


    }
    
    /**
     * The render method is called every frame to render the menu screen.
     * It clears the screen and draws the stage.
     * @param deltaTime The time in seconds since the last render.
     */
    @Override
    public void render(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.resetHud();
            Bomb.setActiveBombs(0);
            Bomb.setMaxConcurrentBombs(1);
            Bomb.setCurrentBombRadius(1);
            MusicTrack.LEVEL_THEME.play();
            game.loadDefaultMap();
        }
        float frameTime = Math.min(deltaTime, 0.250f); // Cap frame time to 250ms to prevent spiral of death        ScreenUtils.clear(Color.BLACK);
        ScreenUtils.clear(Color.BLACK);
        stage.act(frameTime); // Update the stage
        stage.draw(); // Draw the stage
    }
    
    /**
     * Resize the stage when the screen is resized.
     * @param width The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
    }

    @Override
    public void dispose() {
        // Dispose of the stage when screen is disposed
        stage.dispose();
    }

    @Override
    public void show() {
        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);
    }

    // The following methods are part of the Screen interface but are not used in this screen.
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
