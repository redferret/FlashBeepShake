package com.example.richard.flashbeepshake;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Shaker.Callback {
    private Shaker shaker=null;
    private Vibrator vibrator;
    private ToneGenerator toneGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shaker = new Shaker(this, 2.05d, 75, this);
        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shaker.close();
    }

    public void shakingStarted() {
        TextView view = (TextView) findViewById(R.id.output);
        view.setText("SHAKING!!!");
        vibrator.vibrate(10000);
        toneGen.startTone(ToneGenerator.TONE_CDMA_PIP,100);

    }

    public void shakingStopped() {
        TextView view = (TextView) findViewById(R.id.output);
        view.setText("Calm");
        vibrator.cancel();
        toneGen.stopTone();

    }

    private boolean isFlashSupported() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
}
