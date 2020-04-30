package com.example.bookbayandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    SearchView searchView;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        searchView = (SearchView)root.findViewById(R.id.search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dosearch(root, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return root;
    }

    public void dosearch(View root, String query){

        String type = "search";
        BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
        try {
            String str_result = backgroundWorker.execute(type,query).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SharedPreferences sp1=getContext().getSharedPreferences("userdetails", MODE_PRIVATE);
        String books=sp1.getString("searchresult", null);
        if(books.equals("No Result!")){

        }
        else {
            String[] book = books.split(";");
            final ListView list = root.findViewById(R.id.list);
            ArrayList<MyBooksData> arrayList = new ArrayList<MyBooksData>();
            int i;
            for(i=0;i<book.length;i++){
                String[] params = book[i].split(":");
                arrayList.add(new MyBooksData(params[1],params[3],params[0],params[2],params[4],""));
            }
            CustomAdapterSearchBook customAdapter = new CustomAdapterSearchBook(root.getContext(), arrayList);
            list.setAdapter(customAdapter);
        }
    }
}

