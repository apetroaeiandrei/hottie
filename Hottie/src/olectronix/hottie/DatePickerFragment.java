package olectronix.hottie;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	public static final String START_OR_END = "type";
	private Activity parentActivity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		parentActivity = activity;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker or one saved
		SharedPreferences sharedPref = parentActivity.getSharedPreferences(
				"syncPrefs", Context.MODE_PRIVATE);
		Bundle args = getArguments();
		String date;
		if (args.getInt(START_OR_END) == 0)
			date = sharedPref.getString("reportGraphStart", "null");
		else
			date = sharedPref.getString("reportGraphStop", "null");
		int year;
		int month;
		int day;
		if (date.equals("null")) {
			final Calendar c = Calendar.getInstance();
			year = c.get(Calendar.YEAR);
			month = c.get(Calendar.MONTH);
			day = c.get(Calendar.DAY_OF_MONTH);
		} else {
			final String[] parts = date.split("\\.");
			year = Integer.parseInt(parts[0]);
			month = Integer.parseInt(parts[1]);
			day = Integer.parseInt(parts[2]);
		}

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		// Saves Start and End time to SharedPreferences file
		SharedPreferences sharedPref = parentActivity.getSharedPreferences(
				"syncPrefs", Context.MODE_PRIVATE);
		final SharedPreferences.Editor editor = sharedPref.edit();
		Bundle args = getArguments();
		if (args.getInt(START_OR_END) == 0)
			editor.putString("reportGraphStart", String.valueOf(year) + "."
					+ String.valueOf(month) + "." + String.valueOf(day));
		else
			editor.putString("reportGraphStop", String.valueOf(year) + "."
					+ String.valueOf(month) + "." + String.valueOf(day));
		editor.commit();
	}
}