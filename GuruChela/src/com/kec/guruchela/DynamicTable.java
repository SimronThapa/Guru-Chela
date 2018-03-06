package com.kec.guruchela;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;


public class DynamicTable extends SherlockActivity {
	private LinearLayout mainLayout;
	private TableLayout tableLayout;
	private TableRow tableRow,tableRow1;
	private TextView textView;
	private static final String SHARED_FILE_NAME = "shared.png";
	EditText name;
	static int j =1;
	EditText number;
	private String no;
	private int numb;
	HorizontalScrollView hr;
	String s,schedule;
	LinearLayout layout;
	ScrollView sc;
	int numb1;
	final Context context = this;
	boolean test = false;
	 Dialog Gudetail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		schedule = extras.getString("schedule");
		numb = 1;

		hr = new HorizontalScrollView(this);
		hr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		layout = new LinearLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		layout.setOrientation(LinearLayout.VERTICAL);
		sc = new ScrollView(this);
		sc.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		sc.setFillViewport(true);
		hr.setDrawingCacheEnabled(true);
		layout.setDrawingCacheEnabled(true);
		setContentView(hr);
		
		// int res = showDialog();

		createTable();
		sc.addView(layout);
		hr.addView(sc);
		this.setTitle("Schedule");
	}

	
	private void createTable() {
		// TODO Auto-generated method stub
		for ( int i = 1; i <= 1; i++) {
			
			no = ("" + j);
			j++;
			tableLayout = new TableLayout(this);

			tableRow = new TableRow(this);
			tableRow.setBackgroundColor(Color.rgb(189, 239, 184));

			textView = new TextView(this);
			textView.setText("No.");
			textView.setPadding(2, 0, 2, 2);
			tableRow.addView(textView);

			textView = new TextView(this);
			textView.setText("Sun");
			textView.setPadding(2, 0, 2, 2);
			tableRow.addView(textView);

			textView = new TextView(this);
			textView.setPadding(2, 0, 2, 2);
			textView.setText("Mon");
			tableRow.addView(textView);

			textView = new TextView(this);
			textView.setPadding(2, 0, 2, 2);
			textView.setText("Tue");
			tableRow.addView(textView);

			textView = new TextView(this);
			textView.setPadding(2, 0, 2, 2);
			textView.setText("Wed");
			tableRow.addView(textView);

			textView = new TextView(this);
			textView.setPadding(2, 0, 2, 2);
			textView.setText("Thu");
			tableRow.addView(textView);

			textView = new TextView(this);
			textView.setPadding(2, 0, 2, 2);
			textView.setText("Fri");
			tableRow.addView(textView);

			textView = new TextView(this);
			textView.setPadding(2, 0, 2, 2);
			textView.setText("Sat");
			tableRow.addView(textView);

			tableLayout.addView(tableRow);

			tableRow1 = new TableRow(this);
			tableRow1.setBackgroundColor(Color.rgb(255, 255, 255));

			textView = new TextView(this);
			textView.setText(no);
			textView.setPadding(2, 0, 2, 2);
			tableRow1.addView(textView);

			EditText editText = new EditText(this);
			editText.setWidth(100);
			editText.setPadding(2, 0, 2, 2);
			tableRow1.addView(editText);

			editText = new EditText(this);
			editText.setWidth(100);
			editText.setPadding(2, 0, 2, 2);
			tableRow1.addView(editText);

			editText = new EditText(this);
			editText.setWidth(100);
			editText.setPadding(2, 0, 2, 2);
			tableRow1.addView(editText);

			editText = new EditText(this);
			editText.setWidth(100);
			editText.setPadding(2, 0, 2, 2);
			tableRow1.addView(editText);

			editText = new EditText(this);
			editText.setWidth(100);
			editText.setPadding(2, 0, 2, 2);
			tableRow1.addView(editText);

			editText = new EditText(this);
			editText.setWidth(100);
			editText.setPadding(2, 0, 2, 2);
			tableRow1.addView(editText);

			editText = new EditText(this);
			editText.setWidth(100);
			editText.setPadding(2, 0, 2, 2);
			tableRow1.addView(editText);

			tableLayout.addView(tableRow1);

			layout.addView(tableLayout);
		}

	//	sc.addView(layout);
	//	hr.addView(sc);
		
	}

 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.menu_share_only, menu);
		MenuItem actionItem = menu.findItem(R.id.menu_shareonly);
		 ShareActionProvider actionProvider = (ShareActionProvider) actionItem.getActionProvider();
		    actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
		    actionProvider.setShareIntent(createShareIntent());
		    
		return true;
	}
	
	
	 private Intent createShareIntent() {
		// startActivity(Intent.createChooser(share, "Share Image"));
		 
	        Intent shareIntent = new Intent(Intent.ACTION_SEND);
	        shareIntent.setType("image/*");
	        Uri uri = Uri.fromFile(getFileStreamPath(schedule+".png"));
	        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/DCIM/"+schedule+".png"));
	     //   startActivity(Intent.createChooser(createShareIntent(), "Share Image"));
	        return shareIntent;
	    }
	 

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {

		case R.id.menu_add:
			createTable();
			return true;

		case R.id.menu_save:
			getScreen();

			return true;
			
		case R.id.menu_shareonly:
			//startActivity(Intent.createChooser(createShareIntent(), "Share Image"));

			return true;

		default:
			return super.onOptionsItemSelected(item);

		}

	}

	private void getScreen() {
		// TODO Auto-generated method stub

		Bitmap bitmap = layout.getDrawingCache();
		File file = new File( Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/"
				+ schedule + ".png");
		try {
			file.createNewFile();
			FileOutputStream ostream = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, ostream);
			ostream.close();
			Toast.makeText(DynamicTable.this, "" + "Your Schedule is saved!",
					Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}



}
