package com.kec.guruchela;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

public class ReportCard extends SherlockActivity {
	String roll, fac, clas; 
	TextView tv;
	LinearLayout hr;
	EditText saveas;
	String str,marks;
	final Context context = this;
	Dialog lo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		roll = extras.getString("rollnumber");
		fac = extras.getString("faculty");
		clas = extras.getString("semester");
		setContentView(R.layout.report_card);
		Button search = (Button) findViewById(R.id.b_reportcard);
		tv = (TextView) findViewById(R.id.tv_reportcard);
		hr = (LinearLayout) findViewById(R.id.ll_reportcard);
		hr.setDrawingCacheEnabled(true);
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				new SelectDB().execute(fac, clas, roll);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.menu_save, menu);
		
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {

		case R.id.menu_savesave1:
			
			String name;
			lo = new Dialog(context);
			lo.setContentView(R.layout.reportcard_dialog);
			lo.setTitle("Save your Report Card!");
			saveas = (EditText) lo.findViewById(R.id.et_reportcard_saveas);
			Button bsaveas = (Button) lo.findViewById(R.id.b_reportcard_saveas);
			bsaveas.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					str = saveas.getText().toString();
					getScreen(str);
				}
			});
			lo.show();
			
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}

	}

	private void getScreen(String st) {
		// TODO Auto-generated method stub
		 marks = st;
		 Log.d("marks_sheet_name:", marks);
		Bitmap bitmap = hr.getDrawingCache();
		File file = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
						+ "/" + marks + ".png");
		try {
			file.createNewFile();
			FileOutputStream ostream = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, ostream);
			ostream.close();
			Toast.makeText(ReportCard.this, "" + "Your Report Card is saved!",
					Toast.LENGTH_LONG).show();
			lo.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// select where bala
	private class SelectDB extends AsyncTask<String, String, String> {

		private String resp = "";

		@Override
		protected String doInBackground(String... params) {
			publishProgress("Reading Data..." + params[0]); // Calls
															// onProgressUpdate()
			String url = "http://www.toolittletoobig.com/guruchela_php/select_subject_gc.php";

			HttpClient httpclient = new DefaultHttpClient();

			try {
				List<NameValuePair> param = new ArrayList<NameValuePair>();

				param.add(new BasicNameValuePair("faculty", params[0]));
				param.add(new BasicNameValuePair("classname", params[1]));
				param.add(new BasicNameValuePair("rollnumber", params[2]));
				String paramString = URLEncodedUtils.format(param, "utf-8");
				// Creating get url
				url += "?" + paramString;

				URI path = new URI(url);
				HttpGet httpget = new HttpGet(path);

				HttpResponse response = httpclient.execute(httpget);

				HttpEntity entity = response.getEntity();
				resp = EntityUtils.toString(entity, "UTF-8");

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				resp = "Error:" + e.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				resp = "Error:" + e.toString();
			} /*
			 * catch (ParserConfigurationException e) { // TODO Auto-generated
			 * catch block e.printStackTrace(); } catch (SAXException e) { //
			 * TODO Auto-generated catch block e.printStackTrace(); }
			 */catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return resp;

		}

		@Override
		protected void onPostExecute(String result) {
			// execution of result of Long time consuming operation
			// display.setText(result);
			tv.setText("");

			String[] userList = result.split(":");
			int i = 1;

			for (String user : userList) { // TextView tv = (TextView)

				tv.setText(tv.getText() + "\n" + user + "\n");
				i++;
				// description.setText("   "+finaluser+" have shared:"+"\n   "+userList1[0]);
				// shareditem.setText("   "+finaluser+" have also shared a link:"+"\n   "+userList1[1]);

			}

		}

		@Override
		protected void onPreExecute() {
			// Things to be done before execution of long running operation. For
			// example showing ProgessDialog
		}

		@Override
		protected void onProgressUpdate(String... text) {
			tv.setText(text[0]);
			// Things to be done while execution of long running operation is in
			// progress. For example updating ProgessDialog
		}

	}
}
