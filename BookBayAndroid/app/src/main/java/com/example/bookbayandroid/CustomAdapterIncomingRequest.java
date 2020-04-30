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

public class CustomAdapterIncomingRequest implements ListAdapter {
    ArrayList<IncomingRequestData> arrayList;
    Context context;
    public CustomAdapterIncomingRequest(Context context, ArrayList<IncomingRequestData> arrayList) {
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
        IncomingRequestData subjectData=arrayList.get(position);
        if(convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView=layoutInflater.inflate(R.layout.incoming_request_element, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            TextView requestid=convertView.findViewById(R.id.requestid);
            requestid.setText(subjectData.requestid);
            TextView bookname=convertView.findViewById(R.id.bookname);
            bookname.setText(subjectData.bookname);
            TextView isbn=convertView.findViewById(R.id.isbn);
            isbn.setText(subjectData.isbn);
            TextView name=convertView.findViewById(R.id.name);
            name.setText(subjectData.name);
            TextView username=convertView.findViewById(R.id.username);
            username.setText(subjectData.username);
            TextView borrowduration=convertView.findViewById(R.id.borrowduration);
            borrowduration.setText(subjectData.borrowduration);
            TextView dateofrequest=convertView.findViewById(R.id.dateofrequest);
            dateofrequest.setText(subjectData.dateofrequest);
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
