package com.example.richard.flashbeepshake;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Shaker.Callback {
    private Shaker shaker=null;
    private Vibrator vibrator;
    private ToneGenerator toneGen;
    private Camera cam;
    private boolean on = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shaker = new Shaker(this, 2.05d, 75, this);
        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100000);

    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shaker.close();
        cam.release();
    }

    public void toggleFlashlight(View v) {
        if (!on) {
            cam = Camera.open();
            Camera.Parameters p = cam.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            cam.setParameters(p);
            cam.startPreview();
            on = true;
        }else{
            cam.stopPreview();
            cam.release();
            on = false;
        }
    }

    public void startVibrate(int mils){
        vibrator.vibrate(mils);
    }

    public void haltVibrate(){
        vibrator.cancel();
    }

    public void startTone(View v){
        toneGen.startTone(ToneGenerator.TONE_CDMA_PIP,1000);
    }

    public void shakingStarted() {
        TextView view = (TextView) findViewById(R.id.output);
        view.setText("SHAKING!!!");
        startVibrate(10000);
    }

    public void shakingStopped() {
        TextView view = (TextView) findViewById(R.id.output);
        view.setText("Calm");
        haltVibrate();
    }

}
