package ru.ltow.game;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

public class Options extends BaseA implements OnSharedPreferenceChangeListener {
    private SeekBar fxVolume, bgmVolume;
    private SeekBarListener sbl;

    private CheckBox fxOn, bgmOn;
    private CheckBoxListener cbl;

    private SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        sp = App.prefs.getPrefs();

        sbl = new SeekBarListener();
        cbl = new CheckBoxListener();

        fxOn = findViewById(R.id.fxCB);
        fxOn.setChecked(App.prefs.soundIsOn(PrefsManager.FX_ON));
        fxOn.setOnCheckedChangeListener(cbl);

        fxVolume = findViewById(R.id.fxSB);
        fxVolume.setMax((int) App.prefs.getMaxVolume());
        fxVolume.setProgress((int) App.prefs.getSoundVolume(PrefsManager.FX_VOLUME));
        fxVolume.setEnabled(App.prefs.soundIsOn(PrefsManager.FX_ON));
        fxVolume.setOnSeekBarChangeListener(sbl);

        bgmOn = findViewById(R.id.bgmCB);
        bgmOn.setChecked(App.prefs.soundIsOn(PrefsManager.BGM_ON));
        bgmOn.setOnCheckedChangeListener(cbl);

        bgmVolume = findViewById(R.id.bgmSB);
        bgmVolume.setMax((int) App.prefs.getMaxVolume());
        bgmVolume.setProgress((int) App.prefs.getSoundVolume(PrefsManager.BGM_VOLUME));
        bgmVolume.setEnabled(App.prefs.soundIsOn(PrefsManager.BGM_ON));
        bgmVolume.setOnSeekBarChangeListener(sbl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sp.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sp.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String pref) {
        switch(pref) {
            case PrefsManager.FX_ON: fxVolume.setEnabled(fxOn.isChecked()); break;
            case PrefsManager.BGM_ON: bgmVolume.setEnabled(bgmOn.isChecked()); break;
        }
    }

    private class SeekBarListener implements OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
            switch(sb.getId()) {
                case R.id.fxSB:
                    App.prefs.setSoundVolume(PrefsManager.FX_VOLUME, (float) progress);
                    setFXVolume(App.prefs.getLogSoundVolume(PrefsManager.FX_VOLUME));
                    break;
                case R.id.bgmSB:
                    App.prefs.setSoundVolume(PrefsManager.BGM_VOLUME, (float) progress);
                    setBGMVolume(App.prefs.getLogSoundVolume(PrefsManager.BGM_VOLUME));
                    break;
            }
        }
        @Override public void onStopTrackingTouch(SeekBar sb) {}
        @Override public void onStartTrackingTouch(SeekBar sb) {}
    }

    private class CheckBoxListener implements OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
            switch(cb.getId()) {
                case(R.id.fxCB):
                    App.prefs.setSoundOn(PrefsManager.FX_ON, isChecked);
                    setFXOn(isChecked);
                    break;
                case(R.id.bgmCB):
                    App.prefs.setSoundOn(PrefsManager.BGM_ON, isChecked);
                    setBGMOn(isChecked);
                    break;
            }
        }
    }
}