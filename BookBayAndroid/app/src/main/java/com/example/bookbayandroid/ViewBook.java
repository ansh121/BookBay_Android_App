package com.example.bookbayandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbook);

        Button save = findViewById(R.id.button_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatebook(view);
            }
        });

        Button delete = findViewById(R.id.button_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletebook(view);
            }
        });

        SharedPreferences sp1=this.getSharedPreferences("userdetails", MODE_PRIVATE);
        String books=sp1.getString("mybooks", null);
        String isbn=sp1.getString("viewbookisbn", null);

        String[] book = books.split(";");
        int i;
        for(i=0;i<book.length;i++){
            String[] params = book[i].split(":");
            if(params[5].equals(isbn)){
                TextView bookname=findViewById(R.id.bookname);
                bookname.setText(params[7]);
                TextView authors=findViewById(R.id.authors);
                authors.setText(params[9]);
                TextView isbn2=findViewById(R.id.isbn);
                isbn2.setText(params[5]);
                TextView language=findViewById(R.id.language);
                language.setText(params[10]);
                TextView year=findViewById(R.id.year);
                year.setText(params[8]);

                Switch available=findViewById(R.id.availability);
                if(params[1].equals("1")){
                    available.setChecked(true);
                }
                else{
                    available.setChecked(false);
                }

                EditText repaymethod=findViewById(R.id.editText_repaymethod);
                repaymethod.setText(params[0]);
                EditText otherspec=findViewById(R.id.editText_otherspec);
                otherspec.setText(params[2]);
                EditText securitymoney=findViewById(R.id.editText_securitymoney);
                securitymoney.setText(params[3]);

                break;
            }
        }
    }

    public void updatebook(View view){
        Switch av = findViewById(R.id.availability);
        boolean flag = av.isChecked();
        String availability;
        if(flag){
            availability = "1";
        }
        else{
            availability = "0";
        }
        EditText mTextrepaymethod=findViewById(R.id.editText_repaymethod);
        String repaymethod = mTextrepaymethod.getText().toString();
        EditText mTextotherspec=findViewById(R.id.editText_otherspec);
        String otherspec = mTextotherspec.getText().toString();
        EditText mTextsecuritymoney=findViewById(R.id.editText_securitymoney);
        String secmoney = mTextsecuritymoney.getText().toString();
        TextView mtextisbn = findViewById(R.id.isbn);
        String isbn = mtextisbn.getText().toString();

        String type = "updatebook";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, isbn, repaymethod, otherspec, availability,secmoney);
    }

    public void deletebook(View view){
        TextView mtextisbn = findViewById(R.id.isbn);
        String isbn = mtextisbn.getText().toString();

        String type = "deletebook";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, isbn);
    }

}
