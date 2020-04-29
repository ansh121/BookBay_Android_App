package com.example.bookbayandroid.ui.myaccount;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bookbayandroid.R;

public class MyAccountFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_myaccount, container, false);

        EditText mTextUsername = root.findViewById(R.id.editText_username);
        EditText mTextEmail = root.findViewById(R.id.editText_email);
        EditText mTextName = root.findViewById(R.id.editText_name);
        EditText mTextHouseNo = root.findViewById(R.id.editText_houseno);
        EditText mTextStreet = root.findViewById(R.id.editText_street);
        EditText mTextLocality = root.findViewById(R.id.editText_locality);
        EditText mTextPostalCode = root.findViewById(R.id.editText_postalcode);
        EditText mTextLandmark = root.findViewById(R.id.editText_landmark);
        EditText mTextCity = root.findViewById(R.id.editText_city);
        EditText mTextState = root.findViewById(R.id.editText_state);
        EditText mTextMobileNo = root.findViewById(R.id.editText_mobileno);

        SharedPreferences sp1=this.getActivity().getSharedPreferences("userdetails", this.getActivity().MODE_PRIVATE);
        String username=sp1.getString("username", null);
        String email = sp1.getString("email",null);
        String name = sp1.getString("name",null);
        String houseno = sp1.getString("houseno",null);
        String street = sp1.getString("street",null);
        String locality = sp1.getString("locality",null);
        String postalcode = sp1.getString("postalcode",null);
        String landmark = sp1.getString("landmark",null);
        String city = sp1.getString("city",null);
        String state = sp1.getString("state",null);
        //String mobileno = sp1.getString("mobileno",null);

        mTextUsername.setText(username);
        mTextEmail.setText(email);
        mTextName.setText(name);
        mTextHouseNo.setText(houseno);
        mTextStreet.setText(street);
        mTextLocality.setText(locality);
        mTextPostalCode.setText(postalcode);
        mTextLandmark.setText(landmark);
        mTextCity.setText(city);
        mTextState.setText(state);

        return root;
    }
}
