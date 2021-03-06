package olectronix.hottie.config.activities;

import java.util.ArrayList;

import olectronix.hottie.R;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import olectronix.hottie.config.adapters.ConfigOutputsAdapter;
import olectronix.hottie.config.ConfigOutputsItem;

public class ConfigOutputsActivity extends FragmentActivity {
	private ListView lv;
	private SharedPreferences syncPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configure_outputs);
		// Show the Up button in the action bar.
		setupActionBar();
		syncPref = getSharedPreferences("syncPrefs", Context.MODE_PRIVATE);
		lv = (ListView) findViewById(R.id.outputs_config_list_view);

		ArrayList<ConfigOutputsItem> your_array_list = populateConfigMenu();
		lv.setAdapter(new ConfigOutputsAdapter(this, your_array_list));
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.configure_outputs, menu);
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

	private ArrayList<ConfigOutputsItem> populateConfigMenu() {
		// Check if hottie device is able to respond via SMS
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(this);
		boolean responseEnabled = sharedPref.getBoolean(
				"hottie_credit_checkbox", false);
		boolean syncOk = syncPref.getBoolean("syncOK", false);
		

		// ArrayList used to populate the ListView with outputs
		ArrayList<ConfigOutputsItem> result = new ArrayList<ConfigOutputsItem>();

		if (!syncOk) {
			ConfigOutputsItem configOutputsItem = new ConfigOutputsItem();
			configOutputsItem.setOutputName(getResources().getString(
					R.string.default_output_5));
			result.add(configOutputsItem);

			configOutputsItem = new ConfigOutputsItem();
			configOutputsItem.setOutputName(getResources().getString(
					R.string.default_output_6));
			result.add(configOutputsItem);
		} else {
			ConfigOutputsItem configOutputsItem;
			for (int i=0;i<6;i++)
			{
				int outputType = syncPref.getInt("outputType"+(i+1), 0);
				if (outputType!=0)
				{
				configOutputsItem = new ConfigOutputsItem();
				configOutputsItem.setOutputName(syncPref.getString("output"+(i+1), "N/A"));
				configOutputsItem.setOutputType(outputType);
				result.add(configOutputsItem);
				}
			}
		}
		return result;
	}

}
