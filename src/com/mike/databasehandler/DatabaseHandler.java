/**
 * 
 */
package com.mike.databasehandler;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.mike.appmodel.AppModel;

/**
 * @author mickey20142014
 * 
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "mylocationdatabase";
	// Location table name
	private static final String TABLE_LOCATION = "mylocationtable";
	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_ZIP_CODE = "zipcode";
	private static final String KEY_ADDRESS = "address";
	private static final String KEY_DATE = "date_added";
	private static final String KEY_LOCALITY = "locality";
	private static final String KEY_IMAGEURLS = "image_urls";
	private static final String KEY_WEBURLS = "web_urls";

	private String CREATE_TABLE;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 * @param errorHandler
	 */
	public DatabaseHandler(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public DatabaseHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {

		CREATE_TABLE = "CREATE TABLE" + TABLE_LOCATION + "(" + KEY_ID
				+ "INTEGER PRIMARY KEY," + KEY_DATE + "TEXT," + KEY_LATITUDE
				+ "TEXT," + KEY_LONGITUDE + "TEXT," + KEY_ZIP_CODE + "TEXT,"
				+ KEY_ADDRESS + "TEXT," + KEY_LOCALITY + "TEXT,"
				+ KEY_IMAGEURLS + "TEXT," + KEY_WEBURLS + "TEXT," + ")";
		db.execSQL(CREATE_TABLE);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
		// Create tables again
		onCreate(db);

	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations on this part
	 */
	// Adding info to table
	public void addInfoInTable(AppModel add_info) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ID, add_info.getID());// Inserting ID
		values.put(KEY_DATE, add_info.getDate());
		values.put(KEY_LATITUDE, add_info.getLatitude());
		values.put(KEY_LONGITUDE, add_info.getLongitude());
		values.put(KEY_ZIP_CODE, add_info.getZip_code());
		values.put(KEY_ADDRESS, add_info.getStreet_address());
		values.put(KEY_LOCALITY, add_info.getLocality());
		values.put(KEY_IMAGEURLS, add_info.getDevice_imageUrls());
		values.put(KEY_WEBURLS, add_info.getWeb_imageUrls());

		db.insert(TABLE_LOCATION, null, values);
		db.close();

	}

	// Get single info
	AppModel getSingleInfo(int id) {

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_LOCATION, new String[] { KEY_ID,
				KEY_DATE, KEY_LATITUDE, KEY_LONGITUDE, KEY_ADDRESS,
				KEY_ZIP_CODE, KEY_LOCALITY, KEY_IMAGEURLS, KEY_WEBURLS },
				KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null,
				null, null);
		if (cursor != null) {

			cursor.moveToFirst();

		}

		AppModel getInfos = new AppModel(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3),
				cursor.getString(4), cursor.getString(5), cursor.getString(6),
				cursor.getString(7), cursor.getString(8));

		return getInfos;
	}

	// Get all info
	public List<AppModel> getAllInfo() {
		List<AppModel> infoList = new ArrayList<AppModel>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_LOCATION + " ORDER BY "
				+ KEY_ID + " DESC ";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {

			do {
				AppModel infoModel = new AppModel();
				infoModel.setID(Integer.parseInt(cursor.getString(0)));
				infoModel.setDate(cursor.getString(1));
				infoModel.setLatitude(cursor.getString(2));
				infoModel.setLongitude(cursor.getString(3));
				infoModel.setZip_code(cursor.getString(4));
				infoModel.setStreet_address(cursor.getString(5));
				infoModel.setLocality(cursor.getString(6));
				infoModel.setDevice_imageUrls(cursor.getString(7));
				infoModel.setWeb_imageUrls(cursor.getString(8));

				infoList.add(infoModel);

			} while (cursor.moveToNext());

		}

		return infoList;
	}

	/**
	 * Updating single contact
	 */

	public int updateContact(AppModel infoModel) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, infoModel.getID());
		values.put(KEY_DATE, infoModel.getDate());
		values.put(KEY_LATITUDE, infoModel.getID());
		values.put(KEY_LONGITUDE, infoModel.getDate());
		values.put(KEY_ZIP_CODE, infoModel.getID());
		values.put(KEY_ADDRESS, infoModel.getDate());
		values.put(KEY_LOCALITY, infoModel.getID());
		values.put(KEY_IMAGEURLS, infoModel.getDate());
		values.put(KEY_WEBURLS, infoModel.getWeb_imageUrls());

		// updating row
		return db.update(TABLE_LOCATION, values, KEY_ID + " = ?",
				new String[] { String.valueOf(infoModel.getID()) });
	}

	/**
	 * Deleting single contact
	 */

	public void deleteContact(AppModel infoModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_LOCATION, KEY_ID + " = ?",
				new String[] { String.valueOf(infoModel.getID()) });
		db.close();
	}

	/**
	 * Getting contacts Count
	 */

	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_LOCATION;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
