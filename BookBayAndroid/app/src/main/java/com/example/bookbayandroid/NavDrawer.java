package com.example.bookbayandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
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
        getMenuInflater().inflate(R.menu.nav_drawer, menu);

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
}