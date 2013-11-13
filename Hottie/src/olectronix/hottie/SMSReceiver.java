package olectronix.hottie;

import java.util.ArrayList;

import olectronix.hottie.config.OnSmsReceivedListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {
	ArrayList<OnSmsReceivedListener> listeners = new ArrayList<OnSmsReceivedListener>();

	public void addSmsReceivedListener(OnSmsReceivedListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeSmsReceivedListener(OnSmsReceivedListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
	
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		String remotePhone = sharedPref.getString("remote_phone_no","0000");

		// ---get the SMS message passed in---
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String messageBody = "";
		if (bundle != null) {
			// ---retrieve the SMS message received---
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i = 0; i < msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				String sender = (msgs[i].getOriginatingAddress()).toString();
				// Add "1555521"+ to remote phone for debugging
				if (("1555521" + remotePhone).equals(sender)
						|| remotePhone.equals(sender)) {
					messageBody = msgs[i].getMessageBody().toString();
					for (OnSmsReceivedListener listener : listeners) {
						listener.onSMSReceived(messageBody);
					}
				}
			}
			// ---display the new SMS message---
			// Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
		}
	}
}