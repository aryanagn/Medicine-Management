package com.example.pccmedicare20;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;

public class SettingsMainActivity extends AppCompatActivity {

    TextView textSize, text;
    SeekBar seekBar;
    View parentView;
    SwitchMaterial themeSwitch;
    TextView themeTV, titleTV;
    ArrayList<String[]> meds = new ArrayList<String[]>();
    Button button_help, button_back;
    Button button_notification;

    UserSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_main);

        button_notification = findViewById(R.id.button_notification);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notifChannel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notifManager = getSystemService(NotificationManager.class);
            notifManager.createNotificationChannel(notifChannel);
        }

        button_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(SettingsMainActivity.this, "My Notification" );
                notifBuilder.setContentTitle("Notification");
                notifBuilder.setContentText("Medication Notification: Please take your daily dosage");
                notifBuilder.setSmallIcon(R.drawable.ic_launcher_background);
                notifBuilder.setAutoCancel(true);
                NotificationManagerCompat notifCompat = NotificationManagerCompat.from(SettingsMainActivity.this);
                notifCompat.notify(3, notifBuilder.build());


            }
        });

        button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button_help = findViewById(R.id.button_help);

        Intent in = getIntent();
        meds = (ArrayList<String[]>) in.getSerializableExtra("xxx");
//slider stuff

        button_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www2.gov.bc.ca/gov/content/health";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });


        seekBar=findViewById(R.id.seekBar);
        textSize = findViewById(R.id.txt1);
        text = findViewById(R.id.txt2);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textSize.setTextSize(i);
                text.setText(String.valueOf(i));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        settings = (UserSettings) getApplication();

        inFunctions();
        loadPreferences();
        themeSwitcher();
    }

    private void inFunctions()
    {
        themeTV = findViewById(R.id.themeTV);
        titleTV = findViewById(R.id.settingsTv);
        themeSwitch = findViewById(R.id.themeSwitch);
        parentView = findViewById(R.id.parentView);
    }

    private void loadPreferences()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE);
        String theme = sharedPreferences.getString(UserSettings.CUSTOM_THEME, UserSettings.LIGHT_THEME);
        settings.setCustomTheme(theme);
        changeView();
    }

    private void themeSwitcher()
    {
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked)
            {
                if(checked)
                    settings.setCustomTheme(UserSettings.DARK_THEME);
                else
                    settings.setCustomTheme(UserSettings.LIGHT_THEME);

                SharedPreferences.Editor editor = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE).edit();
                editor.putString(UserSettings.CUSTOM_THEME, settings.getCustomTheme());
                editor.apply();
                changeView();
            }
        });
    }

    private void changeView()
    {
        final int black = ContextCompat.getColor(this, R.color.black);
        final int white = ContextCompat.getColor(this, R.color.white);

        if(settings.getCustomTheme().equals(UserSettings.DARK_THEME))
        {
            titleTV.setTextColor(white);
            themeTV.setTextColor(white);
            themeTV.setText("Dark");
            parentView.setBackgroundColor(black);
            themeSwitch.setChecked(true);
        }
        else
        {
            titleTV.setTextColor(black);
            themeTV.setTextColor(black);
            themeTV.setText("Light");
            parentView.setBackgroundColor(white);
            themeSwitch.setChecked(false);
        }
    }

    public void goBack(View view){
        Intent intent = new Intent(this, show.class);
        Bundle bundle = new Bundle();

        intent.putExtra("xxx", meds);
        intent.putExtras(bundle);

        startActivity(intent);

    }


}