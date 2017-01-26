package com.example.faizrehman.campus_recruitment_system;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

   private Button adminLogin,companyLogin,studentLogin;
   private   TextView signupText;
    private android.support.v4.app.FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            adminLogin = (Button)findViewById(R.id.admin_Login_btn);
            companyLogin = (Button)findViewById(R.id.company_login_btn);
            studentLogin = (Button)findViewById(R.id.student_login_btn);
            signupText = (TextView)findViewById(R.id.sign_up);
        fragmentManager = getSupportFragmentManager();

            signupText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager.beginTransaction().add(R.id.container, new signup_fragment()).addToBackStack(null).commit();
                }
            });

            adminLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    intent.putExtra("TAG","admin");
                    startActivity(intent);
                }
            });

            studentLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    intent.putExtra("TAG","student");
                    startActivity(intent);
                }
            });

            companyLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    intent.putExtra("TAG","company");
                    startActivity(intent);
                }
            });

    }
}
