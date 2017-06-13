package com.eric.savingsmanager.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import static com.eric.savingsmanager.data.SavingsItemEntry.TABLE_NAME;

public class SavingsContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.eric.savingsmanager.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + SavingsItemEntry.TABLE_NAME);


    /*
     * Defines a handle to the database helper object. The MainDatabaseHelper class is defined
     * in a following snippet.
     */
    private SavingsDatabaseHelper mOpenHelper;

    // Holds the database object
    private SQLiteDatabase mDatabase;

    public boolean onCreate() {

        /*
         * Creates a new helper object. This method always returns quickly.
         * Notice that the database itself isn't created or opened
         * until SQLiteOpenHelper.getWritableDatabase is called
         */
        mOpenHelper = new SavingsDatabaseHelper(
                getContext()       // the application context
        );

        return true;
    }

    // Implements the provider's insert method
    public Uri insert(Uri uri, ContentValues values) {
        // Insert code here to determine which table to open, handle error-checking, and so forth
        /*
         * Gets a writeable database. This will trigger its creation if it doesn't already exist.
         *
         */
        mDatabase = mOpenHelper.getWritableDatabase();

        // Insert the new row, returning the primary key value of the new row
        mDatabase.insert(TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        mDatabase = mOpenHelper.getWritableDatabase();
        int rowID = mDatabase.delete(TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowID;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        mDatabase = mOpenHelper.getReadableDatabase();
        Cursor cursor = mDatabase.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }else{
            return null;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        mDatabase = mOpenHelper.getWritableDatabase();
        int rowID = mDatabase.update(TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowID;
    }
}
