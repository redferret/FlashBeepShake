package com.example.richard.flashbeepshake;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Shaker.Callback {
    private Shaker shaker=null;
    private Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shaker = new Shaker(this, 2.25d, 75, this);
        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    protected void onDestroy() {
        super.onRestart();
        shaker.close();
    }

    public void shakingStarted() {
        TextView view = (TextView) findViewById(R.id.output);
        view.setText("SHAKING!!!");
        vibrator.vibrate(10000);
    }

    public void shakingStopped() {
        TextView view = (TextView) findViewById(R.id.output);
        view.setText("Calm");
        vibrator.cancel();
    }
}
