package com.example.mystopwatch;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView tv_time;
    private EditText et_laps;
    private Button btn_start;
    private Button btn_lap;
    private Button btn_stop;
    private Button btn_reset;
    private ScrollView sv_laps;

    private int lapsCounter = 1;

    private Context context;
    private com.example.mystopwatch.Chronometer chronometer;
    private Thread threadChrono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        tv_time = (TextView) findViewById(R.id.tv_time);
        et_laps = (EditText) findViewById(R.id.et_laps);
        sv_laps = (ScrollView) findViewById(R.id.sv_laps);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_lap = (Button) findViewById(R.id.btn_lap);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_reset = (Button) findViewById(R.id.btn_reset);

        et_laps.setEnabled(false);


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chronometer == null){
                    chronometer = new Chronometer(context);
                    threadChrono = new Thread(chronometer);
                    threadChrono.start();
                    chronometer.start();
                    lapsCounter = 1;
                    et_laps.setText("");
                }
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chronometer != null) {
                    chronometer.stop();
                    threadChrono.interrupt();
                    threadChrono = null;
                    chronometer = null;
                }
            }
        });

        btn_lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chronometer == null){
                    return;
                }
                et_laps.append("LAP " + String.valueOf(lapsCounter) + " " + String.valueOf(tv_time.getText()) + "\n");
                lapsCounter++;
                sv_laps.post(new Runnable() {
                    @Override
                    public void run() {
                        sv_laps.smoothScrollTo(0, et_laps.getBottom());
                    }
                });
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chronometer == null){
                    tv_time.setText("00:00:00:000");
                    et_laps.setText("");
                }
            }
        });
    }

    public void updateTimerText(final String time){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_time.setText(time);
            }
        });
    }

}
