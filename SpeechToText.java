package com.example.textaudioconverter;

import static com.example.textaudioconverter.R.drawable.baseline_mic_24;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class SpeechToText extends AppCompatActivity {
    
    ImageButton imageButton;
    EditText editText;
    SpeechRecognizer speechRecognizer;
    int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);
        
        imageButton=findViewById(R.id.button_micof);
        editText=findViewById(R.id.editText);
        
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED)
        {
             ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.RECORD_AUDIO},1);
        }

        speechRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
        final Intent spintent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if(count==0){
                    imageButton.setImageDrawable(getDrawable(baseline_mic_24));
                    speechRecognizer.startListening(spintent);
                    count=1;
                }
                else{
                    imageButton.setImageDrawable(getDrawable(R.drawable.baseline_mic_off_24));
                    speechRecognizer.stopListening();
                    count=0;
                }
            }
        });
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {

                ArrayList<String> data = bundle.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);
                editText.setText(data.get(0));

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT);
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT);
            }
        }
    }

}