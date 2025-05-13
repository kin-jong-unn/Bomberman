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

    MENU_BGM("menu-bgm.mp3", 0.15f,true),
    PLAYER_MOVE1("player-moving1.mp3", 0.15f,true),
    PLAYER_MOVE2("player-moving2.mp3", 0.15f,true),
    LEVEL_THEME("level-theme.mp3", 0.15f,true),
    PLAYER_DEMISE("player-demise.mp3", 0.15f,false),
    BOMB_PLANT("bomb-plant-sfx.mp3",0.15f,false),
    BOMB_EXPLOSION("bomb-explosion-sfx.mp3",0.15f,false),
    POWERUP_TAKEN("con-bomb-power-up.mp3",0.15f,false);

    /** The music file owned by this variant. */
    private final Music music;
    
    MusicTrack(String fileName, float volume, boolean loop) {
        this.music = Gdx.audio.newMusic(Gdx.files.internal("audio/" + fileName));
        this.music.setLooping(loop);
        this.music.setVolume(volume);
    }
    
    /** Play this music track. */
    public void play() {
        this.music.play();
    }

    /** Stop music track */
    public void stop() {
        this.music.stop();
    }

}
