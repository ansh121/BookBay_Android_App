package com.example.bookbayandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class LogoutFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_logout, container, false);
        final TextView textView = root.findViewById(R.id.text_logout);

        SharedPreferences sp1=this.getActivity().getSharedPreferences("userdetails", this.getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sp1.edit();
        editor.clear();
        editor.apply();
        this.getActivity().finish();

        Intent registerIntent = new Intent(LogoutFragment.this.getActivity(), MainActivity.class);
        startActivity(registerIntent);

        return root;
    }
}
