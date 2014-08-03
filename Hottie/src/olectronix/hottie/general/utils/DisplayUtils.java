package olectronix.hottie.general.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by Andrei Apetroaei on 6/15/14.
 */
public class DisplayUtils {
    public static void showAlert(final Context context, String message, final Class nextActivity) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setMessage(message);
        dialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (nextActivity != null) {
                    Intent intent = new Intent(context, nextActivity);
                    context.startActivity(intent);
                }
            }
        });
        dialog.show();
    }
}
