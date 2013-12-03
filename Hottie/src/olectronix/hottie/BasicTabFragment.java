package olectronix.hottie;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class BasicTabFragment extends Fragment {
	Context context;
	Activity parentActiviy;
	SMSHandler smsHandler;

	public BasicTabFragment() {
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		parentActiviy = activity;
		context = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_basic_tab,
				container, false);
		smsHandler = new SMSHandler(context);
		Button on = (Button) rootView.findViewById(R.id.on_button);
		Button off = (Button) rootView.findViewById(R.id.off_button);
		Button report = (Button) rootView.findViewById(R.id.report_button);

		on.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String message = getResources().getString(R.string.onCommand);
				smsHandler.sendSMS(message);
			}
		});
		off.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String message = getResources().getString(R.string.offCommand);
				smsHandler.sendSMS(message);
			}
		});
		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String message = getResources().getString(
						R.string.reportCommand);
				smsHandler.sendSMS(message);
			}
		});

		return rootView;
	}
}
