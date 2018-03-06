package com.kec.guruchela;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

public class FNewEvent extends SherlockActivity {
	LinearLayout hr;
	EditText des,venue,time,date;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.x_newmemo);
		this.setTitle("Events");
		
		des = (EditText) findViewById(R.id.etdes);
		venue = (EditText) findViewById(R.id.etvenue);
		time = (EditText) findViewById(R.id.ettime);
		date = (EditText) findViewById(R.id.etdate);
		
		hr = (LinearLayout) findViewById(R.id.scroll_events);
		hr.setDrawingCacheEnabled(true);
		
		
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
		        Uri uri = Uri.fromFile(getFileStreamPath("events.png"));
		        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/DCIM/events.png"));
		     //   startActivity(Intent.createChooser(createShareIntent(), "Share Image"));
		        return shareIntent;
		    }

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {

		case R.id.menu_savesave:
			//adding to database
			boolean didItWork = true;
			try{
				String desc = des.getText().toString();
				String ven = venue.getText().toString();
				String tim = time . getText().toString();
				String dat = date.getText().toString();
				
				CountriesDbAdapter db = new CountriesDbAdapter(FNewEvent.this);
				db.open();
				db.createCountry(desc,ven,tim,dat);
				db.close();
			}catch(Exception e){
				didItWork = false;
				String error = e.toString();
				Toast.makeText(FNewEvent.this,
						error, Toast.LENGTH_LONG)
						.show();
			}finally{
				if(didItWork){
					Toast.makeText(FNewEvent.this,
							"Success! Your entry is saved to database!", Toast.LENGTH_LONG)
							.show();
					des.setText("");
					venue.setText("");
					time.setText("");
					date.setText("");
					
				}
			}
			
			//saving to sdcard
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
						+ "/" + "events" + ".png");
		try {
			file.createNewFile();
			FileOutputStream ostream = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, ostream);
			ostream.close();
			Toast.makeText(FNewEvent.this, "" + "Your Event has been saved in your SD Card too!",
					Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
