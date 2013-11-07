package olectronix.hottie.config;

import olectronix.hottie.R;
import olectronix.hottie.SMSHandler;
import olectronix.hottie.SMSReceiver;
import android.annotation.TargetApi;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class RegisterPhoneActivity extends FragmentActivity implements
		OnSmsReceivedListener {

	SMSHandler smsHandler = new SMSHandler(this);
	SMSReceiver smsReceiver =new SMSReceiver();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_phone);
		setupActionBar();
		IntentFilter filter = new IntentFilter();
		  filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(smsReceiver, filter);
		smsReceiver.addSmsReceivedListener(this);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(smsReceiver);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
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
		getMenuInflater().inflate(R.menu.register_phone, menu);
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

	public void pinConfirmationClick(View view) {
		String pin = ((EditText) findViewById(R.id.pin_edit_text)).getText()
				.toString();
		String command = String.format(
				getResources().getString(R.string.pinCommand), pin);
		smsHandler.sendSMS(command);
	}

	public void showNumberFragment(String no1, String no2, String no3) {
		View container = findViewById(R.id.fragment_container);
		if (container != null) {

			RegisterPhoneFragment newFragment = new RegisterPhoneFragment();
			Bundle args = new Bundle();
			args.putString(RegisterPhoneFragment.NUMBER1, no1);
			args.putString(RegisterPhoneFragment.NUMBER2, no2);
			args.putString(RegisterPhoneFragment.NUMBER3, no3);
			newFragment.setArguments(args);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.fragment_container, newFragment).commit();
		}
	}

	@Override
	public void onSMSReceived(String messageBody) {
		if (messageBody
				.equals(getResources().getString(R.string.pinResponseOK))) {
			smsHandler
					.sendSMS(getResources().getString(R.string.numberCommand));
		}
		if (messageBody.contains(getResources().getString(
				R.string.numberResponse))) {
			String[] parts = messageBody.split(" ");
			String number1 = parts[3];
			String number2 = parts[5];
			String number3 = parts[7];

			View container = findViewById(R.id.fragment_container);
			if (container != null) {
				RegisterPhoneFragment newFragment = new RegisterPhoneFragment();
				Bundle args = new Bundle();
				args.putString(RegisterPhoneFragment.NUMBER1, number1);
				args.putString(RegisterPhoneFragment.NUMBER2, number2);
				args.putString(RegisterPhoneFragment.NUMBER3, number3);
				newFragment.setArguments(args);
				getSupportFragmentManager().beginTransaction()
						.add(R.id.fragment_container, newFragment).commit();
			}
		}
	}
}
