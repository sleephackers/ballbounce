package com.example.android.ballbounce;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
    ImageView refresh, line;
    AnimatedView animatedView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animatedView = findViewById(R.id.anim_view);

    }

}