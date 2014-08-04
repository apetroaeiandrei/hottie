package olectronix.hottie.general.access;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.widget.Toast;
import olectronix.hottie.R;
import olectronix.hottie.general.activities.UserSettingsActivity;
import olectronix.hottie.general.utils.Constants;
import olectronix.hottie.general.utils.DisplayUtils;

public class SMSHandler {
    private static final String DEFAULT_PHONE_NUMBER = "0";
    private Context parentActivity;
    private Boolean delivered = false;

    public SMSHandler(Context activity) {
        parentActivity = activity;
    }

    //---sends an SMS message to another device---
    public Boolean sendSMS(String message) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(parentActivity);
        String remotePhone = sharedPref.getString(Constants.SETTINGS_REMOTE_PHONE_NUMBER, DEFAULT_PHONE_NUMBER);

        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(parentActivity, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(parentActivity, 0,
                new Intent(DELIVERED), 0);

        if (!remotePhone.equals(DEFAULT_PHONE_NUMBER)) {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(remotePhone, null, message, sentPI, deliveredPI);

            //---when the SMS has been sent---
            parentActivity.registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context arg0, Intent arg1) {
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            Toast.makeText(parentActivity, "SMS sent",
                                    Toast.LENGTH_SHORT).show();
                            delivered = true;
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            Toast.makeText(parentActivity, "Generic failure",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            Toast.makeText(parentActivity, "No service",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            Toast.makeText(parentActivity, "Null PDU",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            Toast.makeText(parentActivity, "Radio off",
                                    Toast.LENGTH_SHORT).show();
                            break;
                    }
                    parentActivity.unregisterReceiver(this);
                }
            }, new IntentFilter(SENT));

            //---when the SMS has been delivered---
            parentActivity.registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context arg0, Intent arg1) {
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            Toast.makeText(parentActivity, "SMS delivered",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(parentActivity, "SMS not delivered",
                                    Toast.LENGTH_SHORT).show();
                            break;
                    }
                    parentActivity.unregisterReceiver(this);
                }
            }, new IntentFilter(DELIVERED));
        } else {
            DisplayUtils.showAlert(parentActivity,
                    parentActivity.getString(R.string.alert_set_phone_number),
                    UserSettingsActivity.class);
        }
        return delivered;
    }
}
