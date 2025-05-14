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
import io.github.phucfix.bombermangame.audio.MusicTrack;
import io.github.phucfix.bombermangame.map.Bomb;

public class VictoryScreen implements Screen {

    private final BombermanGame game;

    private final Stage stage;


    public VictoryScreen(BombermanGame game) {
        this.game = game;
        var camera = new OrthographicCamera();
        camera.zoom = 1.4f; // Set camera zoom for a closer view
        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements

        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        // Add a label as a title
        table.add(new Label("You WON :)", game.getSkin(), "title")).padBottom(80).row();
        table.add(new Label("You are a GigaChad", game.getSkin())).padBottom(40).row();


        TextButton resumeButton = new TextButton("Play Again?", game.getSkin());
        table.add(resumeButton).width(250).row();
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameScreen.setGameWon(false);
                MusicTrack.GAME_OVER.stop();
                MusicTrack.LEVEL_THEME.play();
                Bomb.setMaxConcurrentBombs(1);
                Bomb.setCurrentBombRadius(1);
                game.loadDefaultMap();

            }
        });

        TextButton goToMenu = new TextButton("Go to Main Menu", game.getSkin());
        table.add(goToMenu).width(350).row();
        goToMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MusicTrack.GAME_OVER.stop();
                game.goToMenu();
            }
        });


    }

    @Override
    public void show() {
        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            MusicTrack.LEVEL_COMPLETED.stop();
            GameScreen.setGameWon(false);
            MusicTrack.LEVEL_THEME.play();
            Bomb.setMaxConcurrentBombs(1);
            Bomb.setCurrentBombRadius(1);
            game.loadDefaultMap();
        }
        float frameTime = Math.min(deltaTime, 0.250f); // Cap frame time to 250ms to prevent spiral of death        ScreenUtils.clear(Color.BLACK);
        ScreenUtils.clear(Color.BLACK);
        stage.act(frameTime); // Update the stage
        stage.draw(); // Draw the stage
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }


}
