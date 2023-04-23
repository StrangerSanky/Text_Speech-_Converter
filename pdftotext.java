package com.example.textaudioconverter;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.IOException;
import java.util.Locale;

public class pdftotext extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private TextView outputTextView;
    private Button button;
    private static final int READ_REQUEST_CODE = 42;
    private static final String PRIMARY = "primary";
    private static final String LOCAL_STORAGE = "/storage/self/primary/";
    private static final String EXT_STORAGE = "/storage/7764-A034/";
    private static final String COLON = ":";

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdftotext);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button button = findViewById(R.id.button_pdfimp);
        outputTextView = findViewById(R.id.pdfview);
        outputTextView.setMovementMethod(new ScrollingMovementMethod());

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("*/*");
                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });

    }

//    private void setSupportActionBar(Toolbar toolbar) {
//    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                  Toast.makeText(this, uri.getPath(), Toast.LENGTH_SHORT).show();
                Log.v("URI", uri.getPath());
                readPdfFile(uri);
            }
        }
    }
//
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void readPdfFile(Uri uri) {
        String fullPath;
        //convert from uri to full path
        if(uri.getPath().contains(PRIMARY)) {
            fullPath = LOCAL_STORAGE + uri.getPath().split(COLON)[1];
        }
        else {
            fullPath = EXT_STORAGE + uri.getPath().split(COLON)[1];
        }
        Log.v("URI", uri.getPath()+" "+fullPath);
        String stringParser;
        try {
            PdfReader pdfReader = new PdfReader(fullPath);
            stringParser = PdfTextExtractor.getTextFromPage(pdfReader, 1).trim();
            pdfReader.close();
            outputTextView.setText(stringParser);
            textToSpeech.speak(stringParser, TextToSpeech.QUEUE_FLUSH,null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}