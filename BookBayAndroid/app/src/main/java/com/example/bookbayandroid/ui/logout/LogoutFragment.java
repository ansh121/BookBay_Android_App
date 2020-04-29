package com.example.bookbayandroid.ui.logout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bookbayandroid.MainActivity;
import com.example.bookbayandroid.NavDrawer;
import com.example.bookbayandroid.R;
import com.example.bookbayandroid.RegisterActivity;

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
