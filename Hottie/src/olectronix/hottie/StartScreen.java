package olectronix.hottie;

import olectronix.hottie.config.ConfigActivity;
import olectronix.hottie.config.OnSmsReceivedListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class StartScreen extends Activity implements OnSmsReceivedListener {

	SMSHandler smsHandler = new SMSHandler(this);
	SMSReceiver smsReceiver = new SMSReceiver();
	ProgressDialog progressBar;
	private Handler progressBarHandler = new Handler();
	int progressBarStatus;
	final Context thisContext = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_screen);
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(smsReceiver, filter);
		smsReceiver.addSmsReceivedListener(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		unregisterReceiver(smsReceiver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent intent = new Intent(this, UserSettingsActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_sync:
			String message = getResources().getString(R.string.syncCommand);
			smsHandler.sendSMS(message);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onReportButtonClick(View view) {
		String message = getResources().getString(R.string.reportCommand);
		smsHandler.sendSMS(message);
	}

	public void onOnButtonClick(View viev) {
		String message = getResources().getString(R.string.onCommand);
		smsHandler.sendSMS(message);
	}

	public void onOffButtonClick(View view) {
		String message = getResources().getString(R.string.offCommand);
		smsHandler.sendSMS(message);
	}

	public void onConfigButtonClick(View view) {
		Intent intent = new Intent(this, ConfigActivity.class);
		startActivity(intent);
	}

	@Override
	public void onSMSReceived(String messageBody) {
		SharedPreferences sharedPref = this.getSharedPreferences("syncPrefs",
				Context.MODE_PRIVATE);
		final SharedPreferences.Editor editor = sharedPref.edit();
		if (messageBody.contains(getResources()
				.getString(R.string.syncResponse))) {
			final String[] parts = messageBody.split(";");
			// prepare for a progress bar dialog
			progressBar = new ProgressDialog(thisContext);
			progressBar.setCancelable(true);
			progressBar.setMessage("Syncing settings");
			progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressBar.setProgress(0);
			progressBar.setMax(9);
			progressBar.show();

			// reset progress bar status
			progressBarStatus = 0;

			new Thread(new Runnable() {
				public void run() {
					for (int i = 1; i <= 9; i++) {
						// process some tasks
						progressBarStatus = saveSyncSettings(parts[i],i);

						// your computer is too fast, sleep 1 second
						try {
							Thread.sleep(400);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						// Update the progress bar
						progressBarHandler.post(new Runnable() {
							public void run() {
								progressBar.setProgress(progressBarStatus);
							}
						});
					}

					if (progressBarStatus >= 9) {
						// sleep 2 seconds, so that you can see the 100%
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						// close the progress bar dialog
						progressBar.dismiss();
						editor.putBoolean("syncOK", true);
						editor.commit();
					}
				}

				private int saveSyncSettings(String command, int i) {
					String[] args = command.split(",");
					switch (i) {
					case 1:
						editor.putString("registeredPhone1", args[0]);
						editor.putString("registeredPhone2", args[1]);
						editor.putString("registeredPhone3", args[2]);
						editor.commit();
						return 1;
						
					
					case 2:
						editor.putString("htignMode", args[0]);
						editor.putString("htignSensor", args[1]);
						editor.putString("htignThL", args[2]);
						editor.putString("htignThH", args[3]);
						editor.commit();
						return 2;
					
					case 3:
						editor.putString("output1", args[0]);
						editor.putString("output2", args[1]);
						editor.putString("output3", args[2]);
						editor.putString("output4", args[3]);
						editor.putString("output5", args[4]);
						editor.putString("output6", args[5]);
						editor.commit();
						return 3;
					
					case 4:
						editor.putString("heaterType", args[0]);
						editor.commit();
						return 4;
					
					case 5:
						editor.putString("ignTime", args[0]);
						editor.commit();
						return 5;
					
					case 6:
						editor.putString("lightTime", args[0]);
						editor.commit();
						return 6;
					
					case 7:
						editor.putString("wtterr", args[0]);
						editor.commit();
						return 7;
					
					case 8:
						editor.putString("comerr", args[0]);
						editor.commit();
						return 8;
					
					case 9:
						editor.putString("led", args[0]);
						editor.commit();
						return 9;
					}
					return 0;
				}
			}).start();
		}
	}
}
