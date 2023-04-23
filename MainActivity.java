package com.example.textaudioconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton imageButton3;
        imageButton3=findViewById(R.id.imageButton3);

        Intent iNext;
        iNext = new Intent(MainActivity.this, TextSpeech.class);

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(iNext);
            }
        });


        ImageButton imageButton;
        imageButton=findViewById(R.id.imageButton);

        Intent iNext1;
        iNext1 = new Intent(MainActivity.this, SpeechToText.class);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(iNext1);
            }
        });

        ImageButton imageButton2;
        imageButton2=findViewById(R.id.imageButton2);

        Intent iNext2;
        iNext2 = new Intent(MainActivity.this, pdftotext.class);

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(iNext2);
            }
        });
    }
}