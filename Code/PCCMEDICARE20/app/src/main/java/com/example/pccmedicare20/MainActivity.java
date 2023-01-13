package com.example.pccmedicare20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button start;
    ArrayList<String[]> meds = new ArrayList<String[]>();
    String[] aa = {"Add Medication","","","","","","","","",""};
    private EditText UserName, Password;
    String username, password;
    private TextView Hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserName = findViewById(R.id.editTextUserName);
        Password = findViewById(R.id.editTextPassword);
        Hint = findViewById(R.id.textViewHint);

        Hint.setText("Hint:" +"\n"+ "User Name: test" + "\n" + "Password: password");

        meds.add(aa);

        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                show();//go to settings page
            }
        });
    }

    private void show(){

        username = UserName.getText().toString();
        password = Password.getText().toString();

        if (username.equals("test") && password.equals("password")) {

            Intent intent = new Intent(this, show.class);
            Bundle bundle = new Bundle();
            intent.putExtra("xxx", meds);
            intent.putExtras(bundle);

            startActivity(intent);
        }
        else
            Toast.makeText(this, "Invalid login - see hint", Toast.LENGTH_LONG).show();
    }
}