package com.kec.guruchela;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.support.v4.view.GravityCompat;

public class NavigationDrawer extends SherlockFragmentActivity {

	// Declare Variable
	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	MenuListAdapter mMenuAdapter;
	String[] title;
	String[] subtitle;
	int[] icon;
	// String Ssub, Stmarks, Satt, Sasg, Sast;
	private static final String SHARED_FILE_NAME = "shared.png";
	EditText sub, tmarks, att, asg, ast, newP, confirmP;
	Fragment fragment1 = new FGProfile();
	Fragment fragment2 = new FGHome();
	// Fragment fragment3 = new FCamera();
	final Context context = this;
	String Ssub;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	float a, b, c;
	String finaluser;
	EditText schedulename;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		finaluser = extras.getString("username");
		setContentView(R.layout.xnavigation_drawer);

		mTitle = mDrawerTitle = getTitle();
		// Generate title
		title = new String[] { "Profile", "Home", "Schedule",
				"Student Records", "Share materials", "Memo and Reminders",
				"Instant Mail", "Click and share", "Location Tracker", "Logout" };

		// Generate subtitle
		subtitle = new String[] { "Your info...", "Whats new!",
				"Instant scheduling!", "Generate reports!",
				"Download and read!", "Stay updated", "Mailing made easy!",
				"Sharing made easy!", "Tap it to know your place!", "Bye-Bye!!" };

		// Generate icon
		icon = new int[] { R.drawable.social_person, R.drawable.social_cc_bcc,
				R.drawable.collections_go_to_today, R.drawable.content_paste,
				R.drawable.content_copy, R.drawable.collections_labels,
				R.drawable.content_email, R.drawable.device_access_camera,
				R.drawable.location_map, R.drawable.alerts_and_states_warning };

		// Locate DrawerLayout in drawer_main.xml
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// Locate ListView in drawer_main.xml
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Pass results to MenuListAdapter Class
		mMenuAdapter = new MenuListAdapter(this, title, subtitle, icon);

		// Set the MenuListAdapter to the ListView
		mDrawerList.setAdapter(mMenuAdapter);

