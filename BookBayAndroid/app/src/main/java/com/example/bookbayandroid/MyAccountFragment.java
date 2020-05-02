package com.example.bookbayandroid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bookbayandroid.NavDrawer;
import com.example.bookbayandroid.R;

import static android.content.Context.MODE_PRIVATE;

public class MyAccountFragment extends Fragment {

    EditText mTextUsername;
    EditText mTextEmail;
    EditText mTextName;
    EditText mTextHouseNo;
    EditText mTextStreet;
    EditText mTextLocality;
    EditText mTextPostalCode;
    EditText mTextLandmark;
    EditText mTextCity;
    EditText mTextState;
    EditText mTextMobileNo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_myaccount, container, false);

        mTextUsername = (EditText) root.findViewById(R.id.editText_username);
        mTextEmail = (EditText) root.findViewById(R.id.editText_email);
        mTextName = (EditText) root.findViewById(R.id.editText_name);
        mTextHouseNo = (EditText) root.findViewById(R.id.editText_houseno);
        mTextStreet = (EditText) root.findViewById(R.id.editText_street);
        mTextLocality = (EditText) root.findViewById(R.id.editText_locality);
        mTextPostalCode = (EditText) root.findViewById(R.id.editText_postalcode);
        mTextLandmark = (EditText) root.findViewById(R.id.editText_landmark);
        mTextCity = (EditText) root.findViewById(R.id.editText_city);
        mTextState = (EditText) root.findViewById(R.id.editText_state);
        mTextMobileNo = (EditText) root.findViewById(R.id.editText_mobileno);

        SharedPreferences sp1= getContext().getSharedPreferences("userdetails", MODE_PRIVATE);

        String username = sp1.getString("username", null);
        String email = sp1.getString("email",null);
        String name = sp1.getString("name",null);
        String houseno = sp1.getString("houseno",null);
        String street = sp1.getString("street",null);
        String locality = sp1.getString("locality",null);
        String postalcode = sp1.getString("postalcode",null);
        String landmark = sp1.getString("landmark",null);
        String city = sp1.getString("city",null);
        String state = sp1.getString("state",null);
        String mobileno = sp1.getString("mobileno",null);

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
        mTextMobileNo.setText(mobileno);

        Button save = (Button) root.findViewById(R.id.button_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateaccount(view);
            }
        });

        return root;
    }

    public void updateaccount(View view){

        String user_name = mTextUsername.getText().toString();
        String email = mTextEmail.getText().toString();
        String name = mTextName.getText().toString();
        String houseno = mTextHouseNo.getText().toString();
        String street = mTextStreet.getText().toString();
        String locality = mTextLocality.getText().toString();
        String postalcode = mTextPostalCode.getText().toString();
        String landmark = mTextLandmark.getText().toString();
        String city = mTextCity.getText().toString();
        String state = mTextState.getText().toString();
        String mobileno = mTextMobileNo.getText().toString();

        String type = "updateaccount";

        BackgroundWorker backgroundWorker = new BackgroundWorker(view.getContext());
        backgroundWorker.execute(type, user_name, email, name, houseno, street, locality, postalcode, landmark, city, state, mobileno);
    }


}
