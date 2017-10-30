package edu.orangecoastcollege.cs273.petprotector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class PetDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        ImageView petDetailsImageView = (ImageView) findViewById(R.id.detailsImageView);
        TextView petDetailsNameTextView = (TextView) findViewById(R.id.detailsNameTextView);
        TextView petDetailsDetailsTextView = (TextView) findViewById(R.id.detailsDetailTextView);
        TextView petDetailsPhoneTextView = (TextView) findViewById(R.id.detailsPhoneTextView);

        Intent petDetailsIntent = getIntent();
        String name = petDetailsIntent.getStringExtra("name");
        String details = petDetailsIntent.getStringExtra("details");
        String phone = petDetailsIntent.getStringExtra("phone");
        String image = petDetailsIntent.getStringExtra("image");

        petDetailsImageView.setImageURI(Uri.parse(image));
        petDetailsNameTextView.setText(name);
        petDetailsDetailsTextView.setText(details);
        petDetailsPhoneTextView.setText(phone);
    }
}
