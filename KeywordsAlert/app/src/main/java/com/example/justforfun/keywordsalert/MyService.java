package com.example.justforfun.keywordsalert;

import java.util.*;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;


/**
 * Created by Jinghao Qiao on 2018/2/3.
 */

public class MyService extends Service {

    private Timer timer;
    private TimerTask task;
    private final TimerBinder timerBinder = new TimerBinder();
    private OnTimerServiceListener onTimerServiceListener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return timerBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    public void startTimer() {
        if (timer == null) {
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    onTimerServiceListener.getData();
                }
            };
            timer.schedule(task, 1000, 1000 * 60 * MainActivity.checkInterval);
        }
    }

    public void stopTimer() {
        if (timer != null) {
            task.cancel();
            timer.cancel();
            task = null;
            timer = null;
        }
    }

    public class TimerBinder extends Binder {

        public MyService getService() {
            return MyService.this;
        }

    }

    /*
     * @param onTimerServiceListener:OnTimerServiceListener
     */
    public void setOnTimerServiceListener(OnTimerServiceListener onTimerServiceListener) {
        this.onTimerServiceListener = onTimerServiceListener;
    }

}