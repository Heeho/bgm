package ru.ltow.game;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Handler;
import android.os.Looper;
import android.app.Service;

public class SoundService extends Service {
    public BGM bgm;

    private static final long DESTRUCTION_CD = 100;

    @Override
    public void onCreate() {
        bgm = new BGM(this);
    }

    public class SBinder extends Binder {
        public SoundService getService() {
            return SoundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent i) {
        return new SBinder();
    }

    @Override
    public int onStartCommand(Intent i, int flags, int startId) {
        Handler h = new Handler(Looper.getMainLooper());
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                SoundService.this.stopSelf();
            }
        }, DESTRUCTION_CD);
        return super.onStartCommand(i, flags, startId);
    }

    @Override
    public void onDestroy() {
        bgm.releaseMP();
    }
}