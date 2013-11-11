package olectronix.hottie.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import olectronix.hottie.R;
import olectronix.hottie.SMSHandler;
import olectronix.hottie.R.string;
import android.R.bool;
import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class ConfigMenuAdapter extends BaseAdapter {

	private static ArrayList<ConfigMenuItem> menusArrayList;
	private LayoutInflater mInflater;
	private Context currentContext;

	public ConfigMenuAdapter(Context context, ArrayList<ConfigMenuItem> menus) {
		currentContext = context;
		menusArrayList = menus;
		mInflater = LayoutInflater.from(context);
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
				public void onClick(View v) {
					if (holderFinal.textView
							.getText()
							.toString()
							.equals(currentContext.getResources().getString(
									R.string.setTimeText))) {
						SMSHandler handler = new SMSHandler(currentContext);
						Calendar c = Calendar.getInstance();
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
							.equals(currentContext.getResources().getString(
									R.string.setDefaultText))) {
						SMSHandler handler = new SMSHandler(currentContext);
						String command = currentContext.getResources()
								.getString(R.string.setDefaultCommand);
						handler.sendSMS(command);
					}
					if (holderFinal.textView
							.getText()
							.toString()
							.equals(currentContext.getResources().getString(
									R.string.saveSettingsText))) {
						SMSHandler handler = new SMSHandler(currentContext);
						String command = "";
						for (ConfigMenuItem item : menusArrayList) {
							// Ignition Detection Time command
							if (item.textView
									.getText()
									.toString()
									.equals(currentContext.getResources()
											.getString(
													R.string.setIgnUpdateText))) {
								command = String
										.format(currentContext
												.getResources()
												.getString(
														R.string.setIgnUpdateCommand),
												item.seek_bar1.getProgress());
								handler.sendSMS(command);
							}

							// Light Timeout command
							if (item.textView
									.getText()
									.toString()
									.equals(currentContext
											.getResources()
											.getString(
													R.string.setLightTimeoutText))) {
								command = String
										.format(currentContext
												.getResources()
												.getString(
														R.string.setLightTimeoutCommand),
												item.seek_bar1.getProgress());
								handler.sendSMS(command);
							}
							// Error notification (WTT)
							if (item.textView
									.getText()
									.toString()
									.equals(currentContext
											.getResources()
											.getString(
													R.string.setWttErrorNoteText))) {
								int state;
								if (item.switch1.isChecked())
									state = 1;
								else
									state = 0;

								command = String
										.format(currentContext
												.getResources()
												.getString(
														R.string.setWttErrorNoteCommand),
												state);
								handler.sendSMS(command);
							}
							// Error notification (COM)
							if (item.textView
									.getText()
									.toString()
									.equals(currentContext
											.getResources()
											.getString(
													R.string.setComErrorNoteText))) {
								int state;
								if (item.switch1.isChecked())
									state = 1;
								else
									state = 0;

								command = String
										.format(currentContext
												.getResources()
												.getString(
														R.string.setComErrorNoteCommand),
												state);
								handler.sendSMS(command);
							}
							
							// Error notification (LED)
							if (item.textView
									.getText()
									.toString()
									.equals(currentContext
											.getResources()
											.getString(
													R.string.setLedText))) {
								int state;
								if (item.switch1.isChecked())
									state = 1;
								else
									state = 0;

								command = String
										.format(currentContext
												.getResources()
												.getString(
														R.string.setLedCommand),
												state);
								handler.sendSMS(command);
							}

						}
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

		for (ConfigMenuItem item : menusArrayList) {
			String itemText = item.getText().toString();
			String holderText = holder.textView.getText().toString();
			if (itemText.equals(holderText)) {
				item.assignObject(holder);
			}

		}
		return convertView;
	}
}
