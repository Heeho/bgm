package ru.ltow.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;

public class PrefsManager {
    private SharedPreferences sp;
    private Editor e;

    private float MAX_VOLUME;

    public static final String SP_NAME = "preferences";
    public static final String FX_VOLUME = "fxVolume";
    public static final String FX_ON = "fxOn";
    public static final String BGM_VOLUME = "bgmVolume";
    public static final String BGM_ON = "bgmOn";

    public static final String P1_HUMAN = "p1human";
    public static final String P2_HUMAN = "p2human";
    public static final String P3_HUMAN = "p3human";

    public PrefsManager(Context c) {
        sp = c.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        e = sp.edit();

        AudioManager am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
        MAX_VOLUME = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    public void setPlayerHuman(String s, boolean yn) {
        e.putBoolean(s, yn);
        e.apply();
    }

    public void setSoundVolume(String s, float volume) {
        e.putFloat(s, volume);
        e.apply();
    }
    public void setSoundOn(String s, boolean state) {
        e.putBoolean(s, state);
        e.apply();
    }

    public SharedPreferences getPrefs() {
        return sp;
    }
    public float getMaxVolume() {
        return MAX_VOLUME;
    }
    public float getSoundVolume(String s) {
        return sp.getFloat(s, MAX_VOLUME);
    }
    public boolean soundIsOn(String s) {
        return sp.getBoolean(s, true);
    }
    public boolean playerIsHuman(String s) {
        return sp.getBoolean(s, false);
    }
    public float getLogSoundVolume(String s) {
        float max = getMaxVolume();
        float current = sp.getFloat(s, MAX_VOLUME);
        return (float) (1 - (Math.log(max - current)/Math.log(max)));
    }
}