		// Capture button clicks on side menu
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// Enable ActionBar app icon to behave as action to toggle nav drawer
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				// TODO Auto-generated method stub
				getSupportActionBar().setTitle(mTitle);
				supportInvalidateOptionsMenu();
				// super.onDrawerClosed(view);
			}

			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				getSupportActionBar().setTitle(mDrawerTitle);
				supportInvalidateOptionsMenu();
				// super.onDrawerOpened(drawerView);
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.menu_ai_chat).setVisible(!drawerOpen);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.cool_menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
			return true;
		case R.id.menu_help:
			final Dialog help = new Dialog(context);
			help.setContentView(R.layout.help1);
			help.setTitle("HELP!");
			Button btn = (Button) help.findViewById(R.id.bok1);
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					help.dismiss();
				}
			});
			help.show();
			return true;
		case R.id.menu_speak:
			// kunai intent dine
			Intent l = new Intent("com.kec.guruchela.VOICE");
			startActivity(l);
			return true;
		case R.id.menu_ai_chat:
			// kunai intent dine
			Intent ai = new Intent(NavigationDrawer.this,
					HelloBubblesActivity.class);
			startActivity(ai);
			return true;

		case R.id.menu_settings:

			final Dialog changeP = new Dialog(context);
			changeP.setContentView(R.layout.changing_password);
			changeP.setTitle("Change your password!");
			EditText oldP = (EditText) changeP
					.findViewById(R.id.et_oldpassword);
			newP = (EditText) changeP.findViewById(R.id.et_newpassword);
			confirmP = (EditText) changeP.findViewById(R.id.et_confirmpassword);
			Button ok = (Button) changeP.findViewById(R.id.b_changepassword_OK);
			ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if ((newP.getText().toString()).equals(confirmP.getText()
							.toString())) {
						new UpdateDB().execute(finaluser, newP.getText()
								.toString());
					} else {
						Toast.makeText(
								NavigationDrawer.this,
								"New password doesnt match with Confirm password!",
								Toast.LENGTH_LONG).show();
					}
				}
			});
			changeP.show();
			return true;
		case R.id.menu_bug:
			Intent j = new Intent(NavigationDrawer.this, ReportBug.class);
			startActivity(j);
			return true;
		case R.id.menu_about_us:

			final Dialog AU = new Dialog(context);
			AU.setContentView(R.layout.x_about_us);
			ImageButton rate = (ImageButton) AU.findViewById(R.id.bRate);
			AU.setTitle("About Us!");
			AU.show();
			return true;
		case R.id.menu_logout:
			finish();
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}
	}

	// The click listener for ListView in the navigation drawer
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		Bundle bundle = new Bundle();
		bundle.putString("username", finaluser);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// Locate Position
		switch (position) {
		case 0:
			fragment1 = new FGProfile();
			fragment1.setArguments(bundle);
			ft.replace(R.id.content_frame, fragment1);
			break;
		case 1:
			fragment2 = new FGHome();
			ft.replace(R.id.content_frame, fragment2);
			break;
		case 4:
			Intent dropbox = new Intent(NavigationDrawer.this,
					DropboxChooser.class);
			dropbox.putExtra("username", finaluser);
			startActivity(dropbox);
			break;
		case 6:
			Intent i = new Intent(NavigationDrawer.this, Email.class);
			startActivity(i);
			break;
		case 7:
			// ft.replace(R.id.content_frame, fragment3);
			Intent j = new Intent(NavigationDrawer.this, FCamera.class);
			startActivity(j);
			break;
		case 2:
			// ft.replace(R.id.content_frame, fragment3);
			final Dialog ask = new Dialog(context);
			ask.setContentView(R.layout.x_f_schedule);
			ask.setTitle("Give youe schedule a title!");
			Button btn = (Button) ask.findViewById(R.id.bSch);
			schedulename = (EditText) ask.findViewById(R.id.et_schedulename);
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String str = schedulename.getText().toString();
					if (str.length() > 0) {
						Intent i = new Intent(NavigationDrawer.this,
								DynamicTable.class);
						i.putExtra("schedule", str);
						startActivity(i);
						ask.dismiss();
					} else {
						Toast.makeText(NavigationDrawer.this,
								"Give a name to your Schedule!",
								Toast.LENGTH_LONG).show();
					}
				}
			});
			ask.show();

			break;
		case 5:
			// ft.replace(R.id.content_frame, fragment3);
			final Dialog md = new Dialog(context);
			md.setContentView(R.layout.x_memo_diary);
			md.setTitle("Choose an activity!");
			Button bdiary = (Button) md.findViewById(R.id.bdiary);
			Button bevents = (Button) md.findViewById(R.id.bevents);
			bdiary.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					final Dialog ask11 = new Dialog(context);
					ask11.setContentView(R.layout.x_memo_dialog);
					ask11.setTitle("Choose an Activity!");
					Button btn11 = (Button) ask11
							.findViewById(R.id.b_readdiary);
					Button btn12 = (Button) ask11.findViewById(R.id.b_newdiary);
					btn12.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent j = new Intent(NavigationDrawer.this,
									FNewDiary.class);
							startActivity(j);
							ask11.dismiss();
						}
					});
					btn11.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent j = new Intent(NavigationDrawer.this,
									MainDiary.class);
							startActivity(j);
						}
					});
					ask11.show();
					md.dismiss();
				}
			});
			bevents.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					final Dialog ask11 = new Dialog(context);
					ask11.setContentView(R.layout.x_events_dialog);
					ask11.setTitle("Choose an Activity!");
					Button btn11 = (Button) ask11
							.findViewById(R.id.b_viewevents);
					Button btn12 = (Button) ask11
							.findViewById(R.id.b_newevents);
					btn12.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent j = new Intent(NavigationDrawer.this,
									FNewEvent.class);
							startActivity(j);
							ask11.dismiss();
						}
					});
					btn11.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent j = new Intent(NavigationDrawer.this,
									AndroidListViewCursorAdaptorActivity.class);
							startActivity(j);
						}
					});
					ask11.show();
					md.dismiss();
				}
			});
			md.show();

			break;
		case 3:
			// ft.replace(R.id.content_frame, fragment3);
			final Dialog ask1 = new Dialog(context);
			ask1.setContentView(R.layout.x_student_dialog);
			ask1.setTitle("Choose an activity!");
			Button btn1 = (Button) ask1.findViewById(R.id.b_viewrecord);
			Button btn2 = (Button) ask1.findViewById(R.id.b_newrecord);
			btn1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent pt = new Intent(NavigationDrawer.this,
							MainReport.class);
					startActivity(pt);
				}
			});
			btn2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					final Dialog ask11 = new Dialog(context);
					ask11.setContentView(R.layout.x_internalmarks);
					ask11.setTitle("Fill it up!");

					sub = (EditText) ask11.findViewById(R.id.etSubjectTitle);
					tmarks = (EditText) ask11.findViewById(R.id.ettotalmarks);
					att = (EditText) ask11.findViewById(R.id.etattendance);
					asg = (EditText) ask11.findViewById(R.id.etassignment);
					ast = (EditText) ask11.findViewById(R.id.etassesment);
					Button btn11 = (Button) ask11.findViewById(R.id.bconfirm);

					btn11.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							// StudentReportDb db = new
							// StudentReportDb(NavigationDrawer.this);
							// db.open();
							// db.deleteAll();
							// db.close();

							Ssub = sub.getText().toString();
							String Stmarks = tmarks.getText().toString();
							String Satt = att.getText().toString();
							String Sasg = asg.getText().toString();
							String Sast = ast.getText().toString();
							a = Float.parseFloat(Satt);
							b = Float.parseFloat(Sasg);
							c = Float.parseFloat(Sast);
							float total = Float.parseFloat(Stmarks);
							Intent j = new Intent(NavigationDrawer.this,
									FReportGenerate.class);

							j.putExtra("STRING_I_NEED", Ssub);
							j.putExtra("total", total);
							j.putExtra("a", a);
							j.putExtra("b", b);
							j.putExtra("c", c);

							startActivity(j);
							ask11.dismiss();
						}

					});
					ask11.show();
					ask1.dismiss();
				}
			});
			ask1.show();
			break;
		case 9:
			final Dialog lo = new Dialog(context);
			lo.setContentView(R.layout.x_logout);
			lo.setTitle("Logging out!");
			Button nop = (Button) lo.findViewById(R.id.bnop);
			Button sure = (Button) lo.findViewById(R.id.bsure);
			nop.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					lo.dismiss();
				}
			});
			sure.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});

			lo.show();
			break;
		case 8:
			Intent intent = new Intent(
					android.content.Intent.ACTION_VIEW,
					Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
			intent.setClassName("com.google.android.apps.maps",
					"com.google.android.maps.MapsActivity");
			startActivity(intent);
			break;
		}
		ft.commit();
		mDrawerList.setItemChecked(position, true);
		// Close drawer
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	// Creating AsynTask for Updating the Database
	private class UpdateDB extends AsyncTask<String, String, String> {

		private String resp = "";

		@Override
		protected String doInBackground(String... params) {
			// publishProgress("Reading Data..."); // Calls onProgressUpdate()
			String url = "http://www.toolittletoobig.com/guruchela_php/updateUserPassword_gc.php";
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);

			try {
				// Add your data

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs
						.add(new BasicNameValuePair("username", params[0]));
				nameValuePairs
						.add(new BasicNameValuePair("password", params[1]));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				HttpResponse response = httpclient.execute(httppost);

				// Reading data from insert.php
				InputStream is = response.getEntity().getContent();

				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));

				resp = br.readLine();
				// Execute HTTP Post Request

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				resp = "Error:" + e.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				resp = "Error:" + e.toString();
			}
			return resp;
		}

		@Override
		protected void onPostExecute(String result) {
			// execution of result of Long time consuming operation
			// tvDisplay.setText(result);
			Toast.makeText(NavigationDrawer.this,
					"Password is updated succesfully!", Toast.LENGTH_LONG)
					.show();
		}

		@Override
		protected void onPreExecute() {
			// Things to be done before execution of long running operation. For
			// example showing ProgessDialog
		}

		@Override
		protected void onProgressUpdate(String... text) {
			// tvAll.setText(text[0]);
			Toast.makeText(NavigationDrawer.this,
					"Password update in progress!", Toast.LENGTH_LONG).show();
			// Things to be done while execution of long running operation is in
			// progress. For example updating ProgessDialog
		}

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		new AlertDialog.Builder(this)
				.setIcon(R.drawable.alerts_and_states_warning)
				.setTitle("closing Activity")
				.setMessage("Are You Sure You want To exit?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								finish();
							}
						}).setNegativeButton("No", null).show();
	}

}
