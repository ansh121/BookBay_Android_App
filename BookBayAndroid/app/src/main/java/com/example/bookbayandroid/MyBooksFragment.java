package com.example.bookbayandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import static android.content.Context.MODE_PRIVATE;


public class MyBooksFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mybooks, container, false);

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent addbookIntent = new Intent(view.getContext(), AddBook.class);
                view.getContext().startActivity(addbookIntent);
            }
        });

        SharedPreferences sp1=getContext().getSharedPreferences("userdetails", MODE_PRIVATE);
        String username=sp1.getString("username", null);

        String type = "mybooks";
        BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
        try {
            String str_result = backgroundWorker.execute(type,username).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String books=sp1.getString("mybooks", null);
        if(books.equals("No Book Added!")){
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setMessage(books);
            alertDialog.show();
        }
        else {
            String[] book = books.split(";");
            final ListView list = root.findViewById(R.id.list);
            ArrayList<MyBooksData> arrayList = new ArrayList<MyBooksData>();
            int i;
            for(i=0;i<book.length;i++){
                String[] params = book[i].split(":");
                String available="";
                if(params[1].equals("1")){
                    available="Yes";
                }
                else{
                    available="No";
                }
                arrayList.add(new MyBooksData(params[7], params[9],params[5],params[8],params[10],available));
            }
            CustomAdapterMyBooks customAdapter = new CustomAdapterMyBooks(root.getContext(), arrayList);
            list.setAdapter(customAdapter);
        }

        return root;
    }
}
