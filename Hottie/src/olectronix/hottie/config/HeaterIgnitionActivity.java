package olectronix.hottie.config;

import java.util.ArrayList;

import olectronix.hottie.R;
import olectronix.hottie.SMSHandler;
import olectronix.hottie.R.string;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class HeaterIgnitionActivity extends Activity {
	Spinner spinner;
	Spinner sensorsSpinner;
	EditText lowTHEditText;
	EditText highTHEditText;
	TextView sensorTextView;
	TextView modeNote;
	Button saveButton;
	TextView tresholdsNoteTextView;
	TextView tresholdTextView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_heater_ignition);
		// Show the Up button in the action bar.
		setupActionBar();
		spinner = (Spinner) findViewById(R.id.heaterign_modes_spinner);
		sensorsSpinner = (Spinner) findViewById(R.id.heaterign_sensors_spinner);
		lowTHEditText = (EditText) findViewById(R.id.heaterign_low_treshold);
		highTHEditText = (EditText) findViewById(R.id.heaterign_high_treshold);
		sensorTextView = (TextView) findViewById(R.id.heaterign_sensor_selection_text);
		modeNote = (TextView) findViewById(R.id.heater_mode_note);
		saveButton = (Button) findViewById(R.id.button_heaterign_save);
		tresholdsNoteTextView = (TextView) findViewById(R.id.heaterign_temp_treshold_note);
		tresholdTextView = (TextView) findViewById(R.id.heaterign_temp_tresholds_text);

		ArrayList<String> spinnerArrayList = new ArrayList<String>();
		spinnerArrayList.add(getResources().getString(
				R.string.heaterignModeOFFText));
		spinnerArrayList.add(getResources().getString(
				R.string.heaterignModeONText));
		spinnerArrayList.add(getResources().getString(
				R.string.heaterignModeIndependentText));
		spinnerArrayList.add(getResources().getString(
				R.string.heaterignModeSensorText));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.heaterign_mode_spinner_layout);
		adapter.addAll(spinnerArrayList);
		// Specify the layout to use when the list of choices appears
		spinner.setAdapter(adapter);

		ArrayList<String> sensorsSpinnerArrayList = new ArrayList<String>();
		sensorsSpinnerArrayList.add(getResources().getString(
				R.string.sensor_WTT_temperature_sensor));
		sensorsSpinnerArrayList.add(getResources().getString(
				R.string.sensor_interior_temperature_sensor));
		sensorsSpinnerArrayList.add(getResources().getString(
				R.string.sensor_exterior_temperature_sensor));
		sensorsSpinnerArrayList.add(getResources().getString(
				R.string.sensor_HOTTIE_system_temperature_sensor));

		ArrayAdapter<String> sensorSpinnerAdapter = new ArrayAdapter<String>(
				this, R.layout.heaterign_mode_spinner_layout);
		sensorSpinnerAdapter.addAll(sensorsSpinnerArrayList);
		// Specify the layout to use when the list of choices appears
		sensorsSpinner.setAdapter(sensorSpinnerAdapter);

		lowTHEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String textString = s.toString();
				String otherEditView =highTHEditText.getText().toString();
				if (textString.equals("") 
						|| textString.equals("-")
						|| Integer.parseInt(textString) < -50
						|| Integer.parseInt(textString) > 100
						|| otherEditView.equals("")
						|| Integer.parseInt(otherEditView) < -50
						|| Integer.parseInt(otherEditView) > 100
						|| Integer.parseInt(textString)>Integer.parseInt(otherEditView)) {
					saveButton.setEnabled(false);
					tresholdsNoteTextView.setVisibility(View.VISIBLE);
				} else {
					saveButton.setEnabled(true);
					tresholdsNoteTextView.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		highTHEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String textString = s.toString();
				String otherEditView = lowTHEditText.getText().toString();
				if (textString.equals("") 
						|| textString.equals("-")
						|| Integer.parseInt(textString) < -50
						|| Integer.parseInt(textString) > 100
						|| otherEditView.equals("")
						|| Integer.parseInt(otherEditView) < -50
						|| Integer.parseInt(otherEditView) > 100
						|| Integer.parseInt(textString)<Integer.parseInt(otherEditView)) {
					saveButton.setEnabled(false);
					tresholdsNoteTextView.setVisibility(View.VISIBLE);
				} else {
					saveButton.setEnabled(true);
					tresholdsNoteTextView.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				// your code here
				String selectedMode = ((TextView) selectedItemView).getText()
						.toString();
				if (!selectedMode.equals(getResources().getString(
						R.string.heaterignModeSensorText))) {
					sensorsSpinner.setVisibility(View.GONE);
					highTHEditText.setVisibility(View.GONE);
					lowTHEditText.setVisibility(View.GONE);
					sensorTextView.setVisibility(View.GONE);
					tresholdTextView.setVisibility(View.GONE);
					tresholdsNoteTextView.setVisibility(View.GONE);
					saveButton.setEnabled(true);
					if (selectedMode.equals(getResources().getString(
							R.string.heaterignModeOFFText)))
						modeNote.setText(getResources().getString(
								R.string.heaterign_modeOFF_note));
					if (selectedMode.equals(getResources().getString(
							R.string.heaterignModeONText)))
						modeNote.setText(getResources().getString(
								R.string.heaterign_modeON_note));
					if (selectedMode.equals(getResources().getString(
							R.string.heaterignModeIndependentText)))
						modeNote.setText(getResources().getString(
								R.string.heaterign_modeIndependent_note));
				} else {
					sensorsSpinner.setVisibility(View.VISIBLE);
					highTHEditText.setVisibility(View.VISIBLE);
					lowTHEditText.setVisibility(View.VISIBLE);
					sensorTextView.setVisibility(View.VISIBLE);
					tresholdTextView.setVisibility(View.VISIBLE);
					modeNote.setText(getResources().getString(
							R.string.heaterign_modeSensor_note));
					if (lowTHEditText.getText().toString().equals("")
							|| highTHEditText.getText().toString().equals("")) {
						saveButton.setEnabled(false);
						tresholdsNoteTextView.setVisibility(View.VISIBLE);
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}
		});
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
		getMenuInflater().inflate(R.menu.heater_ignition, menu);
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

	public void heaterignSaveSettings(View v) {
		String selectedMode = spinner.getSelectedItem().toString();

		SMSHandler handler = new SMSHandler(this);
		String command = "";
		if (selectedMode.equals(getResources().getString(
				R.string.heaterignModeOFFText))) {
			command = String.format(
					getResources().getString(R.string.setHeaterignMode), 0);
			handler.sendSMS(command);
		}
		if (selectedMode.equals(getResources().getString(
				R.string.heaterignModeONText))) {
			command = String.format(
					getResources().getString(R.string.setHeaterignMode), 1);
			handler.sendSMS(command);
		}
		if (selectedMode.equals(getResources().getString(
				R.string.heaterignModeIndependentText))) {
			command = String.format(
					getResources().getString(R.string.setHeaterignMode), 3);
			handler.sendSMS(command);
		}
		if (selectedMode.equals(getResources().getString(
				R.string.heaterignModeSensorText))) {
			String selectedSensor = sensorsSpinner.getSelectedItem().toString();
			String lowTh = lowTHEditText.getText().toString();
			String highTh = highTHEditText.getText().toString();

			if (selectedSensor.equals(getResources().getString(
					R.string.sensor_WTT_temperature_sensor))) {
				command = String.format(
						getResources().getString(
								R.string.setHeaterignModeSensor), 2, 1, lowTh,
						highTh);
				handler.sendSMS(command);
			}
			if (selectedSensor.equals(getResources().getString(
					R.string.sensor_exterior_temperature_sensor))) {
				command = String.format(
						getResources().getString(
								R.string.setHeaterignModeSensor), 2, 2, lowTh,
						highTh);
				handler.sendSMS(command);
			}
			if (selectedSensor.equals(getResources().getString(
					R.string.sensor_interior_temperature_sensor))) {
				command = String.format(
						getResources().getString(
								R.string.setHeaterignModeSensor), 2, 4, lowTh,
						highTh);
				handler.sendSMS(command);
			}
			if (selectedSensor.equals(getResources().getString(
					R.string.sensor_HOTTIE_system_temperature_sensor))) {
				command = String.format(
						getResources().getString(
								R.string.setHeaterignModeSensor), 2, 8, lowTh,
						highTh);
				handler.sendSMS(command);
			}
		}
	}
}
