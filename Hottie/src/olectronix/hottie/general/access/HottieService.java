package olectronix.hottie.general.access;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class HottieService extends Service {

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO do something useful
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
