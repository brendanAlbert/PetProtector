package edu.orangecoastcollege.cs273.petprotector;

/**
 * Created by brendantyleralbert on 10/29/17.
 */

public class Pet {
    private int mId;
    private String mName;
    private String mDetails;
    private String mPhone;
    private String mImageURI;

    public Pet(int id, String name, String details, String phone, String imageURI) {
        mId = id;
        mName = name;
        mDetails = details;
        mPhone = phone;
        mImageURI = imageURI;
    }

    public Pet(String name, String details, String phone, String imageURI) {
        mName = name;
        mDetails = details;
        mPhone = phone;
        mImageURI = imageURI;
    }

    public int getId() { return mId; }

    public String getName() { return mName; }

    public void setName(String name) { mName = name; }

    public String getDetails() { return mDetails; }

    public void setDetails(String details) { mDetails = details; }

    public String getPhone() { return mPhone; }

    public void setPhone(String phone) { mPhone = phone; }

    public String getImageURI() { return mImageURI; }

    public void setImageURI(String imageURI) { mImageURI = imageURI; }

    @Override
    public String toString() {
        return "Pet{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mDetails='" + mDetails + '\'' +
                ", mPhone='" + mPhone + '\'' +
                ", mImageURI='" + mImageURI + '\'' +
                '}';
    }
}
