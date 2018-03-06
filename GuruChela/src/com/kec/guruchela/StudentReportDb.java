package com.kec.guruchela;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;





public class StudentReportDb {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_SUBNAME = "subject";
	public static final String KEY_TOTALMARKS = "total";
	public static final String KEY_ATTMARKS = "attendence";
	public static final String KEY_ASGMARKS = "assignment";
	public static final String KEY_ASTMARKS = "assestment";
	public static final String KEY_STUDNAME = "student";
	public static final String KEY_ROLLNO = "roll";
	public static final String KEY_FACULTY = "faculty";
	public static final String KEY_STOTAL = "studtotal";
	public static final String KEY_SATT = "studatt";
	public static final String KEY_SASG = "studasg";
	public static final String KEY_SAST = "studast";

	private static final String TAG = "ReportCard";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final String DATABASE_NAME = "StudentReportDb";
	private static final String SQLITE_TABLE = "StudentReport";
	private static final int DATABASE_VERSION = 1;

	private final Context mCtx;

	private static final String DATABASE_CREATE = "CREATE TABLE if not exists "
			+ SQLITE_TABLE + " (" + KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, " + KEY_SUBNAME + " TEXT NOT NULL, "
			+ KEY_TOTALMARKS + " TEXT NOT NULL, " 
			+ KEY_ATTMARKS + " TEXT NOT NULL, "
			+ KEY_ASGMARKS + " TEXT NOT NULL, "
			+ KEY_ASTMARKS + " TEXT NOT NULL, "
			+ KEY_STUDNAME + " TEXT NOT NULL, "
			+ KEY_ROLLNO + " TEXT NOT NULL, "
			+ KEY_FACULTY + " TEXT NOT NULL, "
			+ KEY_STOTAL + " TEXT NOT NULL, "
			+ KEY_SATT + " TEXT NOT NULL, "
			+ KEY_SASG + " TEXT NOT NULL, "
			+ KEY_SAST +" TEXT NOT NULL);" ;

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

	public StudentReportDb(Context ctx) {
		this.mCtx = ctx;
	}

	public StudentReportDb open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		if (mDbHelper != null) {
			mDbHelper.close();
		}
	}

	public long createReport(String subject, String total, String attendence, String assignment , String assestment , String student , String roll, String faculty , String studtotal, String studatt , String studasg, String studast) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_SUBNAME, subject);
		initialValues.put(KEY_TOTALMARKS, total);
		initialValues.put(KEY_ATTMARKS, attendence);
		initialValues.put(KEY_ASGMARKS, assignment);
		initialValues.put(KEY_ASTMARKS, assestment);
		initialValues.put(KEY_STUDNAME, student);
		initialValues.put(KEY_ROLLNO, roll);
		initialValues.put(KEY_FACULTY, faculty);
		initialValues.put(KEY_STOTAL, studtotal);
		initialValues.put(KEY_SATT, studatt);
		initialValues.put(KEY_SASG, studasg);
		initialValues.put(KEY_SAST, studast);
		return mDb.insert(SQLITE_TABLE, null, initialValues);
	}
	public Cursor fetchAll() {

		Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] { KEY_ROWID, KEY_SUBNAME, KEY_TOTALMARKS , KEY_ATTMARKS , KEY_ASGMARKS , KEY_ASTMARKS,
				KEY_STUDNAME , KEY_ROLLNO , KEY_FACULTY , KEY_STOTAL , KEY_SATT , KEY_SASG , KEY_SAST  }, null, null,
				null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	public void deleteEntry(long lRow1) throws SQLException{
		mDb.delete(SQLITE_TABLE, KEY_ROWID + "=" + lRow1, null);
	}
	public String getContent(long l) throws SQLException{
		String[] columns = new String[] {KEY_ROWID, KEY_SUBNAME, KEY_TOTALMARKS , KEY_ATTMARKS , KEY_ASGMARKS , KEY_ASTMARKS,
				KEY_STUDNAME , KEY_ROLLNO , KEY_FACULTY , KEY_STOTAL , KEY_SATT , KEY_SASG , KEY_SAST};
		
		Cursor c = mDb.query(SQLITE_TABLE, columns, KEY_ROWID + "=" + l,
				null, null, null, null);

		String result = "";
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
		
			result = result + "STUDENT NAME:   " + c.getString(6) + "\n"
					+ "ROLL NUMBER:   " + c.getString(7) + "\n" + "FACULTY:   "
					+ c.getString(8) + "\n" + "SUBJECT NAME:   "
					+ c.getString(1) + "\n" + "FINAL INTERNAL MARKS:   "
					+ c.getString(9);
			

		}
		
		return result;
	}
}
