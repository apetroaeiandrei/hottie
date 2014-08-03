package olectronix.hottie.general.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import olectronix.hottie.general.data.ReportDataSource;

import java.util.Calendar;

/**
 * Created by Andrei Apetroaei on 6/15/14.
 */
public class DataUtils {
    private static Handler progressBarHandler = new Handler();
    private static int progressBarStatus;

    public static void saveReportInDb (Context context, String messageBody){
        final String[] parts = messageBody.split("[\n:]");
        ReportDataSource datasource = new ReportDataSource(context);
        datasource.open();
        double exterior = Double.parseDouble(parts[3]);
        double interior = Double.parseDouble(parts[5]);
        double hottie = Double.parseDouble(parts[7]);
        double wtt = Double.parseDouble(parts[9]);
        double voltage = Double.parseDouble(parts[11]);
        int status = parts[13].equals(" on") ? 1 : 0;
        String heatingTime = parts[15] + ":" + parts[16];
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        datasource.createReport(exterior, interior, hottie, wtt, voltage,
                status, heatingTime, year, month, day);
        datasource.close();
    }

    public static void syncSettings(Context context, String messageBody) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.SYNC_PREFERENCES_KEY,
                Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        final String[] parts = messageBody.split(";");
        // prepare for a progress bar dialog
        final ProgressDialog progressBar = new ProgressDialog(context);
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
                        editor.putString(Constants.SYNC_PREFS_PHONE_1, args[0]);
                        editor.putString(Constants.SYNC_PREFS_PHONE_2, args[1]);
                        editor.putString(Constants.SYNC_PREFS_PHONE_3, args[2]);
                        editor.commit();
                        return 1;

                    case 2:
                        editor.putString(Constants.SYNC_PREFS_HTIGN_MODE, args[0]);
                        editor.putString(Constants.SYNC_PREFS_HTIGN_SENSOR, args[1]);
                        editor.putString(Constants.SYNC_PREFS_HTIGN_THL, args[2]);
                        editor.putString(Constants.SYNC_PREFS_HTIGN_THH, args[3]);
                        editor.commit();
                        return 2;

                    case 3:
                        editor.putString(Constants.SYNC_PREFS_OUTPUT_1, args[0]);
                        editor.putString(Constants.SYNC_PREFS_OUTPUT_2, args[1]);
                        editor.putString(Constants.SYNC_PREFS_OUTPUT_3, args[2]);
                        editor.putString(Constants.SYNC_PREFS_OUTPUT_4, args[3]);
                        editor.putString(Constants.SYNC_PREFS_OUTPUT_5, args[4]);
                        editor.putString(Constants.SYNC_PREFS_OUTPUT_6, args[5]);
                        editor.commit();
                        return 3;

                    case 4:
                        editor.putString(Constants.SYNC_PREFS_HEATER_TYPE, args[0]);
                        editor.commit();
                        return 4;

                    case 5:
                        editor.putString(Constants.SYNC_PREFS_IGNITION_TIME, args[0]);
                        editor.commit();
                        return 5;

                    case 6:
                        editor.putString(Constants.SYNC_PREFS_LIGHT_TIME, args[0]);
                        editor.commit();
                        return 6;

                    case 7:
                        editor.putString(Constants.SYNC_PREFS_ERROR_WTT, args[0]);
                        editor.commit();
                        return 7;

                    case 8:
                        editor.putString(Constants.SYNC_PREFS_ERROR_COM, args[0]);
                        editor.commit();
                        return 8;

                    case 9:
                        editor.putString(Constants.SYNC_PREFS_LED_STATUS, args[0]);
                        editor.commit();
                        return 9;

                    case 10:
                        editor.putInt(Constants.SYNC_PREFS_OUTPUT_TYPE_1, Integer.parseInt(args[0]));
                        editor.putInt(Constants.SYNC_PREFS_OUTPUT_TYPE_2, Integer.parseInt(args[1]));
                        editor.putInt(Constants.SYNC_PREFS_OUTPUT_TYPE_3, Integer.parseInt(args[2]));
                        editor.putInt(Constants.SYNC_PREFS_OUTPUT_TYPE_4, Integer.parseInt(args[3]));
                        editor.putInt(Constants.SYNC_PREFS_OUTPUT_TYPE_5, Integer.parseInt(args[4]));
                        editor.putInt(Constants.SYNC_PREFS_OUTPUT_TYPE_6, Integer.parseInt(args[5]));
                        editor.commit();
                        return 10;
                }
                return 0;
            }
        }).start();
    }
}
