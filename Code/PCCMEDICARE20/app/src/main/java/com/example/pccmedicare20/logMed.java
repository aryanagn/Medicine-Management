package com.example.pccmedicare20;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class logMed extends AppCompatActivity {

    ArrayList<String[]> meds = new ArrayList<String[]>();
    Spinner spinnerLog;
    Button back;
    private TextView medLog, Log, thedate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_med);

        spinnerLog = (Spinner) findViewById(R.id.spinnerLog);
        back = (Button) findViewById(R.id.buttonBackTrack);
        medLog = (TextView) findViewById(R.id.textViewMedLog);
        Log = (TextView) findViewById(R.id.textViewLog);
        thedate = (TextView) findViewById(R.id.textView24);

        Intent in = getIntent();
        meds = (ArrayList<String[]>) in.getSerializableExtra("xxx");

        ArrayList<String> meds1 = new ArrayList<>();
        for(String[] a: meds)
            meds1.add(a[0]);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, meds1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLog.setAdapter(arrayAdapter);

        String logInit = "";
        for (String[] a : meds){
            if(a[10].equals("yes"))
               logInit = logInit + a[0] + "\n";
        }
        medLog.setText(logInit);

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(today);
        thedate.setText("Entry for " +formattedDate);
    }

   public void addLog(View view) {
        String medName = (String) spinnerLog.getSelectedItem();
        for (String[] a : meds) {
            if(a[0].equals(medName)) {
                a[10] = "yes";
                Toast.makeText(this, "Logged " + medName, Toast.LENGTH_LONG).show();
            }
        }
       displayLog();
    }

    public void remove(View view){
        String medName = (String) spinnerLog.getSelectedItem();
        for (String[] a : meds) {
            if(a[0].equals(medName)) {
                a[10] = "no";
                Toast.makeText(this, "Removed " + medName + " From Log", Toast.LENGTH_LONG).show();
            }
        }
        displayLog();
    }

    public void displayLog(){
        String logList="";
        for (String[] a : meds){
            if(a[10].equals("yes"))
                logList = logList + a[0] + "\n";
        }
        medLog.setText(logList);
    }

    public void goBack(View view) {

        Intent intent = new Intent(this,track.class);
        Bundle bundle = new Bundle();

        intent.putExtra("xxx", meds);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}