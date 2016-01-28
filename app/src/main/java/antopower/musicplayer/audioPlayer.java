package antopower.musicplayer;

import android.media.MediaPlayer;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Antopower on 2016-01-27.
 */
public class audioPlayer {

    private HashMap<String, String> currentSong;
    MediaPlayer mp = new MediaPlayer();

    public audioPlayer() {
        // Required empty public constructor
    }

    /**
     * @param songMetadata
     * @return Boolean
     */
    public boolean play(HashMap<String, String> songMetadata) {
        try {
            mp.reset();
            mp.setDataSource(songMetadata.get("songPath"));
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            return false;
        }
        this.currentSong = songMetadata;
        return true;
    }

    public boolean pause() {
        try {
            mp.pause();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void previous() {

    }

    public void next() {

    }

    public boolean close() {
        try {
            mp.reset();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void getPosition() {

    }

    public void getDuration() {

    }

    public void isPlaying() {

    }

    public void getMetaData() {

    }

}
