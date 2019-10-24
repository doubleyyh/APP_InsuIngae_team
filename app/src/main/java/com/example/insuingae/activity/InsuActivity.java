package com.example.insuingae.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.insuingae.Insus;
import com.example.insuingae.R;

public class InsuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insu);
        Insus insus = (Insus) getIntent().getSerializableExtra("Insus");
        Log.d("test", insus.getTitle());
    }
}
