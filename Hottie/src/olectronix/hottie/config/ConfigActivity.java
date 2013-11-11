package olectronix.hottie.config;

import java.util.ArrayList;

import olectronix.hottie.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

@SuppressLint("NewApi")
public class ConfigActivity extends Activity {

	// This is the Adapter being used to display the list's data
	SimpleCursorAdapter mAdapter;
	private ListView lv;
	private Context context = this;
	ArrayList<ConfigMenuItem> lvItems;

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		// Show the Up button in the action bar.
		setupActionBar();

		lv = (ListView) findViewById(R.id.config_list_view);
		// Instanciating an array list (you don't need to do this, you already
		// have yours)
		ArrayList<ConfigMenuItem> your_array_list = new ArrayList<ConfigMenuItem>();
		your_array_list =populateConfigMenu();
		lvItems=your_array_list;
		
		lv.setAdapter(new ConfigMenuAdapter(this, your_array_list));
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ConfigMenuItem selectedItem =(ConfigMenuItem) (lv.getItemAtPosition(position));
				String selectedFromList = selectedItem.textView.getText().toString();

				if (selectedFromList.equals(getResources().getString(
						R.string.registerPhoneText))) {
					Intent intent = new Intent(context,
							RegisterPhoneActivity.class);
					startActivity(intent);
				}
			}
		});
	}
	
	private ArrayList<ConfigMenuItem> populateConfigMenu(){
		ArrayList<ConfigMenuItem> result = new ArrayList<ConfigMenuItem>();
		ConfigMenuItem configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.registerPhoneText));
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setHeaterIgnText));
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setOutputsText));
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setTypeText));
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setIgnUpdateText));
		configMenuItem.setSeek_bar_visibility(View.VISIBLE);
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setLightTimeoutText));
		configMenuItem.setSeek_bar_visibility(View.VISIBLE);
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setWttErrorNoteText));
		configMenuItem.setSwitch_visibility(View.VISIBLE);
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setComErrorNoteText));
		configMenuItem.setSwitch_visibility(View.VISIBLE);
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setLedText));
		configMenuItem.setSwitch_visibility(View.VISIBLE);
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setTimeText));
		configMenuItem.setButton_go_visibility(View.VISIBLE);
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setDefaultText));
		configMenuItem.setButton_go_visibility(View.VISIBLE);
		result.add(configMenuItem);	
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.saveSettingsText));
		configMenuItem.setButton_go_visibility(View.VISIBLE);
		result.add(configMenuItem);	
		return result;
	}
	
}
