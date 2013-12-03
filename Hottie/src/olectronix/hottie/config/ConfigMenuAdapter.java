package olectronix.hottie.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import olectronix.hottie.R;
import olectronix.hottie.SMSHandler;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ConfigMenuAdapter extends BaseAdapter {

	private static ArrayList<ConfigMenuItem> menusArrayList;
	private LayoutInflater mInflater;
	private Context currentContext;
	private SharedPreferences syncPref;
	private long antiFlood = 0;

	public ConfigMenuAdapter(Context context, ArrayList<ConfigMenuItem> menus) {
		currentContext = context;
		menusArrayList = menus;
		mInflater = LayoutInflater.from(context);
		syncPref = currentContext.getSharedPreferences("syncPrefs",
				Context.MODE_PRIVATE);
	}

	@Override
	public int getCount() {
		return menusArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return menusArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ConfigMenuItem holder = new ConfigMenuItem();

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.config_row_template, null);

			holder.textView = (TextView) convertView
					.findViewById(R.id.config_line_textView);
			holder.switch1 = (Switch) convertView.findViewById(R.id.switch1);
			holder.seek_bar1 = (SeekBar) convertView
					.findViewById(R.id.seek_bar1);
			final TextView seek_bar_valueTextView = (TextView) convertView
					.findViewById(R.id.seek_bar_value_textView);
			seek_bar_valueTextView.setText(holder.seek_bar1.getProgress()
					+ "(s)");
			holder.seek_bar_valueTextView = seek_bar_valueTextView;
			holder.button_go = (Button) convertView
					.findViewById(R.id.button_set);
			final ConfigMenuItem holderFinal = holder;
			holder.button_go.setOnClickListener(new Button.OnClickListener() {
				@SuppressLint("SimpleDateFormat")
				public void onClick(View v) {
					Calendar c = Calendar.getInstance();
					long diff = 0;
					if (antiFlood == 0) {
						antiFlood = c.getTimeInMillis();
						diff = 2;
					} else
						diff = (c.getTimeInMillis() - antiFlood) / (60 * 1000)
								% 60;

					if (diff > 1) {
						if (holderFinal.textView
								.getText()
								.toString()
								.equals(currentContext.getResources()
										.getString(R.string.setTimeText))) {
							SMSHandler handler = new SMSHandler(currentContext);

							SimpleDateFormat format1 = new SimpleDateFormat(
									"dd-MM-yy");
							SimpleDateFormat format2 = new SimpleDateFormat(
									"hh:mm:ss");
							String date = format1.format(c.getTime());
							String time = format2.format(c.getTime());
							String command = String.format(
									currentContext.getResources().getString(
											R.string.setTimeCommand), time,
									c.get(Calendar.DAY_OF_WEEK), date);
							handler.sendSMS(command);
						}
						if (holderFinal.textView
								.getText()
								.toString()
								.equals(currentContext.getResources()
										.getString(R.string.setDefaultText))) {
							SMSHandler handler = new SMSHandler(currentContext);
							String command = currentContext.getResources()
									.getString(R.string.setDefaultCommand);
							handler.sendSMS(command);
						}
						if (holderFinal.textView
								.getText()
								.toString()
								.equals(currentContext.getResources()
										.getString(R.string.saveSettingsText))) {
							SMSHandler handler = new SMSHandler(currentContext);
							String command = "";
							int ignUpdateValue = 0;
							int lightTimeoutValue = 0;
							int wttErrNoteValue = 0;
							int comErrNoteValue = 0;
							int ledValue = 0;
							for (ConfigMenuItem item : menusArrayList) {
								// Ignition Detection Time command
								if (item.textView
										.getText()
										.toString()
										.equals(currentContext
												.getResources()
												.getString(
														R.string.setIgnUpdateText))) {
									ignUpdateValue = item.seek_bar1
											.getProgress();
								}
								// Light Timeout command
								if (item.textView
										.getText()
										.toString()
										.equals(currentContext
												.getResources()
												.getString(
														R.string.setLightTimeoutText))) {
									lightTimeoutValue = item.seek_bar1
											.getProgress();
								}
								// Error notification (WTT)
								if (item.textView
										.getText()
										.toString()
										.equals(currentContext
												.getResources()
												.getString(
														R.string.setWttErrorNoteText))) {
									if (item.switch1.isChecked())
										wttErrNoteValue = 1;
									else
										wttErrNoteValue = 0;
								}
								// Error notification (COM)
								if (item.textView
										.getText()
										.toString()
										.equals(currentContext
												.getResources()
												.getString(
														R.string.setComErrorNoteText))) {
									if (item.switch1.isChecked())
										comErrNoteValue = 1;
									else
										comErrNoteValue = 0;
								}

								// Error notification (LED)
								if (item.textView
										.getText()
										.toString()
										.equals(currentContext.getResources()
												.getString(R.string.setLedText))) {
									if (item.switch1.isChecked())
										ledValue = 1;
									else
										ledValue = 0;
								}
							}
							command = String.format(
									currentContext.getResources().getString(
											R.string.saveAllSettingsCommand),
									ignUpdateValue, lightTimeoutValue,
									wttErrNoteValue, comErrNoteValue, ledValue);
							handler.sendSMS(command);
						}
					} else {
						Toast.makeText(
								currentContext,
								currentContext.getResources().getString(
										R.string.flood_control_message),
								Toast.LENGTH_SHORT).show();
					}
				}
			});
			holder.seek_bar1
					.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
						@Override
						public void onProgressChanged(SeekBar arg0, int arg1,
								boolean arg2) {
							seek_bar_valueTextView.setText(String.valueOf(arg1)
									+ "(s)");
						}

						@Override
						public void onStartTrackingTouch(SeekBar arg0) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onStopTrackingTouch(SeekBar arg0) {
							// TODO Auto-generated method stub
						}
					});

			convertView.setTag(holder);
		} else {
			holder = (ConfigMenuItem) convertView.getTag();
		}

		holder.textView.setText(menusArrayList.get(position).getText());
		holder.switch1.setVisibility(menusArrayList.get(position)
				.getSwitch_visibility());
		holder.button_go.setVisibility(menusArrayList.get(position)
				.getButton_go_visibility());
		holder.seek_bar1.setVisibility(menusArrayList.get(position)
				.getSeek_bar_visibility());
		holder.seek_bar_valueTextView.setVisibility(menusArrayList
				.get(position).getSeek_bar_visibility());

		Boolean wttErr = (syncPref.getString("wtterr", "0").equals("1")) ? true
				: false;
		Boolean comErr = (syncPref.getString("comerr", "0").equals("1")) ? true
				: false;
		Boolean led = (syncPref.getString("led", "0").equals("1")) ? true
				: false;
		int ignTime = Integer.parseInt(syncPref.getString("ignTime", "0"));
		int lightTime = Integer.parseInt(syncPref.getString("lightTime", "0"));
		for (ConfigMenuItem item : menusArrayList) {
			String itemText = item.getText().toString();
			String holderText = holder.textView.getText().toString();

			if (itemText.equals(holderText)) {
				if (itemText.equals(currentContext.getResources().getString(
						R.string.setComErrorNoteText)))
					holder.switch1.setChecked(comErr);
				if (itemText.equals(currentContext.getResources().getString(
						R.string.setWttErrorNoteText)))
					holder.switch1.setChecked(wttErr);
				if (itemText.equals(currentContext.getResources().getString(
						R.string.setLedText)))
					holder.switch1.setChecked(led);
				if (itemText.equals(currentContext.getResources().getString(
						R.string.setIgnUpdateText)))
					holder.seek_bar1.setProgress(ignTime);
				if (itemText.equals(currentContext.getResources().getString(
						R.string.setLightTimeoutText)))
					holder.seek_bar1.setProgress(lightTime);
				item.assignObject(holder);
			}

		}
		return convertView;
	}
}
