package olectronix.hottie;

import olectronix.hottie.config.ConfigActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class StartScreen extends Activity {

	SMSHandler smsHandler =new SMSHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_screen);
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
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void onReportButtonClick(View view)
	{
		String message=getResources().getString(R.string.reportCommand);
		smsHandler.sendSMS(message );
	}
	
	public void onOnButtonClick(View viev)
	{
		String message=getResources().getString(R.string.onCommand);
		smsHandler.sendSMS(message );
	}
	
	public void onOffButtonClick(View view)
	{
		String message=getResources().getString(R.string.offCommand);
		smsHandler.sendSMS(message );
	}
	
	public void onConfigButtonClick(View view)
	{
		Intent intent = new Intent(this, ConfigActivity.class);
		startActivity(intent);
	}
}
