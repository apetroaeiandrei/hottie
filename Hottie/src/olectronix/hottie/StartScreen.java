package olectronix.hottie;

import olectronix.hottie.config.ConfigActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
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
	
	public void saveNumber(View view)
	{
		String  phoneNumber =((EditText)findViewById(R.id.remote_phone_no)).getText().toString();
		SharedPreferences sharedPref = this.getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString("remote_phone_no", phoneNumber);
		editor.commit();
	}
}
