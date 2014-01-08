package olectronix.hottie.config;

import java.text.DecimalFormat;
import java.util.ArrayList;

import olectronix.hottie.R;
import olectronix.hottie.SMSHandler;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class ConfigOutputFragment extends Fragment {

	public ConfigOutputFragment(){}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		View fragmentView = inflater.inflate(R.layout.fragment_config_output, container, false);
		final Activity parentActivity = getActivity();
		GridLayout gridLayout= (GridLayout) fragmentView;
        WindowManager wm = (WindowManager) parentActivity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        LayoutParams params = gridLayout.getLayoutParams();
        params.height = height;
        params.width = width;
        final SeekBar maxTimeSeekBar =(SeekBar) fragmentView.findViewById(R.id.output_max_time_seek);
        LayoutParams seekBarParams = maxTimeSeekBar.getLayoutParams();
        seekBarParams.width=width-295;
        final EditText nameEditText = (EditText) fragmentView.findViewById(R.id.output_name_edit_text);
        nameEditText.getLayoutParams().width = width - 240;
        final Spinner outputModeSpinner = (Spinner) fragmentView.findViewById(R.id.output_mode_spinner);
        outputModeSpinner.getLayoutParams().width = width - 240;
        final Spinner outputSensorSpinner = (Spinner) fragmentView.findViewById(R.id.output_sensor_spinner);
        outputSensorSpinner.getLayoutParams().width = width - 240;
        
        final Bundle args = getArguments();
        nameEditText.setText(args.getString("name", "output"));
        // Populating the spinners
        // The order of this items is important. Please don't change
        ArrayList<String> spinnerArrayList = new ArrayList<String>();
		spinnerArrayList.add(getResources().getString(
				R.string.output_mode_never));
		spinnerArrayList.add(getResources().getString(
				R.string.output_mode_sms_request));
		spinnerArrayList.add(getResources().getString(
				R.string.output_mode_auto));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(parentActivity,
				R.layout.heaterign_mode_spinner_layout);
		adapter.addAll(spinnerArrayList);
		// Specify the layout to use when the list of choices appears
		outputModeSpinner.setAdapter(adapter);
		
		ArrayList<String> sensorSpinnerArrayList = new ArrayList<String>();
		sensorSpinnerArrayList.add(getResources().getString(
				R.string.sensor_WTT_temperature_sensor));
		sensorSpinnerArrayList.add(getResources().getString(
				R.string.sensor_exterior_temperature_sensor));
		sensorSpinnerArrayList.add(getResources().getString(
				R.string.sensor_interior_temperature_sensor));
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(parentActivity,
				R.layout.heaterign_mode_spinner_layout);
		adapter2.addAll(sensorSpinnerArrayList);
		// Specify the layout to use when the list of choices appears
		outputSensorSpinner.setAdapter(adapter2);
        
        final CheckBox indepCheckBox = (CheckBox) fragmentView.findViewById(R.id.output_indep_check_box);
        final SeekBar onTempSeekBar = (SeekBar) fragmentView.findViewById(R.id.output_on_temp_seek);
        final SeekBar offTempSeekBar = (SeekBar) fragmentView.findViewById(R.id.output_off_temp_seek);
        final SeekBar offVoltageSeekBar = (SeekBar) fragmentView.findViewById(R.id.output_off_voltage_seek);
        final SeekBar offCurrentSeekBar = (SeekBar) fragmentView.findViewById(R.id.output_off_current_seek);
        final SeekBar maxIntensitySeekBar = (SeekBar) fragmentView.findViewById(R.id.output_max_intensity_seek);
        final SeekBar minIntensitySeekBar = (SeekBar) fragmentView.findViewById(R.id.output_min_intensity_seek);
        Button outputSaveSettingsButton = (Button) fragmentView.findViewById(R.id.output_save_button);
        
        final TextView maxTimeTextViewIndicator = (TextView) fragmentView.findViewById(R.id.output_max_time_indicator);
        final TextView onTempTextViewIndicator = (TextView) fragmentView.findViewById(R.id.output_on_temp_indicator);
        final TextView offTempTextViewIndicator = (TextView) fragmentView.findViewById(R.id.output_off_temp_indicator);
        final TextView offVoltageTextViewIndicator = (TextView) fragmentView.findViewById(R.id.output_off_voltage_indicator);
        final TextView offCurrentTextViewIndicator = (TextView) fragmentView.findViewById(R.id.output_off_current_indicator);
        final TextView maxIntensityTextViewIndicator = (TextView) fragmentView.findViewById(R.id.output_max_intensity_indicator);
        final TextView minIntensityTextViewIndicator = (TextView) fragmentView.findViewById(R.id.output_min_intensity_indicator);
        
        outputSaveSettingsButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				SMSHandler handler = new SMSHandler(parentActivity);
				Integer indep = indepCheckBox.isChecked()? 1:0;
				String sensorValue="";
				if (outputSensorSpinner.getSelectedItemPosition()==0)
					sensorValue="1";
				else if (outputSensorSpinner.getSelectedItemPosition()==1)
					sensorValue="2";
				else
					sensorValue="4";
				String command = args.getString("name", "output") + " set:" +
				indep.toString() + "," + 
				String.valueOf(outputModeSpinner.getSelectedItemPosition()) + "," +
				String.valueOf(maxTimeSeekBar.getProgress()) + "," +
				sensorValue + "," +
				String.valueOf(onTempSeekBar.getProgress()) + "," +
				String.valueOf(offTempSeekBar.getProgress()) + "," +
				String.valueOf(offVoltageSeekBar.getProgress()) + "," +
				String.valueOf(maxIntensitySeekBar.getProgress()) + "," +
				String.valueOf(minIntensitySeekBar.getProgress()) + "," +
				nameEditText.getText().toString() + "," +
				String.valueOf(offCurrentSeekBar.getProgress())
				;
				handler.sendSMS(command);
			}}
			);
       
        maxTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				maxTimeTextViewIndicator.setText(String.valueOf(arg1)
						+ "min");
			}
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}});
        
        onTempSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				onTempTextViewIndicator.setText(String.valueOf(arg1-30)
						+ "C");
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}});
        
        offTempSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				offTempTextViewIndicator.setText(String.valueOf(arg1-30)
						+ "C");
			}
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}});
        
        offVoltageSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				DecimalFormat df = new DecimalFormat("##.#");
				offVoltageTextViewIndicator.setText(df.format((float)arg1/1000)
						+ "V");
			}
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}});
        
        offCurrentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				DecimalFormat df = new DecimalFormat("##.#");
				offCurrentTextViewIndicator.setText(df.format((float)arg1/1000)
						+ "A");
			}
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}});
        
        maxIntensitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				maxIntensityTextViewIndicator.setText(String.valueOf(arg1)
						+ "%");
			}
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}});
        
        minIntensitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				minIntensityTextViewIndicator.setText(String.valueOf(arg1)
						+ "%");
			}
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}});
        
        return fragmentView;
    }

}
