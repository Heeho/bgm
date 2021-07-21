package ru.ltow.game;

import android.content.Context;

public class FX extends SoundBase {
    public FX(Context c) {
        super(c);
    }

    public void setOn(boolean state) {
        isOn = state;
    }

    public void setVolume(float v) {
        volume = v;
    }
}