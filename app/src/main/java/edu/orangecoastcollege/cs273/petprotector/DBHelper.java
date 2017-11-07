package edu.orangecoastcollege.cs273.petprotector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by brendantyleralbert on 10/29/17.
 *
 * DBHelper is another vital part of the Model of our Pet Protector app.
 * Since we need to persist the Pet data, we use an SQLite database.
 *
 * This code is practically boilerplate for whenever you want to create a database
 * and persist data.
 *
 * DBHelper is an SQLiteOpenHelper.
 *
 * We have constants for the database information and for the table.
 *
 * onCreate and onUpgrade must be implemented when working with SQLIteOpenHelper.
 * addPet and getAllPets are specific methods for our app, although most apps
 * will probably implement these methods too.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PetProtector";
    private static final String DATABASE_TABLE = "Pets";
    private static final int DATABASE_VERSION = 1;

    // Define the fields for the table
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DETAILS = "details";
    private static final String FIELD_PHONE = "phone";
    private static final String FIELD_IMAGE_URI = "uri";


    /**
     * DBHelper is a parameterized constructor which accepts a context argument.
     * Inside the method, its super method is called and we pass the context,
     * the database name,
     * null for the cursor factory,
     * and the database version, which is updated when onUpgrade is called.
     * @param context
     */
    public DBHelper(Context context) { super (context, DATABASE_NAME, null, DATABASE_VERSION); }

    /**
     * onCreate must be implemented when working with SQLiteOpenHelper.
     *
     * A string is constructed to represent the SQL query.
     *
     * The SQL query is executed via database.execSQL(the_SQL_query);
     * @param database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {

        String createTable =
                "CREATE TABLE " + DATABASE_TABLE
                + " ( " + KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_NAME + " TEXT, "
                + FIELD_DETAILS + " TEXT, "
                + FIELD_PHONE + " TEXT, "
                + FIELD_IMAGE_URI + " TEXT " + ")";
        database.execSQL(createTable);
    }

    /**
     * onUpgrade must also be implemented with working with SQLIteOpenHelper.
     * When the database is upgrade, the previous table is dropped and it
     * is recreated with a call to onCreate.
     *
     * @param database the name of the database to work with
     * @param oldVersion the previous version number of the db
     * @param newVersion the new version number of the db
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    /**
     * addPet is called when we have received valid input from the User.
     *
     * The pet's details are acquired using a ContentValues object, we insert them into
     * and close the database.
     * @param pet
     */
    public void addPet(Pet pet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_NAME, pet.getName());
        values.put(FIELD_DETAILS, pet.getDetails());
        values.put(FIELD_PHONE, pet.getPhone());
        values.put(FIELD_IMAGE_URI, pet.getImageURI() + "");

        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }

    /**
     * getAllPets is called in PetListActivity to populate a list of Pet's which will be used
     * in the adapter, PetListAdapter.
     *
     * A readable database object is gotten.
     * A Cursor object is created by querying the database table using the requisite table values.
     * The cursor is used to get the values to populate Pet objects
     * which then populate the petList.
     *
     * Remember to close the cursor and the database.
     *
     * @return an ArrayList of Pet objects.
     */
    public ArrayList<Pet> getAllPets() {
        ArrayList<Pet> petList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(DATABASE_TABLE,
                new String[] {KEY_FIELD_ID, FIELD_NAME, FIELD_DETAILS, FIELD_PHONE, FIELD_IMAGE_URI},
                null, null,null,null,null);

        if (cursor.moveToFirst()) {
            do {
                Pet pet = new Pet(
                        cursor.getInt(0), // id
                        cursor.getString(1), // name
                        cursor.getString(2), // details
                        cursor.getString(3), // phone
                        cursor.getString(4)); // image URI
                petList.add(pet);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return petList;
    }

}
