package com.example.bookbayandroid;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
//import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class CustomAdapterMyBooks implements ListAdapter {
    ArrayList<MyBooksData> arrayList;
    Context context;
    public CustomAdapterMyBooks(Context context, ArrayList<MyBooksData> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyBooksData subjectData=arrayList.get(position);
        if(convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView=layoutInflater.inflate(R.layout.single_book, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            TextView bookname=convertView.findViewById(R.id.bookname);
            bookname.setText(subjectData.bookname);
            TextView authors=convertView.findViewById(R.id.authors);
            authors.setText(subjectData.authors);
            TextView isbn=convertView.findViewById(R.id.isbn);
            isbn.setText(subjectData.isbn);
            TextView available=convertView.findViewById(R.id.availability);
            available.setText(subjectData.available);
            TextView language=convertView.findViewById(R.id.language);
            language.setText(subjectData.language);
            TextView year=convertView.findViewById(R.id.year);
            year.setText(subjectData.year);
        }
        return convertView;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
}
