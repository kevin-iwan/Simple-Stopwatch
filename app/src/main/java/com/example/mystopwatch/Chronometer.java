package com.example.mystopwatch;

import android.content.Context;

public class Chronometer implements Runnable {

    public static final long MILLISECONDS_TO_MINUTES = 60000;
    public static final long MILLISECONDS_TO_HOURS = 3600000;


    private Context context;
    private long startTime;
    private boolean isRunning;

    public Chronometer(Context mContext) {
        this.context = mContext;
    }

    public void start(){
        startTime = System.currentTimeMillis();
        isRunning = true;
    }

    public void stop(){
        isRunning = false;
    }


    @Override
    public void run() {
        while(isRunning){
            long since = System.currentTimeMillis() - startTime;

            int seconds = (int) ((since / 1000) % 60);
            int minutes = (int) ((since / MILLISECONDS_TO_MINUTES) % 60);
            int hours = (int) ((since / MILLISECONDS_TO_HOURS) % 24);
            int milliSeconds = (int) since % 1000;

            ((MainActivity)context).updateTimerText(String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, milliSeconds));
        }
    }
}
