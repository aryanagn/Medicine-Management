package com.example.pccmedicare20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class track extends AppCompatActivity {
    String dt = "2022/11/14";
    Button goback, buttonLog;
    ArrayList<String[]> meds = new ArrayList<String[]>();
    LocalDate caldt;
    LocalDate smeddt;
    String date;
    String taken; //a[9]
    Date today;
    private TextView thedate, medInfo, takenText, refillText;
    private CalendarView mCalendarView;
    String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        goback = (Button) findViewById(R.id.goback);
        buttonLog = (Button) findViewById(R.id.buttonLog);

        Intent in = getIntent();
        meds = (ArrayList<String[]>) in.getSerializableExtra("xxx");// get curent list of meds

        thedate = (TextView) findViewById(R.id.textViewDate);
        medInfo = (TextView) findViewById(R.id.textViewMedInfo);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        takenText = (TextView) findViewById(R.id.textViewTaken);
        refillText = (TextView) findViewById(R.id.textViewRefill);


        // code to populate output on opening tack page
        today = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        formattedDate = df.format(today);
        thedate.setText("Schedule for \n" +formattedDate);
        caldt = LocalDate.parse(formattedDate);
        displayInfo();


        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
                month++;
                date = year + "-";
                if (month < 10)
                    date = date + "0" + month + "-";
                else
                    date = date + month + "-";
                if (dayOfMonth < 10)
                    date = date + "0" + dayOfMonth;
                else
                    date = date + dayOfMonth;

                thedate.setText("Schedule for \n" +date);
                caldt = LocalDate.parse(date);
                displayInfo();
          }

        });
        goback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                home();
            }
        });
    }

    private void home() {
        Intent intent = new Intent(this, show.class);
        Bundle bundle = new Bundle();

        intent.putExtra("xxx", meds);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    // before calling displayInfo() need to set 'caldt' variable
    private void displayInfo(){
        String take = "";
        String taken = "";
        String taken2 = "";

        for (String[] a : meds) {
            smeddt = LocalDate.parse(a[4]);
            if (a[2].equals("daily")) {
                if (caldt.isAfter(smeddt))
                //if(a[10].equals("no"))
                    take = take + "Take " + a[0] + " " + a[1] + "\n";
                if(a[10].equals("yes"))
                    taken = taken + "Taken " + a[0] + " " + a[1] + "\n";

            }
            if (a[2].equals("weekly")) {
                LocalDate temdt = smeddt;
                for (int i = 0; i <= 52; i++) { // i<10 signifies 10 weeks on meds. Alter to 52 for whole year.
                    //temdt = temdt.plusDays(7);
                    if (temdt.compareTo(caldt) == 0) {
                        //if(a[10].equals("no"))
                        take = take + "Take " + a[0] + " " + a[1] + "\n";
//                        if(a[10].equals("yes"))
//                            taken2 = taken2 + "Taken " + a[0] + "\n";
                    }
                    temdt = temdt.plusDays(7);
                }
                if(a[10].equals("yes"))
                    taken2 = taken2 + "Taken " + a[0] + " " + a[1] + "\n";
            }
//            if(a[10].equals("yes"))
//                taken = taken + "Taken " + a[0] + "\n";
//            if(a[10].equals("yes"))
//                taken2 = taken2 + "Taken " + a[0] + "\n";

        }

        // refill
        String refil = "Refills Due: \n";
        String refillMeds = "";
        for (String[] a : meds) {

            if (LocalDate.parse(a[6]).equals(caldt)) {
                refillMeds = refillMeds + a[0] + "\n";
            }
        }
        medInfo.setText(take);

       // takenText.setText(taken);
        if(!formattedDate.equals(date))
            takenText.setText("");
        else
            takenText.setText(taken + taken2);
        refillText.setText(refil + refillMeds);
    }

    public void goLog(View view){

        LocalDate todaysDate = LocalDate.parse(formattedDate);
        if (caldt.isAfter(todaysDate))
            Toast.makeText(this, "Can't Log For Future Date!", Toast.LENGTH_LONG).show();
        else {
            Intent intent = new Intent(this, logMed.class);
            Bundle bundle = new Bundle();

            intent.putExtra("xxx", meds);
            intent.putExtras(bundle);

            startActivity(intent);
        }
    }


}


