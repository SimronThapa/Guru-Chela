package com.kec.guruchela;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BunkManagerDb {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_SUBNAME = "subject";
	public static final String KEY_TLECTURES = "tlect";
	public static final String KEY_BLECTURES = "blect";
	public static final String KEY_RATTENDANCE = "ratt";
	public static final String KEY_CATTENDANCE = "catt";
	public static final String KEY_BEFFECIENCY = "beff";

	private static final String TAG = "BunkManager";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final String DATABASE_NAME = "BunkManagerDb";
	private static final String SQLITE_TABLE = "BunkingTable";
	private static final int DATABASE_VERSION = 1;

	private final Context mCtx;

	private static final String DATABASE_CREATE = "CREATE TABLE if not exists "
			+ SQLITE_TABLE + " (" + KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, " + KEY_SUBNAME
			+ " TEXT NOT NULL, " + KEY_TLECTURES + " TEXT NOT NULL, "
			+ KEY_BLECTURES + " TEXT NOT NULL, " + KEY_RATTENDANCE
			+ " TEXT NOT NULL, " + KEY_CATTENDANCE + " TEXT NOT NULL, "
			+ KEY_BEFFECIENCY + " TEXT NOT NULL);";

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			Log.w(TAG, DATABASE_CREATE);
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
			onCreate(db);
		}

	}

	public BunkManagerDb(Context ctx) {
		this.mCtx = ctx;
	}

	public BunkManagerDb open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		if (mDbHelper != null) {
			mDbHelper.close();
		}
	}

	public long createReport(String subject, String tlect, String blect,
			String ratt, String catt, String beff) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_SUBNAME, subject);
		initialValues.put(KEY_TLECTURES, tlect);
		initialValues.put(KEY_BLECTURES, blect);
		initialValues.put(KEY_RATTENDANCE, ratt);
		initialValues.put(KEY_CATTENDANCE, catt);
		initialValues.put(KEY_BEFFECIENCY, beff);

		return mDb.insert(SQLITE_TABLE, null, initialValues);
	}

	public Cursor fetchAll() {

		Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] { KEY_ROWID,
				KEY_SUBNAME, KEY_TLECTURES, KEY_BLECTURES, KEY_RATTENDANCE,
				KEY_CATTENDANCE, KEY_BEFFECIENCY }, null, null, null, null,
				null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public void deleteEntry(long lRow1) throws SQLException {
		mDb.delete(SQLITE_TABLE, KEY_ROWID + "=" + lRow1, null);
	}

	public void updateEntry(long lRow, String tlect, String blect) throws SQLException {
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(KEY_TLECTURES, tlect);
		cvUpdate.put(KEY_BLECTURES, blect);
		
		mDb.update(SQLITE_TABLE, cvUpdate, KEY_ROWID + "=" + lRow, null);

	}

	public Cursor getContent(long l) throws SQLException {
		String[] columns = new String[] { KEY_ROWID, KEY_SUBNAME,
				KEY_TLECTURES, KEY_BLECTURES, KEY_RATTENDANCE, KEY_CATTENDANCE,
				KEY_BEFFECIENCY };

		Cursor c = mDb.query(SQLITE_TABLE, columns, KEY_ROWID + "=" + l, null,
				null, null, null);

		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	public String getTotal(long l) throws SQLException {
		String[] columns = new String[] { KEY_ROWID, KEY_SUBNAME,
				KEY_TLECTURES, KEY_BLECTURES, KEY_RATTENDANCE, KEY_CATTENDANCE,
				KEY_BEFFECIENCY };

		Cursor c = mDb.query(SQLITE_TABLE, columns, KEY_ROWID + "=" + l, null,
				null, null, null);

		if (c != null) {
			c.moveToFirst();
			String tot = c.getString(2);
			return tot;
		}
		return null;
	}
	public String getBunked(long l) throws SQLException {
		String[] columns = new String[] { KEY_ROWID, KEY_SUBNAME,
				KEY_TLECTURES, KEY_BLECTURES, KEY_RATTENDANCE, KEY_CATTENDANCE,
				KEY_BEFFECIENCY };

		Cursor c = mDb.query(SQLITE_TABLE, columns, KEY_ROWID + "=" + l, null,
				null, null, null);

		if (c != null) {
			c.moveToFirst();
			String tot = c.getString(3);
			return tot;
		}
		return null;
	}
}
