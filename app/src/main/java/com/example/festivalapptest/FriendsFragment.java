package com.example.festivalapptest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendsFragment extends Fragment {

    private static final int PERMISSION_REQUEST_READ_CONTACTS = 100;
    Cursor cursor;
    ArrayList<String> contacts;

    @Nullable
    @Override
    // Opens the view created in the layout file, which is called in the parameter of the inflater
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView;
        TextView textView;

        listView = getView().findViewById(R.id.list_contacts);

        // Using getContext because it's a fragment
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS );

        if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            showContacts();
        } else {
            // Using getActivity because the activity doesn't need to be declared in a fragment
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] { Manifest.permission.READ_CONTACTS } ,
                    PERMISSION_REQUEST_READ_CONTACTS );
        }

        /////////////////////////
        // Resource is single listitem !!!!
        /////////////////////////
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.id., contacts );

    }
}
