package com.example.bookbayandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

    EditText mTextUsername,mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp1=getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp1.edit();
        editor.clear();
        editor.apply();

        mTextUsername = (EditText)findViewById(R.id.edittext_username);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mButtonLogin = (Button)findViewById(R.id.button_register);
        mTextViewRegister = (TextView)findViewById(R.id.textview_login);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    public void OnLogin(View view) throws ExecutionException, InterruptedException, TimeoutException {
        String username = mTextUsername.getText().toString();
        String password = mTextPassword.getText().toString();
        String type = "login";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        String wait = backgroundWorker.execute(type, username, password).get(1000, TimeUnit.MILLISECONDS);

        SharedPreferences sp = getSharedPreferences("userdetails", MODE_PRIVATE);
        String status = sp.getString("loginstatus",null);

        if(status!=null && status.equals("success")){
            finish();
        }
    }
}
