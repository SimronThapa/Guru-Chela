package com.kec.guruchela;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton; 
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;


public class FCamera extends SherlockActivity implements View.OnClickListener{
	
	ImageButton ib;
	//Button b;
	ImageView iv;	
	Intent i;
	final static int cameraData = 0;
	Bitmap bmp;
	LinearLayout hr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.x_f_camera);
		 hr = (LinearLayout) findViewById(R.id.imagelayout);
		this.setTitle("Camera");
		initialize();
		InputStream is = getResources().openRawResource(R.drawable.camera);
		bmp = BitmapFactory.decodeStream(is);
	}
	private void initialize() {
		// TODO Auto-generated method stub
		iv = (ImageView) findViewById (R.id.ivimg);
		ib = (ImageButton) findViewById (R.id.ivclick);
		
		ib.setOnClickListener(this);
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		
		case R.id.ivclick:
			i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i, cameraData);
		break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK){
			Bundle extras = data.getExtras();
			bmp = (Bitmap) extras.get("data");
			iv.setImageBitmap(bmp);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.save_share, menu);
		MenuItem actionItem = menu.findItem(R.id.menu_shareshare);
		 ShareActionProvider actionProvider = (ShareActionProvider) actionItem.getActionProvider();
		    actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
		    actionProvider.setShareIntent(createShareIntent());
		return true;
	}
	
	 private Intent createShareIntent() {
			// startActivity(Intent.createChooser(share, "Share Image"));
			 
		        Intent shareIntent = new Intent(Intent.ACTION_SEND);
		        shareIntent.setType("image/*");
		        Uri uri = Uri.fromFile(getFileStreamPath("imagexyz.png"));
		        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/DCIM/imagexyz.png"));
		     //   startActivity(Intent.createChooser(createShareIntent(), "Share Image"));
		        return shareIntent;
		    }

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {

		case R.id.menu_savesave:
			getScreen();
			return true;
		

		default:
			return super.onOptionsItemSelected(item);

		}

	}
	private void getScreen() {
		// TODO Auto-generated method stub

		//Bitmap bitmap = hr.getDrawingCache();
		Bitmap bitmap = bmp;
		File file = new File( Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/"
				+ "imagexyz" + ".png");
		try {
			file.createNewFile();
			FileOutputStream ostream = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, ostream);
			ostream.close();
			Toast.makeText(FCamera.this, "" + "Your Image is saved!",
					Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
