package clucn.disc.dsm.wsierra.contactlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * The Main Act.
 * @author Walter Sierra-Vega.
 * V 1.0
 */
public class MainActivity extends AppCompatActivity {

    // Initialize the RecyclerView, the ArrayList and the MainAdapter.
    RecyclerView recyclerView;
    ArrayList<ContactModel> arrayList = new ArrayList<ContactModel>();
    MainAdapter adapter;


    /**
     * On create.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign recyclerview
        recyclerView = findViewById(R.id.recycler_view);

        //Check permission
        checkPermission();
    }

    /**
     * Checks if permission its granted.
     */
    private void checkPermission() {
        // Check condition
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            // When permission is not granted
            // Request permission
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 100);

        }else {
            getContactList();
        }

    }

    /**
     * Gets the contact list.
     */
    private void getContactList() {

        // Initialize the uri
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        // Sorts by ascending
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC";

        // Initialize the cursor
        Cursor cursor = getContentResolver().query(uri, null, null, null, sort);

        // Check condition
        if(cursor.getCount() >0){
            // When count is greater than 0
            // Use while loop
            while(cursor.moveToNext()){
                // Cursor move to next
                // Get contact id
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                // Get contact name
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                // Initialize phone uri
                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

                // Initialize selection
                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" =?";

                // Initialize phone cursor
                Cursor phoneCursor = getContentResolver().query(uriPhone, null, selection, new String[]{id}, null);

                // Check condition
                if(phoneCursor.moveToNext()){
                    // When phone cursor move to next
                    String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    // Initialize contact model
                    ContactModel model = new ContactModel();

                    // Set name
                    model.setName(name);

                    // Set number
                    model.setNumber(number);

                    //Add model in array list
                    arrayList.add(model);

                    // Close phone cursor
                    phoneCursor.close();

                }

            }

            // Close cursor
            cursor.close();
        }
        // Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter
        adapter = new MainAdapter(this, arrayList);

        // Set adapter
        recyclerView.setAdapter(adapter);

    }

    /**
     *  Check if permission
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Check condition
        if(requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            //When permission is granted
            // Call method
            getContactList();

        }else{

            // Prints a message "Permission denied."
            Toast.makeText(MainActivity.this, "Permission Denied.",Toast.LENGTH_SHORT).show();

            // Call check permission method
            checkPermission();
        }
    }
}