package com.example.bookbayandroid;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class ContactFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_contact, container, false);

        TextView textView1 =(TextView)root.findViewById(R.id.anshul);
        TextView textView2 =(TextView)root.findViewById(R.id.ayush);
        TextView textView3 =(TextView)root.findViewById(R.id.prakhar);
        TextView textView4 =(TextView)root.findViewById(R.id.gaurav);
        textView1.setClickable(true);
        textView2.setClickable(true);
        textView3.setClickable(true);
        textView4.setClickable(true);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());
        textView2.setMovementMethod(LinkMovementMethod.getInstance());
        textView3.setMovementMethod(LinkMovementMethod.getInstance());
        textView4.setMovementMethod(LinkMovementMethod.getInstance());

        String text = "<a href='https://github.com/Anshul718'>Anshul718</a>";
        textView1.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));

        text = "<a href='https://github.com/t-aayu'>t-aayu</a>";
        textView2.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));

        text = "<a href='https://github.com/prakharmath'>prakharmath</a>";
        textView3.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));

        text = "<a href='https://github.com/Gaurav314'>Gaurav314</a>";
        textView4.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));


        return root;
    }

}
