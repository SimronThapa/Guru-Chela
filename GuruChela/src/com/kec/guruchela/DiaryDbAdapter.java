package com.kec.guruchela;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DiaryDbAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_DATE = "diarydate";
	public static final String KEY_DESCRIPTION = "diarydescription";

	private static final String TAG = "DiaryDbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final String DATABASE_NAME = "Diary";
	private static final String SQLITE_TABLE = "DailyDiary";
	private static final int DATABASE_VERSION = 1;

	private final Context mCtx;

	private static final String DATABASE_CREATE = "CREATE TABLE if not exists "
			+ SQLITE_TABLE + " (" + KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, " + KEY_DATE + " TEXT NOT NULL, "
			+ KEY_DESCRIPTION + " TEXT NOT NULL);" ;

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

	public DiaryDbAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	public DiaryDbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		if (mDbHelper != null) {
			mDbHelper.close();
		}
	}

	public long createDiary(String code, String description) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_DATE, code);
		initialValues.put(KEY_DESCRIPTION, description);
		return mDb.insert(SQLITE_TABLE, null, initialValues);
	}
	public Cursor fetchAllDiary() {

		Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] { KEY_ROWID, KEY_DATE,
				KEY_DESCRIPTION }, null, null,
				null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	public void deleteEntry(long lRow1) throws SQLException{
		mDb.delete(SQLITE_TABLE, KEY_ROWID + "=" + lRow1, null);
	}
	public String getDiaryContent(long l) throws SQLException{
		String[] columns = new String[] {KEY_ROWID, KEY_DATE, KEY_DESCRIPTION};
		Cursor c = mDb.query(SQLITE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if(c != null){
			c.moveToFirst();
			String name = c.getString(2);
			return name;
		}
		return null;
	}
}
