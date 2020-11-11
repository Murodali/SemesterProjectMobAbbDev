package com.example.semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Feed extends AppCompatActivity {

    private TextView t1,t2;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        t1 =(TextView)findViewById(R.id.textViewUser);
        t2 = (TextView)findViewById(R.id.textView);
        logout = (Button)findViewById(R.id.LogOut);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("token",MODE_PRIVATE);
        String username = sp.getString("username","");
        sp.getString("password","");
        String token =sp.getString("token","");

        t1.setText(username);
        t2.setText(token);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("token",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("token").apply();


                Intent logout = new Intent(getApplication(), LoginActivity.class);
                startActivity(logout);
                finish();

            }

        });


    }
}