package com.kec.guruchela;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class AndroidListViewCursorAdaptorActivity extends SherlockActivity {

	private CountriesDbAdapter dbHelper;
	private SimpleCursorAdapter dataAdapter;
	ActionMode mMode;
	long Lid;
	ListView listView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		dbHelper = new CountriesDbAdapter(this);
		dbHelper.open();

		// Clean all data
		// dbHelper.deleteAllCountries();
		// Add some data

		// Generate ListView from SQLite Database
		displayListView();

	}

	private void displayListView() {

		Cursor cursor = dbHelper.fetchAllCountries();

		// The desired columns to be bound
		String[] columns = new String[] { CountriesDbAdapter.KEY_DESCRIPTION,
				CountriesDbAdapter.KEY_VENUE, CountriesDbAdapter.KEY_TIME,
				CountriesDbAdapter.KEY_DATE };

		// the XML defined views which the data will be bound to
		int[] to = new int[] { R.id.code, R.id.name, R.id.continent,
				R.id.region, };

		// create the adapter using the cursor pointing to the desired data
		// as well as the layout information
		dataAdapter = new SimpleCursorAdapter(this, R.layout.country_info,
				cursor, columns, to, 0);

		 listView = (ListView) findViewById(R.id.listView1);
		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listView, View view,
					int position, long id) {

				mMode = startActionMode(new AnActionModeOfEpicProportions());

				Lid = id;

			}
		});

		
	}

	private final class AnActionModeOfEpicProportions implements
			ActionMode.Callback {
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// Used to put dark icons on light action bar

			menu.add("Delete!").setIcon(R.drawable.content_discard)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			// TODO Auto-generated method stub
			CountriesDbAdapter db = new CountriesDbAdapter(
					AndroidListViewCursorAdaptorActivity.this);
			db.open();
			db.deleteEntry(Lid);
			db.close();
			dataAdapter.notifyDataSetChanged();
			SimpleCursorAdapter dataAdapter1;
			Cursor cursor = dbHelper.fetchAllCountries();

			// The desired columns to be bound
			String[] columns = new String[] { CountriesDbAdapter.KEY_DESCRIPTION,
					CountriesDbAdapter.KEY_VENUE, CountriesDbAdapter.KEY_TIME,
					CountriesDbAdapter.KEY_DATE };

			// the XML defined views which the data will be bound to
			int[] to = new int[] { R.id.code, R.id.name, R.id.continent,
					R.id.region, };

			// create the adapter using the cursor pointing to the desired data
			// as well as the layout information
			dataAdapter1 = new SimpleCursorAdapter(AndroidListViewCursorAdaptorActivity.this, R.layout.country_info,
					cursor, columns, to, 0);

			 listView = (ListView) findViewById(R.id.listView1);
			// Assign adapter to ListView
			listView.setAdapter(dataAdapter1);

			//listView.setAdapter(dataAdapter);
			mode.finish();
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			// TODO Auto-generated method stub

		}

	}
}
