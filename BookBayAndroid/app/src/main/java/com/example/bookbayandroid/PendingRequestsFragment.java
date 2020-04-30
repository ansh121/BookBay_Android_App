package com.example.bookbayandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class PendingRequestsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pendingrequests, container, false);

        SharedPreferences sp1=getContext().getSharedPreferences("userdetails", MODE_PRIVATE);
        String username=sp1.getString("username", null);

        String type = "pendingrequests";
        BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
        try {
            String str_result = backgroundWorker.execute(type,username).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String requests=sp1.getString("pendingrequests", null);
        if(requests.equals("No request found!")){
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setMessage(requests);
            alertDialog.show();
        }
        else {
            String[] book = requests.split(";");
            final ListView list1 = root.findViewById(R.id.list1);
            final ListView list2 = root.findViewById(R.id.list2);
            ArrayList<IncomingRequestData> arrayListincoming = new ArrayList<IncomingRequestData>();
            ArrayList<IncomingRequestData> arrayListoutgoing = new ArrayList<IncomingRequestData>();
            int i;
            for(i=0;i<book.length;i++){
                String[] params = book[i].split(":");
                if(username.equals(params[9]))arrayListincoming.add(new IncomingRequestData(params[4], params[11],params[8],params[16],params[15],params[0]+":"+params[1]+":"+params[2],params[5]));
                else arrayListoutgoing.add(new IncomingRequestData(params[4], params[11],params[8],params[16],params[15],params[0]+":"+params[1]+":"+params[2],params[5]));
            }
            CustomAdapterIncomingRequest customAdapterin = new CustomAdapterIncomingRequest(root.getContext(), arrayListincoming);
            CustomAdapterOutgoingRequest customAdapterout = new CustomAdapterOutgoingRequest(root.getContext(), arrayListoutgoing);
            if(!(arrayListincoming.isEmpty())) list1.setAdapter(customAdapterin);
            if(!(arrayListoutgoing.isEmpty())) list2.setAdapter(customAdapterout);
        }

        return root;
    }
}
