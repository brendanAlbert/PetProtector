package edu.orangecoastcollege.cs273.petprotector;

/**
 * Created by brendantyleralbert on 10/29/17.
 *
 * A Pet represents the Model of our Pet Protector app.
 *
 * Pets have names, details, phone numbers and an image URI to access the pet's picture.
 */
public class Pet {
    private int mId;
    private String mName;
    private String mDetails;
    private String mPhone;
    private String mImageURI;

    /**
     * A parameterized constructor which accepts all five member variables.
     * @param id
     * @param name
     * @param details
     * @param phone
     * @param imageURI
     */
    public Pet(int id, String name, String details, String phone, String imageURI) {
        mId = id;
        mName = name;
        mDetails = details;
        mPhone = phone;
        mImageURI = imageURI;
    }

    /**
     * A parameterized constructor which accepts only four member variables, the id is absent.
     * @param name
     * @param details
     * @param phone
     * @param imageURI
     */
    public Pet(String name, String details, String phone, String imageURI) {
        mName = name;
        mDetails = details;
        mPhone = phone;
        mImageURI = imageURI;
    }

    /**
     *
     * @return the pet's id.
     */
    public int getId() { return mId; }

    /**
     *
     * @return the pet's name.
     */
    public String getName() { return mName; }

    /**
     *
     * @param name the name to set.
     */
    public void setName(String name) { mName = name; }

    /**
     *
     * @return the pet's details.
     */
    public String getDetails() { return mDetails; }

    /**
     *
     * @param details the details to set.
     */
    public void setDetails(String details) { mDetails = details; }

    /**
     *
     * @return the phone number.
     */
    public String getPhone() { return mPhone; }

    /**
     *
     * @param phone the phone number to set.
     */
    public void setPhone(String phone) { mPhone = phone; }

    /**
     *
     * @return the URI to access the pet's picture.
     */
    public String getImageURI() { return mImageURI; }

    public void setImageURI(String imageURI) { mImageURI = imageURI; }

    /**
     * toString must be overridden if you wish to get the details
     * about a Pet object in one convenient package.
     * @return a string with the data pertaining to this particular object.
     */
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
