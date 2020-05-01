package com.example.bookbayandroid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

import static android.view.View.INVISIBLE;

public class RequestUser extends AppCompatActivity {
    TextView mtextbookname;
    TextView mtextname;
    TextView mtextauthor;
    TextView mtextisbn;
    TextView mtextyear;
    TextView mtextlanguage;
    TextView mtextrepaymethod;
    TextView mtextavailability;
    TextView mtextotherspec;
    TextView mtextsecuritymoney;
    TextView mtextborrow;
    TextView mtextmsg;
    EditText mtextborrowduration;
    EditText mtextmessage;
    TextView mtextalert;

    String bookisbn;
    String ownerid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestuser);
        setTitle("Request Book");

        mtextbookname = findViewById(R.id.bookname);
        mtextname = findViewById(R.id.ownername);
        mtextauthor = findViewById(R.id.authors);
        mtextisbn = findViewById(R.id.isbn);
        mtextyear = findViewById(R.id.year);
        mtextlanguage = findViewById(R.id.language);
        mtextrepaymethod = findViewById(R.id.repaymethod);
        mtextavailability = findViewById(R.id.availability);
        mtextotherspec = findViewById(R.id.otherspec);
        mtextsecuritymoney = findViewById(R.id.securitymoney);
        mtextborrow = findViewById(R.id.borrow);
        mtextmsg = findViewById(R.id.msg);
        mtextborrowduration = findViewById(R.id.borrowduration);
        mtextmessage = findViewById(R.id.message);
        mtextalert = findViewById(R.id.notavailable);

        Button makerequest = findViewById(R.id.button_makerequest);
        makerequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    makerequest(view);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        SharedPreferences sp1=this.getSharedPreferences("userdetails", MODE_PRIVATE);
        String isbn = sp1.getString("bookdetailisbn",null);
        String username = sp1.getString("requestinguser",null);

        String type = "requestpage";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        try {
            String str_result = backgroundWorker.execute(type, isbn, username).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String details = sp1.getString("requestpage",null);
        String[] params = details.split(":");

        String availability;
        if(params[16].equals("1")){
            availability="yes";
            mtextalert.setVisibility(INVISIBLE);
        }
        else{
            availability="No";
            mtextborrowduration.setVisibility(INVISIBLE);
            mtextmessage.setVisibility(INVISIBLE);
            mtextborrow.setVisibility(INVISIBLE);
            mtextmsg.setVisibility(INVISIBLE);
            makerequest.setVisibility(INVISIBLE);
        }

        mtextbookname.setText(params[11]);
        mtextname.setText(params[1]);
        mtextauthor.setText(params[13]);
        mtextisbn.setText(params[10]);
        mtextyear.setText(params[12]);
        mtextlanguage.setText(params[14]);
        mtextrepaymethod.setText(params[15]);
        mtextavailability.setText(availability);
        mtextotherspec.setText(params[17]);
        mtextsecuritymoney.setText("Rs. "+params[18]);

        bookisbn=params[10];
        ownerid=params[0];
    }

    public void makerequest(View view) throws ExecutionException, InterruptedException {
        String borrowduration = mtextborrowduration.getText().toString();
        String message = mtextmessage.getText().toString();


        String type = "makerequest";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        String str_result = backgroundWorker.execute(type, borrowduration, message, bookisbn, ownerid).get();

        mtextborrowduration.setText("");
        mtextmessage.setText("");
    }
}
