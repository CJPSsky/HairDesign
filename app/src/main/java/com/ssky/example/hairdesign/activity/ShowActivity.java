package com.ssky.example.hairdesign.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ssky.example.hairdesign.R;

public class ShowActivity extends AppCompatActivity { private static final String TAG = "ShowActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout);
    }

}
