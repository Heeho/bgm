package ru.ltow.game;

import android.content.Context;

public abstract class SoundBase {
    protected Context context;
    protected boolean isOn;
    protected float volume;

    private static final float MAX_VOLUME = 1;

    protected SoundBase(Context c) {
        context = c;
        isOn = true;
        volume = MAX_VOLUME;
    }

    public abstract void setVolume(float v);
    public abstract void setOn(boolean state);
}