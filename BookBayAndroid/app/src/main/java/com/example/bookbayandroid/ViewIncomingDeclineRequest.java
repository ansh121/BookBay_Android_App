package com.example.bookbayandroid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewIncomingDeclineRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewincomingdeclinerequest);
        setTitle("Request");

        SharedPreferences sp1=this.getSharedPreferences("userdetails", MODE_PRIVATE);
        String requestid=sp1.getString("incomingdeclinerequestid", null);
        String requests=sp1.getString("history", null);

        String[] book = requests.split(";");
        int i;
        for(i=0;i<book.length;i++){
            String[] params = book[i].split(":");
            if(params[4].equals(requestid)){
                Log.d("myTag",book[i]);
                TextView bookname=findViewById(R.id.bookname);
                bookname.setText(params[11]);
                TextView authors=findViewById(R.id.authors);
                authors.setText(params[13]);
                TextView isbn2=findViewById(R.id.isbn);
                isbn2.setText(params[8]);
                TextView language=findViewById(R.id.language);
                language.setText(params[14]);
                TextView year=findViewById(R.id.year);
                year.setText(params[12]);

                TextView available=findViewById(R.id.availability);
                if(params[26].equals("1")){
                    available.setText("Yes");
                }
                else{
                    available.setText("No");
                }

                TextView repaymethod=findViewById(R.id.repaymethod);
                repaymethod.setText(params[25]);
                TextView otherspec=findViewById(R.id.otherspec);
                otherspec.setText(params[27]);
                TextView securitymoney=findViewById(R.id.securitymoney);
                securitymoney.setText("Rs. "+params[28]);
                TextView name=findViewById(R.id.name);
                name.setText(params[16]);
                TextView userid=findViewById(R.id.userid);
                userid.setText(params[7]);
                TextView requestid2=findViewById(R.id.requestid);
                requestid2.setText(params[4]);
                TextView dateofrequest=findViewById(R.id.dateofrequest);
                dateofrequest.setText(params[0]+":"+params[1]+":"+params[2]);
                TextView borrowduration=findViewById(R.id.borrowduration);
                borrowduration.setText(params[5]+" days");
                TextView message=findViewById(R.id.message);
                message.setText(params[3]);

                break;
            }
        }
    }


}

