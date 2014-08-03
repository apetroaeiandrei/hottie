package olectronix.hottie.general.activities;

import olectronix.hottie.*;
import olectronix.hottie.general.access.OnSmsReceivedListener;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import olectronix.hottie.general.access.HottieService;
import olectronix.hottie.general.access.SMSHandler;
import olectronix.hottie.general.access.SMSReceiver;
import olectronix.hottie.general.adapters.SwipePagerAdapter;
import olectronix.hottie.general.utils.DataUtils;

public class SwipeViews extends FragmentActivity implements
		OnSmsReceivedListener {

	SMSHandler smsHandler = new SMSHandler(this);
	SMSReceiver smsReceiver = new SMSReceiver();


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

        //Used for debugging purpose for erasing database
//		ReportDataSource datasource = new ReportDataSource(this);
//		datasource.open();
//		datasource.delete();
//		datasource.close();
		
	/*	// Use this to start and trigger a service
		Intent i = new Intent(this, HottieService.class);
		// Potentially add data to the intent
		i.putExtra("KEY1", "Value to be used by the service");
		this.startService(i);*/

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSwipePagerAdapter = new SwipePagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSwipePagerAdapter);

		final ActionBar actionBar = getActionBar();
		// Specify that tabs should be displayed in the action bar.
        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        }
        // Create a tab listener that is called when the user changes tabs.
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			@Override
			public void onTabReselected(Tab arg0,
					android.app.FragmentTransaction arg1) {
			}

			@Override
			public void onTabSelected(Tab tab,
					android.app.FragmentTransaction arg1) {
				mViewPager.setCurrentItem(tab.getPosition(), true);
			}

			@Override
			public void onTabUnselected(Tab arg0,
					android.app.FragmentTransaction arg1) {
			}
		};
		actionBar.addTab(actionBar.newTab().setText("Config")
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Basic")
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Advanced")
				.setTabListener(tabListener));

        // Used to set the correct selected tab on the action bar
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                actionBar.selectTab(getActionBar().getTabAt(i));
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(smsReceiver);
	}

    @Override
    protected void onResume() {
        super.onResume();
        registerReceivers();
        mViewPager.setCurrentItem(1);
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
		// Insert Report into database
		if (messageBody.contains(getResources().getString(
				R.string.statusReportResponse))
				&& !messageBody.contains("%")) {
            DataUtils.saveReportInDb(this,messageBody);
		}

        // Synchronize settings
		if (messageBody.contains(getResources()
				.getString(R.string.syncResponse))) {
			DataUtils.syncSettings(this,messageBody);
		}
	}

    private void registerReceivers(){
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver, filter);
        smsReceiver.addSmsReceivedListener(this);
    }
}
