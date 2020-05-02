package com.example.bookbayandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.ExecutionException;

public class NavDrawer extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_myaccount, R.id.nav_mybooks, R.id.nav_pendingrequests, R.id.nav_history, R.id.nav_contact, R.id.nav_about, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        SharedPreferences sp1=this.getSharedPreferences("userdetails", MODE_PRIVATE);
        String username=sp1.getString("username", null);

        String type = "home";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        try {
            String str_result = backgroundWorker.execute(type, username).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TextView name_id = (TextView) findViewById(R.id.name_id);
        username=sp1.getString("username", null);
        name_id.setText(username);

        TextView email = (TextView) findViewById(R.id.email);
        String mail=sp1.getString("email", null);
        email.setText(mail);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }





    public void bookdetail(View view){
        TextView mTextisbn = (TextView) view.findViewById(R.id.isbn);
        String isbn = (String) mTextisbn.getText().toString();
        Log.d("myTag",isbn);
        SharedPreferences sp=view.getContext().getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("viewbookisbn",isbn);
        Ed.commit();
        Intent viewbookIntent = new Intent(view.getContext(),ViewBook.class);
        startActivity(viewbookIntent);
    }

    public void userwithbook(View view){
        TextView mTextisbn = (TextView) view.findViewById(R.id.isbn);
        String isbn = (String) mTextisbn.getText().toString();
        Log.d("myTag userwithbook",isbn);
        SharedPreferences sp=view.getContext().getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("bookdetailisbn",isbn);
        Ed.commit();
        Intent bookdetailIntent = new Intent(view.getContext(),BookDetail.class);
        startActivity(bookdetailIntent);
    }

    public void viewincomingrequest(View view){
        TextView mTextrequestid = (TextView) view.findViewById(R.id.requestid);
        String requestid = (String) mTextrequestid.getText().toString();
        Log.d("myTag IncomingRequest",requestid);

        TextView mtextflag = view.findViewById(R.id.flag);
        String flag = mtextflag.getText().toString();

        SharedPreferences sp=view.getContext().getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();
        if(flag.equals("default")){
            Ed.putString("incomingrequestid",requestid);
            Ed.commit();
            Intent viewrequestIntent = new Intent(view.getContext(),ViewIncomingRequest.class);
            startActivity(viewrequestIntent);
        }
        else if(flag.equals("accept")){
            Ed.putString("incomingacceptrequestid",requestid);
            Ed.commit();
            Intent viewrequestIntent = new Intent(view.getContext(),ViewIncomingAcceptRequest.class);
            startActivity(viewrequestIntent);
        }
        else if(flag.equals("decline")){
            Ed.putString("incomingdeclinerequestid",requestid);
            Ed.commit();
            Intent viewrequestIntent = new Intent(view.getContext(),ViewIncomingDeclineRequest.class);
            startActivity(viewrequestIntent);
        }
        else if(flag.equals("cancel")){
            Ed.putString("incomingcancelrequestid",requestid);
            Ed.commit();
            Intent viewrequestIntent = new Intent(view.getContext(),ViewIncomingCancelRequest.class);
            startActivity(viewrequestIntent);
        }

        //String imgname = img.getTag().toString();
        //Log.d("myTag",imgname);
    }

    public void viewoutgoingrequest(View view){
        TextView mTextrequestid = (TextView) view.findViewById(R.id.requestid);
        String requestid = (String) mTextrequestid.getText().toString();
        Log.d("myTag OutgoingRequest",requestid);

        TextView mtextflag = view.findViewById(R.id.flag);
        String flag = mtextflag.getText().toString();

        SharedPreferences sp=view.getContext().getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();

        if(flag.equals("default")){
            Ed.putString("outgoingrequestid",requestid);
            Ed.commit();
            Intent viewrequestIntent = new Intent(view.getContext(),ViewOutgoingRequest.class);
            startActivity(viewrequestIntent);
        }
        else if(flag.equals("accept")){
            Ed.putString("outgoingacceptrequestid",requestid);
            Ed.commit();
            Intent viewrequestIntent = new Intent(view.getContext(),ViewOutgoingAcceptRequest.class);
            startActivity(viewrequestIntent);
        }
        else if(flag.equals("decline")){
            Ed.putString("outgoingdeclinerequestid",requestid);
            Ed.commit();
            Intent viewrequestIntent = new Intent(view.getContext(),ViewOutgoingDeclineRequest.class);
            startActivity(viewrequestIntent);
        }
        else if(flag.equals("cancel")){
            Ed.putString("outgoingcancelrequestid",requestid);
            Ed.commit();
            Intent viewrequestIntent = new Intent(view.getContext(),ViewOutgoingCancelRequest.class);
            startActivity(viewrequestIntent);
        }
    }

}