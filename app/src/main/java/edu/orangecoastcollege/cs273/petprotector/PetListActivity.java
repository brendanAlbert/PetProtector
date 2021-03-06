package edu.orangecoastcollege.cs273.petprotector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static edu.orangecoastcollege.cs273.petprotector.R.id.petImageView;

/**
 * PetListActivity is the main controller for the PetProtector app.
 *
 * This app was instructional in how to handle requesting User permissions and using a Universal
 * Resource Identifier (URI).
 *
 * The User's permission is required to be able to access their camera or external storage, such
 * as the images in their Image Gallery.
 *
 * Constants are created for permission granted or denied.
 *
 * The onCreate method sets the content view and wires up the pet Image View.
 * The pet Image View gets the image from a URI.
 *
 * The selectPetImage method is called when the User taps the ImageView.
 * If this is the first time, then the permissions will be requested.
 * Once the permissions are allowed, the onActivityResult method is called.
 *
 * onActivityResult is called and will display the Image Gallery if the user
 * allowed access.
 */
public class PetListActivity extends AppCompatActivity {

    private DBHelper db;
    private ImageView mPetImageView;
    private Uri imageUri;

    // Constants for permissions:
    private static final int GRANTED = PackageManager.PERMISSION_GRANTED;
    public static final int DENIED = PackageManager.PERMISSION_DENIED;


    // widgets for adding a pet
    private EditText mNameEditText;
    private EditText mDetailsEditText;
    private EditText mPhoneEditText;

    // member variables for the list view
    private List<Pet> mPetsList;
    private PetListAdapter mPetListAdapter;

    /**
     * The onCreate method sets the content view and wires up the pet Image View.
     * The pet Image View gets the image from a URI we created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        mPetImageView = (ImageView) findViewById(petImageView);
        mPetImageView.setImageURI(getUriFromResource(this, R.drawable.none));

        mNameEditText = (EditText) findViewById(R.id.petNameEditText);
        mDetailsEditText = (EditText) findViewById(R.id.petDetailsEditText);
        mPhoneEditText = (EditText) findViewById(R.id.petPhoneEditText);
        ListView petsListView = (ListView) findViewById(R.id.petListView);

        mNameEditText.setText("");
        mNameEditText.setHint("Name");
        mDetailsEditText.setText("");
        mDetailsEditText.setHint("Pet Details");
        mPhoneEditText.setText("");
        mPhoneEditText.setHint("Phone Number");

        db = new DBHelper(this);

        mPetsList = db.getAllPets();
        mPetListAdapter = new PetListAdapter(this, R.layout.pet_list_item, mPetsList);
        petsListView.setAdapter(mPetListAdapter);
    }

    /**
     * getUriFromResource is called from onCreate to set the initial ImageView.
     *
     * This static method is also called from addPet to set the image correctly.
     *
     * A URI is built from a scheme, host and a path.
     *
     * URI's are created dynamically
     * so this static method is incredibly versatile.
     * @param context
     * @param resId
     * @return
     */
    public static Uri getUriFromResource(Context context, int resId)
    {
        Resources res = context.getResources();
        // Build a String in the form:
        // android.resource://edu.orangecoastcollege.cs273.petprotector/drawable/none
        String uri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + res.getResourcePackageName(resId) + "/"
                + res.getResourceTypeName(resId) + "/"
                + res.getResourceEntryName(resId);

        // Parse the String in order to construct a URI
        return Uri.parse(uri);
    }

    /**
     * The selectPetImage method is called when the User taps the ImageView.
     * If this is the first time, then the permissions will be requested.
     * This is performed by building a list of denied permissions.
     * Once the permissions are allowed, the onActivityResult method is called.

     * @param v is the View object to be used from the Image Gallery.
     */
    public void selectPetImage(View v)
    {
        List<String> permissionsList = new ArrayList<>();

        // Check each permission individually
        int hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (hasCameraPermission == DENIED)
            permissionsList.add(Manifest.permission.CAMERA);

        int readStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readStoragePermission == DENIED)
            permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        int writeStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writeStoragePermission == DENIED)
            permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // Some permissions have not been granted
        if (permissionsList.size() > 0)
        {
            // Convert the permissionsList into an array:
            String[] permsArray = new String[permissionsList.size()];
            permissionsList.toArray(permsArray);

            // Ask user for them:
            ActivityCompat.requestPermissions(this, permsArray, 1337);
        }

        // Let's make sure we have all the permissions, then start up the Image Gallery:
        if (hasCameraPermission == GRANTED && readStoragePermission == GRANTED && writeStoragePermission == GRANTED)
        {
            // Let's open up the image gallery
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // Start activity for a result (picture)
            startActivityForResult(galleryIntent, 1);
        }
    }

    /**
     * onActivityResult is called and will display the Image Gallery if the user
     * allowed access.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            // data is the data from GalleryIntent (the URI of some image)
            imageUri = data.getData();
            mPetImageView.setImageURI(imageUri);
        }
    }

    /**
     * viewPetDetails is called when the User taps on a particular Pet displayed in the list.
     *
     * An Intent is created, the Pet's details are added to the intent
     * and it is passed to PetDetailsActivity.java via the startActivity static method call.
     * @param view is the View object tapped on
     */
    public void viewPetDetails(View view)
    {
        Intent petDetails = new Intent(this, PetDetailsActivity.class);

        LinearLayout selectedLayout = (LinearLayout) view;
        Pet selectedPet = (Pet) selectedLayout.getTag();

        petDetails.putExtra("name", selectedPet.getName());
        petDetails.putExtra("details", selectedPet.getDetails());
        petDetails.putExtra("phone", selectedPet.getPhone());
        petDetails.putExtra("image", selectedPet.getImageURI());

        startActivity(petDetails);
    }

    /**
     * addPet is called when the User taps the ADD PET button.
     * A check ensures that all the fields have data before adding to the database.
     * Then the Views are reset as a visual notification that the addition took place
     * and to clean the slate if the User wishes to add another Pet.
     * @param view
     */
    public void addPet(View view) {
        String name = mNameEditText.getText().toString();
        String details = mDetailsEditText.getText().toString();
        String phone = mPhoneEditText.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(details) || TextUtils.isEmpty(phone))
            Toast.makeText(this, "All information about the pet must be provided", Toast.LENGTH_LONG).show();
        else
        {
            Pet newPet = new Pet(name, details, phone, imageUri.toString());
            db.addPet(newPet);
            mPetsList.add(newPet);
            mPetListAdapter.notifyDataSetChanged();
        }

        mNameEditText.setText("");
        mNameEditText.setHint("Name");
        mDetailsEditText.setText("");
        mDetailsEditText.setHint("Pet Details");
        mPhoneEditText.setText("");
        mPhoneEditText.setHint("Phone Number");
        mPetImageView.setImageURI(getUriFromResource(this,R.drawable.none));
    }
}
