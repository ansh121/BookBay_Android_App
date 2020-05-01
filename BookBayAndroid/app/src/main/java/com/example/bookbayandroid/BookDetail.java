package com.example.bookbayandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BookDetail extends AppCompatActivity {

    TextView mtextbookname, mtextauthors, mtextisbn, mtextlanguage, mtextyear;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_bookdetail);
            setTitle("Book Detail");

            mtextbookname = findViewById(R.id.bookname);
            mtextauthors = findViewById(R.id.authors);
            mtextisbn = findViewById(R.id.isbn);
            mtextlanguage = findViewById(R.id.language);
            mtextyear = findViewById(R.id.year);


            SharedPreferences sp1=this.getSharedPreferences("userdetails", MODE_PRIVATE);
            String isbn=sp1.getString("bookdetailisbn", null);

            String type = "bookdetail";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            try {
                String str_result = backgroundWorker.execute(type,isbn).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String books=sp1.getString("bookdetail", null);
            if(books.equals("No user with this book available!")){
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setMessage(books);
                alertDialog.show();
            }
            else {
                String[] temp = books.split(">");
                String[] book = temp[0].split(":");

                mtextbookname.setText(book[1]);
                mtextyear.setText(book[2]);
                mtextlanguage.setText(book[4]);
                mtextisbn.setText(book[0]);
                mtextauthors.setText(book[3]);

                String[] users = temp[1].split(";");
                final ListView list = findViewById(R.id.list);
                ArrayList<UserWithBookData> arrayList = new ArrayList<UserWithBookData>();
                int i;
                for(i=0;i<users.length;i++){
                    String[] params = users[i].split(":");
                    String availability;
                    if(params[1].equals("1")){
                        availability = "Yes";
                    }
                    else{
                        availability = "No";
                    }
                    arrayList.add(new UserWithBookData(params[7],params[4],availability,"Rs."+params[12]));
                }
                CustomAdaptorUserWithBook customAdapter = new CustomAdaptorUserWithBook(this, arrayList);
                list.setAdapter(customAdapter);
            }

        }

    public void requestpage(View view){
        SharedPreferences sp=view.getContext().getSharedPreferences("userdetails", MODE_PRIVATE);
        String isbn = sp.getString("bookdetailisbn",null);
        SharedPreferences.Editor Ed=sp.edit();
        TextView mTextusername = view.findViewById(R.id.username);
        String requestinguser = (String) mTextusername.getText().toString();
        Log.d("myTag requestpage",isbn+" from "+requestinguser);
        Ed.putString("requestinguser",requestinguser);
        Ed.commit();

        Intent requestuserIntent = new Intent(view.getContext(),RequestUser.class);
        startActivity(requestuserIntent);
    }

    }
