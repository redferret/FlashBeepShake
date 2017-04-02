package com.example.richard.flashbeepshake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements Shaker.Callback {
    private Shaker shaker=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shaker = new Shaker(this, 1.25d, 500, this);
    }

    @Override
    protected void onDestroy() {
        super.onRestart();
        shaker.close();
    }

    public void shakingStarted() {

    }

    public void shakingStopped() {

    }
}
