package olectronix.hottie.config;

import java.util.ArrayList;

import olectronix.hottie.R;
import olectronix.hottie.SMSHandler;
import olectronix.hottie.R.string;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
	SharedPreferences syncPref;
	SharedPreferences generalPref;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_heater_ignition);
		// Show the Up button in the action bar.
		setupActionBar();
		generalPref = PreferenceManager.getDefaultSharedPreferences(this);
		syncPref = this.getSharedPreferences("syncPrefs", Context.MODE_PRIVATE);
		String htignMode = syncPref.getString("htignMode", "0");
		String htignSensor = syncPref.getString("htignSensor", "0");
		String htignThL = syncPref.getString("htignThL", "0");
		String htignThH = syncPref.getString("htignThH", "0");

		spinner = (Spinner) findViewById(R.id.heaterign_modes_spinner);
		sensorsSpinner = (Spinner) findViewById(R.id.heaterign_sensors_spinner);
		lowTHEditText = (EditText) findViewById(R.id.heaterign_low_treshold);
		highTHEditText = (EditText) findViewById(R.id.heaterign_high_treshold);
		sensorTextView = (TextView) findViewById(R.id.heaterign_sensor_selection_text);
		modeNote = (TextView) findViewById(R.id.heater_mode_note);
		saveButton = (Button) findViewById(R.id.button_heaterign_save);
		tresholdsNoteTextView = (TextView) findViewById(R.id.heaterign_temp_treshold_note);
		tresholdTextView = (TextView) findViewById(R.id.heaterign_temp_tresholds_text);

		// The order of these arrays is important. Please do not change
		ArrayList<String> spinnerArrayList = new ArrayList<String>();
		spinnerArrayList.add(getResources().getString(
				R.string.heaterignModeOFFText));
		spinnerArrayList.add(getResources().getString(
				R.string.heaterignModeONText));
		spinnerArrayList.add(getResources().getString(
				R.string.heaterignModeSensorText));
		spinnerArrayList.add(getResources().getString(
				R.string.heaterignModeIndependentText));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.heaterign_mode_spinner_layout);
		adapter.addAll(spinnerArrayList);
		// Specify the layout to use when the list of choices appears
		spinner.setAdapter(adapter);
		spinner.setSelection(Integer.parseInt(htignMode));

		// The order of these arrays is important. Please do not change
		ArrayList<String> sensorsSpinnerArrayList = new ArrayList<String>();
		sensorsSpinnerArrayList.add(getResources().getString(
				R.string.sensor_WTT_temperature_sensor));
		sensorsSpinnerArrayList.add(getResources().getString(
				R.string.sensor_exterior_temperature_sensor));
		sensorsSpinnerArrayList.add(getResources().getString(
				R.string.sensor_interior_temperature_sensor));
		sensorsSpinnerArrayList.add(getResources().getString(
				R.string.sensor_HOTTIE_system_temperature_sensor));

		ArrayAdapter<String> sensorSpinnerAdapter = new ArrayAdapter<String>(
				this, R.layout.heaterign_mode_spinner_layout);
		sensorSpinnerAdapter.addAll(sensorsSpinnerArrayList);
		// Specify the layout to use when the list of choices appears
		sensorsSpinner.setAdapter(sensorSpinnerAdapter);
		switch (Integer.parseInt(htignSensor)) {
		case 1:
			sensorsSpinner.setSelection(0);
			break;
		case 2:
			sensorsSpinner.setSelection(1);
			break;
		case 4:
			sensorsSpinner.setSelection(2);
			break;
		case 8:
			sensorsSpinner.setSelection(3);
			break;
		default:
			sensorsSpinner.setSelection(0);
		}
		lowTHEditText.setText(htignThL);
		highTHEditText.setText(htignThH);

		lowTHEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String textString = s.toString();
				String otherEditView = highTHEditText.getText().toString();
				if (textString.equals("")
						|| textString.equals("-")
						|| Integer.parseInt(textString) < -50
						|| Integer.parseInt(textString) > 100
						|| otherEditView.equals("")
						|| Integer.parseInt(otherEditView) < -50
						|| Integer.parseInt(otherEditView) > 100
						|| Integer.parseInt(textString) > Integer
								.parseInt(otherEditView)) {
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
						|| Integer.parseInt(textString) < Integer
								.parseInt(otherEditView)) {
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
		final SharedPreferences.Editor editor = generalPref.edit();
		SMSHandler handler = new SMSHandler(this);
		String command = "";
		if (selectedMode.equals(getResources().getString(
				R.string.heaterignModeOFFText))) {
			command = String.format(
					getResources().getString(R.string.setHeaterignMode), 0);
			if (handler.sendSMS(command))
				if (!generalPref.getBoolean("hottie_credit_checkbox", false)) {
					editor.putString("htignMode", "0");
					editor.commit();
				} else
					waitForAnswer();
		}
		if (selectedMode.equals(getResources().getString(
				R.string.heaterignModeONText))) {
			command = String.format(
					getResources().getString(R.string.setHeaterignMode), 1);
			if (handler.sendSMS(command))
				if (!generalPref.getBoolean("hottie_credit_checkbox", false)) {
					editor.putString("htignMode", "1");
					editor.commit();
				} else
					waitForAnswer();
		}
		if (selectedMode.equals(getResources().getString(
				R.string.heaterignModeIndependentText))) {
			command = String.format(
					getResources().getString(R.string.setHeaterignMode), 3);
			if (handler.sendSMS(command))
				if (!generalPref.getBoolean("hottie_credit_checkbox", false)) {
					editor.putString("htignMode", "3");
					editor.commit();
				} else
					waitForAnswer();
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
				if (handler.sendSMS(command))
					if (!generalPref.getBoolean("hottie_credit_checkbox", false)) {
						editor.putString("htignMode", "2");
						editor.putString("htignSensor", "1");
						editor.putString("htignThL", lowTh);
						editor.putString("htignThH", highTh);
						editor.commit();
					} else
						waitForAnswer();
			}
			if (selectedSensor.equals(getResources().getString(
					R.string.sensor_exterior_temperature_sensor))) {
				command = String.format(
						getResources().getString(
								R.string.setHeaterignModeSensor), 2, 2, lowTh,
						highTh);
				if (handler.sendSMS(command))
					if (!generalPref.getBoolean("hottie_credit_checkbox", false)) {
						editor.putString("htignMode", "2");
						editor.putString("htignSensor", "2");
						editor.putString("htignThL", lowTh);
						editor.putString("htignThH", highTh);
						editor.commit();
					} else
						waitForAnswer();
			}
			if (selectedSensor.equals(getResources().getString(
					R.string.sensor_interior_temperature_sensor))) {
				command = String.format(
						getResources().getString(
								R.string.setHeaterignModeSensor), 2, 4, lowTh,
						highTh);
				if (handler.sendSMS(command))
					if (!generalPref.getBoolean("hottie_credit_checkbox", false)) {
						editor.putString("htignMode", "2");
						editor.putString("htignSensor", "4");
						editor.putString("htignThL", lowTh);
						editor.putString("htignThH", highTh);
						editor.commit();
					} else
						waitForAnswer();
			}
			if (selectedSensor.equals(getResources().getString(
					R.string.sensor_HOTTIE_system_temperature_sensor))) {
				command = String.format(
						getResources().getString(
								R.string.setHeaterignModeSensor), 2, 8, lowTh,
						highTh);
				if (handler.sendSMS(command))
					if (!generalPref.getBoolean("hottie_credit_checkbox", false)) {
						editor.putString("htignMode", "2");
						editor.putString("htignSensor", "8");
						editor.putString("htignThL", lowTh);
						editor.putString("htignThH", highTh);
						editor.commit();
					} else
						waitForAnswer();
			}
		}
	}

	private void waitForAnswer() {
		// TODO Auto-generated method stub

	}
}
