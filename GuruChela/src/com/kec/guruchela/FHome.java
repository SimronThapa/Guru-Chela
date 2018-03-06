package com.kec.guruchela;

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

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class FHome extends SherlockFragment {
	public FHome() {
		// Empty constructor required for fragment subclasses
	}

	// TODO Auto-generated constructor stub
	public static Fragment newInstance(Context context) {
		FHome f = new FHome();

		return f;
	}

	String finaluser;
	String classname;
	String faculty;

	TextView description;
	TextView shareditem;
	ViewGroup root;
	ArrayList<ItemDetails> results = new ArrayList<ItemDetails>();

	ListView lv1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle bundle = this.getArguments();
		if (bundle != null) {
			finaluser = bundle.getString("username");

		}
		root = (ViewGroup) inflater.inflate(R.layout.x_f_home, null);
		// shareditem = (TextView) root.findViewById(R.id.tvDislapySharedItem);

		lv1 = (ListView) root.findViewById(R.id.listView1);
		new SelectDB().execute(finaluser);
		lv1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				ItemDetails selectedItem = results.get(position);

				Toast.makeText(getActivity(), selectedItem.getDescription(),
						Toast.LENGTH_LONG).show();

			}
		});
		return root;
	}

	// select where bala
	private class SelectDB extends AsyncTask<String, String, String> {

		private String resp = "";

		@Override
		protected String doInBackground(String... params) {
			
			
															// onProgressUpdate()
			String url = "http://www.toolittletoobig.com/guruchela_php/select_share_inner1.php";

			HttpClient httpclient = new DefaultHttpClient();
			
			try {
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("username",finaluser));

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
			String []hotelList=result.split("!");
			
			  for(String hotel:hotelList)
			  {
				  
				  String row[]=hotel.split(",");
				  ItemDetails item_details = new ItemDetails();
				  item_details.setUname(row[0]);
				  item_details.setDescription(row[1]);
				  item_details.setShareditem(row[2]);
				  //item_details.setImageNumber(1);
				  results.add(item_details);
				 
			  }
			  lv1.setAdapter(new ItemListBaseAdapter(getActivity(), results));
			  
			 
			  
		}

		@Override
		protected void onPreExecute() {
			// Things to be done before execution of long running operation. For
			// example showing ProgessDialog
		}

		@Override
		protected void onProgressUpdate(String... text) {
			
			// Things to be done while execution of long running operation is in
			// progress. For example updating ProgessDialog
			Toast.makeText(getActivity(), text[0],
					Toast.LENGTH_LONG).show();
		}

	}}