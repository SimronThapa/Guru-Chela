package com.kec.guruchela;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

public class FNewDiary extends SherlockActivity { 
	LinearLayout hr;
	TextView calender;
	EditText diary;
	String mydate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.x_write_diary);
		this.setTitle("Diary");
		hr = (LinearLayout) findViewById(R.id.ll_diary);
	    calender = (TextView)findViewById(R.id.tvDay);
		diary = (EditText) findViewById(R.id.etDiary);
		hr.setDrawingCacheEnabled(true);
		 mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		calender.setText(mydate);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.save_share, menu);
		MenuItem actionItem = menu.findItem(R.id.menu_shareshare);
		ShareActionProvider actionProvider = (ShareActionProvider) actionItem
				.getActionProvider();
		actionProvider
				.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
		actionProvider.setShareIntent(createShareIntent());
		return true;
	}

	 private Intent createShareIntent() {
			// startActivity(Intent.createChooser(share, "Share Image"));
			 
		        Intent shareIntent = new Intent(Intent.ACTION_SEND);
		        shareIntent.setType("image/*");
		        Uri uri = Uri.fromFile(getFileStreamPath("notes.png"));
		        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/DCIM/notes.png"));
		     //   startActivity(Intent.createChooser(createShareIntent(), "Share Image"));
		        return shareIntent;
		    }
	
	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {

		case R.id.menu_savesave:
			boolean didItWork = true;
			try{
				String desc = diary.getText().toString();
				
				
				DiaryDbAdapter db = new DiaryDbAdapter(FNewDiary.this);
				db.open();
				db.createDiary(mydate,desc);
				db.close();
			}catch(Exception e){
				didItWork = false;
				String error = e.toString();
				Toast.makeText(FNewDiary.this,
						error, Toast.LENGTH_LONG)
						.show();
			}finally{
				if(didItWork){
					Toast.makeText(FNewDiary.this,
							"Success! Your entry is saved to database!", Toast.LENGTH_LONG)
							.show();
					diary.setText("");
					
					
				}
			}
			
			getScreen();
			return true;
		

		default:
			return super.onOptionsItemSelected(item);

		}

	}
	private void getScreen() {
		// TODO Auto-generated method stub

		Bitmap bitmap = hr.getDrawingCache();
		File file = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
						+ "/" + "notes" + ".png");
		try {
			file.createNewFile();
			FileOutputStream ostream = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, ostream);
			ostream.close();
			Toast.makeText(FNewDiary.this, "" + "Your Notes is saved!",
					Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
