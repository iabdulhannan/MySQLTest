package com.example.mysqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText password, username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById( R.id.username);
        password = findViewById(R.id.password);
    }

    public void login(View view) {
        String user = username.getText().toString();
        String pass = password.getText().toString();
        Background bg = new Background(this);
        bg.execute(user,pass);
    }
}