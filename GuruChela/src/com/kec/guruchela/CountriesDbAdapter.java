package com.kec.guruchela;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CountriesDbAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_DESCRIPTION = "code";
	public static final String KEY_VENUE = "name";
	public static final String KEY_TIME = "continent";
	public static final String KEY_DATE = "region";

	private static final String TAG = "CountriesDbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final String DATABASE_NAME = "World";
	private static final String SQLITE_TABLE = "Country";
	private static final int DATABASE_VERSION = 1;

	private final Context mCtx;

	private static final String DATABASE_CREATE = "CREATE TABLE if not exists "
			+ SQLITE_TABLE + " (" + KEY_ROWID
			+ " integer PRIMARY KEY autoincrement," + KEY_DESCRIPTION + ","
			+ KEY_VENUE + "," + KEY_TIME + "," + KEY_DATE + "," + " UNIQUE ("
			+ KEY_DESCRIPTION + "));";

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.w(TAG, DATABASE_CREATE);
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
			onCreate(db);
		}
	}

	public CountriesDbAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	public CountriesDbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		if (mDbHelper != null) {
			mDbHelper.close();
		}
	}

	public long createCountry(String code, String name, String continent,
			String region) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_DESCRIPTION, code);
		initialValues.put(KEY_VENUE, name);
		initialValues.put(KEY_TIME, continent);
		initialValues.put(KEY_DATE, region);

		return mDb.insert(SQLITE_TABLE, null, initialValues);
	}

	public boolean deleteAllCountries() {

		int doneDelete = 0;
		doneDelete = mDb.delete(SQLITE_TABLE, null, null);
		Log.w(TAG, Integer.toString(doneDelete));
		return doneDelete > 0;

	}

	public Cursor fetchCountriesByName(String inputText) throws SQLException {
		Log.w(TAG, inputText);
		Cursor mCursor = null;
		if (inputText == null || inputText.length() == 0) {
			mCursor = mDb.query(SQLITE_TABLE, new String[] { KEY_ROWID,
					KEY_DESCRIPTION, KEY_VENUE, KEY_TIME, KEY_DATE }, null,
					null, null, null, null);

		} else {
			mCursor = mDb.query(true, SQLITE_TABLE, new String[] { KEY_ROWID,
					KEY_DESCRIPTION, KEY_VENUE, KEY_TIME, KEY_DATE }, KEY_VENUE
					+ " like '%" + inputText + "%'", null, null, null, null,
					null);
		}
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	public Cursor fetchAllCountries() {

		Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] { KEY_ROWID,
				KEY_DESCRIPTION, KEY_VENUE, KEY_TIME, KEY_DATE }, null, null,
				null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	public void deleteEntry(long lRow1) throws SQLException{
		mDb.delete(SQLITE_TABLE, KEY_ROWID + "=" + lRow1, null);
	}
}
