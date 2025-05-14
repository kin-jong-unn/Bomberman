package io.github.phucfix.bombermangame.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * This enum is used to manage the music tracks in the game.
 * Currently, only one track is used, but this could be extended to include multiple tracks.
 * Using an enum for this purpose is a good practice, as it allows for easy management of the music tracks
 * and prevents the same track from being loaded into memory multiple times.
 * See the assets/audio folder for the actual music files.
 * Feel free to add your own music tracks and use them in the game!
 */
public enum MusicTrack {

    MENU_BGM("menu-bgm.mp3",false),
    PLAYER_MOVE1("player-moving1.mp3", true),
    PLAYER_MOVE2("player-moving2.mp3", true),
    LEVEL_THEME("level-theme.mp3", true),
    LEVEL_THEME2("level-theme2.mp3", false),
    LEVEL_COMPLETED("level-complete.mp3", false),
    PLAYER_DEMISE("player-demise.mp3", false),
    BOMB_PLANT("bomb-plant-sfx.mp3",false),
    BOMB_EXPLOSION("bomb-explosion-sfx.mp3",false),
    POWERUP_TAKEN("power-up-sfx.mp3",false),
    GAME_PAUSE("game-pause-sfx.mp3",false),
    GAME_OVER("game-over.mp3",false),
    ENEMIES_CLEAR("enemies-clear.mp3",false);

    /** The music file owned by this variant. */
    private final Music music;
    private static float volume = 0.15f;

    MusicTrack(String fileName, boolean loop) {
        this.music = Gdx.audio.newMusic(Gdx.files.internal("audio/" + fileName));
        this.music.setLooping(loop);
        this.music.setVolume(0.15f);
    }
    
    /** Play this music track. */
    public void play() {
        this.music.play();
    }

    /** Stop music track */
    public void stop() {
        this.music.stop();
    }

    public static void setVolume(float newVolume) {
        volume = newVolume;
        // Update all music tracks with new volume
        for (MusicTrack track : MusicTrack.values()) {
            track.updateVolume();

        }
    }

    private void updateVolume() {
        this.music.setVolume(volume);
    }

    public static float getVolume() {
        return volume;
    }
}
