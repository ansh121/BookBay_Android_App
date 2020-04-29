package com.example.bookbayandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class AddBook extends AppCompatActivity {

    EditText mTextisbn,mTextrepaymethod,mTextotherspec,mTextsecuritymoney;
    Switch mSwitchavailability;
    Button mButtonsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);

        mTextisbn = (EditText)findViewById(R.id.editText_ISBN);
        mTextrepaymethod = (EditText)findViewById(R.id.editText_repaymethod);
        mSwitchavailability = (Switch)findViewById(R.id.availability);
        mTextotherspec = (EditText)findViewById(R.id.editText_otherspec);
        mTextsecuritymoney = (EditText)findViewById(R.id.editText_securitymoney);
        mButtonsignup = (Button)findViewById((R.id.button_addbook));
        mButtonsignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                OnAddBook(view);
            }
        });

    }

    public void OnAddBook(View view){
        String isbn = mTextisbn.getText().toString();
        String repaymethod = mTextrepaymethod.getText().toString();
        boolean flag = mSwitchavailability.isChecked();
        String availability;
        if(flag){
            availability = "1";
        }
        else{
            availability = "0";
        }
        String otherspec = mTextotherspec.getText().toString();
        String securitymoney = mTextsecuritymoney.getText().toString();

        String type = "addbook";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,isbn,repaymethod,availability,otherspec,securitymoney);
    }
}
