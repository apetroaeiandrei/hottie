package olectronix.hottie.general.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import olectronix.hottie.general.fragments.DatePickerFragment;
import olectronix.hottie.R;

public class ReportGraphActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_graph);

		Spinner seriesSpinner = (Spinner) findViewById(R.id.graph_serie_spinner);
		// Populating the spinners
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.graph_series_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		seriesSpinner.setAdapter(adapter);

		// Open SharedPreferences file
		SharedPreferences sharedPref = this.getSharedPreferences("syncPrefs",
				Context.MODE_PRIVATE);
		final SharedPreferences.Editor editor = sharedPref.edit();
		seriesSpinner.setSelection(sharedPref.getInt("reportGraphSerie", 0));

		seriesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				editor.putInt("reportGraphSerie", position);
				editor.commit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.report_graph, menu);
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

	public void setDurationStart(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		Bundle args = new Bundle();
		args.putInt(DatePickerFragment.START_OR_END, 0);
		newFragment.setArguments(args);
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	public void setDurationStop(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		Bundle args = new Bundle();
		args.putInt(DatePickerFragment.START_OR_END, 1);
		newFragment.setArguments(args);
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}
}
