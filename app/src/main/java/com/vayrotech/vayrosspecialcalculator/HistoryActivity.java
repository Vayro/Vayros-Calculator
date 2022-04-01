package com.vayrotech.vayrosspecialcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HistoryActivity extends AppCompatActivity {
    private String receivedLog="";
    private TextView log;
    private String FILE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        log = findViewById(R.id.textViewLog);
        receivedLog = getIntent().getExtras().getString("log");
        FILE_NAME = getIntent().getExtras().getString("filename");
        log.setText(receivedLog);
        log.setMovementMethod(new ScrollingMovementMethod());
    }


    public void clearLog(View view)
    {
        String logging = " ";
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(logging.getBytes());
           receivedLog=("");
            log.setText("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }








    public void backPressed(View view) {
        this.finish();

    }
}