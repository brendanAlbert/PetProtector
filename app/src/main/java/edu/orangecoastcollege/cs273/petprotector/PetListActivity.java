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
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class PetListActivity extends AppCompatActivity {

    private ImageView petImageView;

    // Constants for permissions:
    private static final int GRANTED = PackageManager.PERMISSION_GRANTED;
    public static final int DENIED = PackageManager.PERMISSION_DENIED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        petImageView = (ImageView) findViewById(R.id.petImageView);
        petImageView.setImageURI(getUriFromResource(this, R.drawable.none));
    }

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
}
