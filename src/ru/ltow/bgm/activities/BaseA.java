package ru.ltow.game;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Toast;

public class BaseA extends Activity implements OnClickListener {
    private SoundService soundService;
    private Intent ssIntent;
    private ServiceConnection ssConnection;
    private boolean ssIsBound;

    private int bgmID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ssIsBound = false;
        bgmID = getBGMID(getClass());

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindSoundService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBGM();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindSoundService();
    }

    private void bindSoundService() {
        if(!ssIsBound) {
            ssIntent = new Intent(this, SoundService.class);
            ssConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName cn, IBinder b) {
                    soundService = ((SoundService.SBinder) b).getService();
                    soundService.bgm.setOn(App.prefs.soundIsOn(PrefsManager.BGM_ON));
                    soundService.bgm.setVolume(App.prefs.getLogSoundVolume(PrefsManager.BGM_VOLUME));
                    soundService.bgm.setBGM(bgmID);
                    ssIsBound = true;
                }

                @Override
                public void onServiceDisconnected(ComponentName cn) {
                    ssIsBound = false;
                }
            };
            bindService(ssIntent, ssConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private void setBGM() {
        if(ssIsBound) {
            soundService.bgm.setBGM(bgmID);
        }
    }

    protected void setBGMOn(boolean state) {
        if(ssIsBound) {
            soundService.bgm.setOn(state);
        } 
    }

    protected void setBGMVolume(float volume) {
        if(ssIsBound) {
            soundService.bgm.setVolume(volume);
        } 
    }

    protected void setFXOn(boolean state) {}
    protected void setFXVolume(float volume) {}

    private void unbindSoundService() {
        if(ssIsBound) {
            startService(ssIntent);
            unbindService(ssConnection);
            ssIsBound = false;
        }
    }

    private int getBGMID(Class<?> c) {
        int bgmID = BGM.NOMUSIC;

        if(c == Main.class) {bgmID = BGM.MUSIC_MAIN;}
        else if(c == Options.class) {bgmID = BGM.MUSIC_MAIN;}
        else if(c == About.class) {bgmID = BGM.NOMUSIC;}
        return bgmID;      
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "tap", 0).show();
    }
}