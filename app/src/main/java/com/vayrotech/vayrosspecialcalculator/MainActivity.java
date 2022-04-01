package com.vayrotech.vayrosspecialcalculator;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.*;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {


    private EditText entry;
    private TextView expressed;
    private TextView lastEntry;
    private static final String FILE_NAME = "historyLog.txt";
    private String historyString="";


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expressed = findViewById(R.id.textViewExpressing);
        entry = findViewById(R.id.textViewEntry);
        lastEntry = findViewById(R.id.textViewHistory);
        entry.setShowSoftInputOnFocus(false);
        entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getString(R.string.display).equals(entry.getText().toString())) {
                    entry.setText("");
                }
                }

        });


        historyString = loadLog();

    }


    private void textUpdate(String addString) {
        String stringOld = entry.getText().toString();
        int cursorPosition=entry.getSelectionStart();
        String stringLeft = stringOld.substring(0,cursorPosition);
        String stringRight = stringOld.substring(cursorPosition);
        if (getString(R.string.display).equals(entry.getText().toString()))
        {
            entry.setText(addString);
            entry.setSelection(cursorPosition + 1);
        }
        else {
            entry.setText(String.format("%s%s%s", stringLeft, addString, stringRight));
            entry.setSelection(cursorPosition + 1);
        }




    }



    private void expressedUpdate(String operation)
    {


        String stringOld = expressed.getText().toString();

        //check if last entry was not empty
        if(!entry.getText().toString().equals(""))
        {

            String stringAdd = entry.getText().toString();
            String operationString = operation;
            expressed.setText(String.format("%s%s%s", stringOld, stringAdd, operationString));
            entry.setText("");

        }
        //if empty, check if we should change the expression
        else
        {
            if(stringOld.endsWith("x") || stringOld.endsWith("-") || stringOld.endsWith("+") || stringOld.endsWith("÷"))
            {
                String newString= stringOld.substring(0,stringOld.length()-1) + operation;
                expressed.setText(newString);

            }



        }

    }


    private void equivocate()
    {
        String stringOld = expressed.getText().toString();

        if(entry.getText().toString().equals("")) {

            if (stringOld.endsWith("x") || stringOld.endsWith("-") || stringOld.endsWith("+") || stringOld.endsWith("÷")) {
                String newString = stringOld.substring(0, stringOld.length() - 1);
                expressed.setText(newString);
            }

        }

        stringOld = expressed.getText().toString();
        String stringAdd = entry.getText().toString();
        expressed.setText(String.format("%s%s", stringOld, stringAdd));
        entry.setText("");



        String expression = expressed.getText().toString();
        expression = expression.replaceAll("÷","/");
        expression = expression.replaceAll("x","*");

        Expression exp = new Expression(expression);

        String result = String.valueOf((exp.calculate()));

        entry.setText(result);
        entry.setSelection(result.length());

        lastEntry.setText(expressed.getText().toString()+"="+result);
       expressed.setText("");
        saveHistory();


    }









    public void btnZero(View view) {
        textUpdate("0");

    }

    public void btnOne(View view) {
        textUpdate("1");

    }

    public void btnTwo(View view) {
        textUpdate("2");

    }

    public void btnThree(View view) {
        textUpdate("3");

    }

    public void btnFour(View view) {

        textUpdate("4");
    }

    public void btnFive(View view) {

        textUpdate("5");
    }

    public void btnSix(View view) {

        textUpdate("6");
    }

    public void btnSeven(View view) {
        textUpdate("7");

    }

    public void btnEight(View view) {

        textUpdate("8");
    }

    public void btnNine(View view) {

        textUpdate("9");
    }

    public void btnSignum(View view) {
       if(!entry.getText().toString().startsWith("(-") && !entry.getText().toString().endsWith(")")) {
           entry.setSelection(0);
           textUpdate("(-");
           entry.setSelection(entry.getText().length());
           textUpdate(")");
           entry.setSelection(entry.getText().length()-1);        }
       else
           {
               entry.setText(entry.getText().toString().replace("-", ""));
               entry.setText(entry.getText().toString().replace("(", ""));
               entry.setText(entry.getText().toString().replace(")", ""));
               entry.setSelection(entry.getText().length());

           }

    }

    public void btnDecimal(View view) {
        textUpdate(".");

    }

    public void btnEquals(View view) {

        equivocate();
    }

    public void btnPlus(View view) {

        expressedUpdate("+");



    }

    public void btnMinus(View view) {
        expressedUpdate("-");

    }

    public void btnMultiply(View view) {

        expressedUpdate("x");
    }

    public void btnDivide(View view) {
        expressedUpdate("÷");

    }

    public void btnBackspace(View view) {
        int cursorPosition = entry.getSelectionStart();
        int textLength = entry.getText().length();
        if(cursorPosition != 0 && textLength != 0){
            SpannableStringBuilder selection = (SpannableStringBuilder) entry.getText();
            selection.replace(cursorPosition-1,cursorPosition, "");
            entry.setText(selection);
            entry.setSelection(cursorPosition-1);

}

    }

    public void btnClear(View view) {
        entry.setText("");
        expressed.setText("");
        lastEntry.setText("");
    }

    public void btnClearEntry(View view) {
        entry.setText("");

    }

    public void btnPercent(View view) {
        if(!entry.getText().toString().equals("")) {
        String getEntry=entry.getText().toString();
        Double i=Double.parseDouble(getEntry);
        i=i/100;
        String u=String.valueOf(i);
        entry.setText(u);}

    }

    public void btnSquareRoot(View view) {
        if(!entry.getText().toString().equals("")) {
        String getEntry=entry.getText().toString();
        Double i=Double.parseDouble(getEntry);
        i=Math.sqrt(i);
        String u=String.valueOf(i);
        entry.setText(u);}


    }

    public void btnSquare(View view) {
        if(!entry.getText().toString().equals("")) {
        String getEntry=entry.getText().toString();
        Double i=Double.parseDouble(getEntry);
        i=i*i;
        String u=String.valueOf(i);
        entry.setText(u);}

    }

    public void btnReciprocal(View view) {

        if(!entry.getText().toString().equals("")) {
            String getEntry = entry.getText().toString();
            Double i = Double.parseDouble(getEntry);
            i = 1 / i;
            String u = String.valueOf(i);
            entry.setText(u);
        }

    }





    //append history

    public void saveHistory()
    {
        String logging = lastEntry.getText().toString();
        logging = historyString +"\n" + logging;
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(logging.getBytes());
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

    public String loadLog()
    {
        String historyLog = "";
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String logged;
            while((logged=br.readLine()) !=null ) {
                sb.append(logged).append("\n");
            }

            //text.setText(sb.toString());
            historyLog = sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return historyLog;


    }
















    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String savedEntry = entry.getText().toString();
        outState.putString("key_Entry",savedEntry);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        String restoredEntry = savedInstanceState.getString("key_Entry");
        entry.setText("" + restoredEntry);

    }



    //to history
    public void toHistory(View view) {
        historyString = loadLog();
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("log", historyString);
        intent.putExtra("filename", FILE_NAME);
        startActivity(intent);
    }




}