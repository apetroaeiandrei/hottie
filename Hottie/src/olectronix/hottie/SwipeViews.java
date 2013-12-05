package olectronix.hottie;

import olectronix.hottie.config.OnSmsReceivedListener;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class SwipeViews extends FragmentActivity implements
		OnSmsReceivedListener {

	SMSHandler smsHandler = new SMSHandler(this);
	SMSReceiver smsReceiver = new SMSReceiver();
	ProgressDialog progressBar;
	private Handler progressBarHandler = new Handler();
	int progressBarStatus;
	final Context thisContext = this;
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SwipePagerAdapter mSwipePagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swipe_views);
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(smsReceiver, filter);
		smsReceiver.addSmsReceivedListener(this);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSwipePagerAdapter = new SwipePagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSwipePagerAdapter);
		final ActionBar actionBar = getActionBar();

		// Specify that tabs should be displayed in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create a tab listener that is called when the user changes tabs.
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			@Override
			public void onTabReselected(Tab arg0,
					android.app.FragmentTransaction arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(Tab tab,
					android.app.FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				mViewPager.setCurrentItem(tab.getPosition(),true);
			}

			@Override
			public void onTabUnselected(Tab arg0,
					android.app.FragmentTransaction arg1) {
				// TODO Auto-generated method stub
			}
		};

		actionBar.addTab(actionBar.newTab().setText("Config")
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Basic")
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Advanced")
				.setTabListener(tabListener));

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(smsReceiver);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.swipe_views, menu);
		return true;
	}

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
			progressBar.setMax(10);
			progressBar.show();

			// reset progress bar status
			progressBarStatus = 0;

			new Thread(new Runnable() {
				public void run() {
					for (int i = 1; i <= 10; i++) {
						// process some tasks
						progressBarStatus = saveSyncSettings(parts[i], i);

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

					if (progressBarStatus >= 10) {
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
					
					case 10:
						editor.putInt("outputType1", Integer.parseInt(args[0]));
						editor.putInt("outputType2", Integer.parseInt(args[1]));
						editor.putInt("outputType3", Integer.parseInt(args[2]));
						editor.putInt("outputType4", Integer.parseInt(args[3]));
						editor.putInt("outputType5", Integer.parseInt(args[4]));
						editor.putInt("outputType6", Integer.parseInt(args[5]));
						editor.commit();
						return 10;
					}	
					return 0;
				}
			}).start();
		}
	}
}
