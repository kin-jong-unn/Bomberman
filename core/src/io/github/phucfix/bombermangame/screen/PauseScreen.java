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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.phucfix.bombermangame.BombermanGame;
import io.github.phucfix.bombermangame.audio.MusicTrack;
import io.github.phucfix.bombermangame.map.Bomb;


public class PauseScreen implements Screen {
    private final BombermanGame game;
    private final Stage stage;

    public PauseScreen(BombermanGame game) {
        this.game = game;
        var camera = new OrthographicCamera();
        camera.zoom = 1.4f; // Set camera zoom for closer view
        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch()); // create stage for UI elements

        Table table = new Table(); // Table layout
        table.setFillParent(true);
        stage.addActor(table); // add table to the stage

        // Label and title
        table.add(new Label("Bomberman", game.getSkin(), "title")).padBottom(80).row();
        table.add(new Label("Game is paused", game.getSkin(), "title")).padBottom(80).row();

        TextButton resumeButton = new TextButton("Resume", game.getSkin());
        table.add(resumeButton).width(200).row();
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Hud.setTimerPaused(false);
                ///Clicking on This button does not work because we are already in the gameScreen?But pressing enter does work.
                game.goToGame();
                MusicTrack.GAME_PAUSE.play();
            }
        });

        TextButton goToMenu = new TextButton("Go to Main Menu", game.getSkin());
        table.add(goToMenu).width(350).row();
        goToMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.resetHud();
                MusicTrack.GAME_OVER.stop();
                Bomb.setActiveBombs(0);
                Bomb.setMaxConcurrentBombs(1);
                Bomb.setCurrentBombRadius(1);
                game.goToMenu();
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            Hud.setTimerPaused(false);
            game.goToGame();
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

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }


}
