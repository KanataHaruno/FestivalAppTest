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
        import android.view.InflateException;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;

public class FriendsFragment extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST_READ_CONTACTS = 100;
    Cursor cursor;
    ArrayList<String> contacts;
    ArrayAdapter<String> adapter;
    ListView listContacts;
    TextView textView;


    @Nullable
    @Override
    // Opens the view created in the layout file, which is called in the parameter of the inflater
    // DONT add anything to this method
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (listContacts != null){
            ViewGroup parent = (ViewGroup) listContacts.getParent();
            if (parent != null){
                parent.removeView(listContacts);
            }
        }

        try {
            return inflater.inflate(R.layout.fragment_friends, container, false);
        } catch (InflateException e){
            e.getStackTrace();
        }

        return listContacts;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listContacts = getView().findViewById(R.id.list_of_contacts);

        // Using getContext because it's a fragment
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS );

        // Check if permission is given already, else ask for permission
        if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            showContacts();
        } else {
            // Using getActivity because the activity doesn't need to be declared in a fragment
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[] { Manifest.permission.READ_CONTACTS } ,
                    PERMISSION_REQUEST_READ_CONTACTS );
        }


        // The problem is a wrong reference to the XML part
        // Maybe need to add an exception later on
        // Layout of textview type can ONLY CONTAIN A TEXTVIEW (gonna slam my head into a door now)
        listContacts.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.friend_item, contacts));

    }


    // Check if permission is given, if not, repeat
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

    // Method that gets the contacts from phone
    private void showContacts(){
        contacts = new ArrayList<>();

        cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");

        while(cursor.moveToNext()){
            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contacts.add("Name: " + contactName + "\n" + "Phone Number: " + phoneNumber );

            // The contacts are taken from the phone contacts and added to the list 'contacts'
            Log.e(TAG,"showContacts: " + contacts);
        }

        cursor.close();
    }
}
