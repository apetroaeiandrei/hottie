package olectronix.hottie.config;

import java.text.DecimalFormat;

import olectronix.hottie.R;
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
		Activity parentActivity = getActivity();
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
        SeekBar maxTimeSeekBar =(SeekBar) fragmentView.findViewById(R.id.output_max_time_seek);
        LayoutParams seekBarParams = maxTimeSeekBar.getLayoutParams();
        seekBarParams.width=width-295;
        EditText nameEditText = (EditText) fragmentView.findViewById(R.id.output_name_edit_text);
        nameEditText.getLayoutParams().width = width - 240;
        Spinner outputModeSpinner = (Spinner) fragmentView.findViewById(R.id.output_mode_spinner);
        outputModeSpinner.getLayoutParams().width = width - 240;
        Spinner outputSensorSpinner = (Spinner) fragmentView.findViewById(R.id.output_sensor_spinner);
        outputSensorSpinner.getLayoutParams().width = width - 240;
        
        SeekBar onTempSeekBar = (SeekBar) fragmentView.findViewById(R.id.output_on_temp_seek);
        SeekBar offTempSeekBar = (SeekBar) fragmentView.findViewById(R.id.output_off_temp_seek);
        SeekBar offVoltageSeekBar = (SeekBar) fragmentView.findViewById(R.id.output_off_voltage_seek);
        SeekBar offCurrentSeekBar = (SeekBar) fragmentView.findViewById(R.id.output_off_current_seek);
        SeekBar maxIntensitySeekBar = (SeekBar) fragmentView.findViewById(R.id.output_max_intensity_seek);
        SeekBar minIntensitySeekBar = (SeekBar) fragmentView.findViewById(R.id.output_min_intensity_seek);
        
        final TextView maxTimeTextViewIndicator = (TextView) fragmentView.findViewById(R.id.output_max_time_indicator);
        final TextView onTempTextViewIndicator = (TextView) fragmentView.findViewById(R.id.output_on_temp_indicator);
        final TextView offTempTextViewIndicator = (TextView) fragmentView.findViewById(R.id.output_off_temp_indicator);
        final TextView offVoltageTextViewIndicator = (TextView) fragmentView.findViewById(R.id.output_off_voltage_indicator);
        final TextView offCurrentTextViewIndicator = (TextView) fragmentView.findViewById(R.id.output_off_current_indicator);
        final TextView maxIntensityTextViewIndicator = (TextView) fragmentView.findViewById(R.id.output_max_intensity_indicator);
        final TextView minIntensityTextViewIndicator = (TextView) fragmentView.findViewById(R.id.output_min_intensity_indicator);
       
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
