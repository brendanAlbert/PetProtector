package edu.orangecoastcollege.cs273.petprotector;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by brendantyleralbert on 10/29/17.
 *
 * PetListAdapter is a helper controller that is used to populate
 * the details of each individual Pet of the list in PetListActivity.
 */
public class PetListAdapter extends ArrayAdapter<Pet> {

    private Context mContext;
    private List<Pet> mPetsList;
    private int mResourceId;

    /**
     * PetListAdapter is a parameterized constructor that accepts three arguments.
     * @param context is the Activity this adapter will be displayed in (PetListActivity)
     * @param resource is the xml resource file that will be inflated
     * @param pets is the List of Pets which will be used to populate the list
     */
    public PetListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Pet> pets) {
        super(context, resource, pets);
        mContext = context;
        mResourceId = resource;
        mPetsList = pets;
    }

    /**
     * getView displays the data at a specified position.
     *
     * @param position is the current position whose view to use
     * @param convertView either reuse a view or create a new one for displaying the data
     * @param parent the parent the view will be attached to
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View petListItemView = inflater.inflate(mResourceId, null);

        // wire up the View widgets
        ImageView petListImageView = petListItemView.findViewById(R.id.petImageView);
        TextView petListNameView = petListItemView.findViewById(R.id.nameTextView);
        TextView petListDetailsView = petListItemView.findViewById(R.id.descriptionTextView);
        LinearLayout petListLinearLayout = petListItemView.findViewById(R.id.petListLinearLayout);

        Pet selectedPet = mPetsList.get(position);

        petListNameView.setText(selectedPet.getName());
        petListDetailsView.setText(selectedPet.getDetails());
        petListImageView.setImageURI(Uri.parse(selectedPet.getImageURI()));

        petListLinearLayout.setTag(selectedPet);

        return petListItemView;
    }
}
