package com.example.festivalapptest;

        import android.Manifest;
        import android.content.pm.PackageManager;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.provider.ContactsContract;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.app.Fragment;
        import android.support.v4.content.ContextCompat;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;

        import static android.content.ContentValues.TAG;

public class FriendsFragment extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();
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
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[] { Manifest.permission.READ_CONTACTS } ,
                    PERMISSION_REQUEST_READ_CONTACTS );
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),R.layout.friend_item, contacts );

        // Only works when there are contacts in the list
        // Maybe need to add an exception later on 
        listView.setAdapter(adapter);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if (requestCode == PERMISSION_REQUEST_READ_CONTACTS){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showContacts();
            }
        } else {
            Toast.makeText(getContext(), "Please give permission to show the list of contacts", Toast.LENGTH_LONG);
        }
    }

    private void showContacts(){
        cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");

        contacts = new ArrayList<>();

        while(cursor.moveToNext()){
            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contacts.add("Name: " + contactName + "\n" + "Phone Number: " + phoneNumber );

            Log.d(TAG,"showContacts: " + contacts);
        }

        cursor.close();
    }
}
