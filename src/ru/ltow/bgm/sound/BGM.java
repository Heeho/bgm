package ru.ltow.game;

import android.media.MediaPlayer;
import java.util.HashMap;
import android.content.Context;

public class BGM extends SoundBase {
    private MediaPlayer mp;
    private int currentBGM;
    private static HashMap<Integer,Integer> playback = new HashMap<Integer,Integer>();

    public static final int NOMUSIC = -1;
    public static final int MUSIC_MAIN = R.raw.hypnotic;

    public BGM(Context c) {
        super(c);
        currentBGM = NOMUSIC;
    }

/*
off       | nothing
no  =  no | return
some=some | nomp ? create : return
no   some | release
some   no | create
some!=some| release create*/

    public void setBGM(int newBGM) {
        if(!isOn) {
            releaseMP();
        } else if((newBGM == NOMUSIC) && (currentBGM == NOMUSIC)) {
            return;
        } else if(newBGM == currentBGM) {
            startNewMP(newBGM);
            return;
        } else if((newBGM != NOMUSIC) && (currentBGM == NOMUSIC)) {
            startNewMP(newBGM);
        } else if((newBGM == NOMUSIC) && (currentBGM != NOMUSIC)) {
            releaseMP();
        } else if((newBGM != NOMUSIC) && (currentBGM != NOMUSIC) && (newBGM != currentBGM)) {
            releaseMP();
            startNewMP(newBGM);
        }
        currentBGM = newBGM;
    }

    private void startNewMP(int newBGM) {
        if(mp == null) {
            mp = MediaPlayer.create(context, newBGM);
            if(playback.containsKey(newBGM)) {
                mp.seekTo(playback.get(newBGM));
            }
            mp.setLooping(true);
            mp.setVolume(volume, volume);
            mp.start();
        }
    }

    public void releaseMP() {
        if(mp != null) {
            playback.put(currentBGM, mp.getCurrentPosition());
            mp.release();
            mp = null;
        }
    }

    public void setOn(boolean state) {
        isOn = state;
        setBGM(currentBGM);
    }

    public void setVolume(float v) {
        volume = v;
        if(mp != null) mp.setVolume(v, v);
    }
}