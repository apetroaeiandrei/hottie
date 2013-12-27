package olectronix.hottie.config;

import java.util.ArrayList;

import olectronix.hottie.R;
import olectronix.hottie.SMSHandler;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ConfigOutputsAdapter extends BaseAdapter {

	private static ArrayList<ConfigOutputsItem> menusArrayList;
	private LayoutInflater mInflater;
	private Context currentContext;

	public ConfigOutputsAdapter(Context context,
			ArrayList<ConfigOutputsItem> items) {
		currentContext = context;
		menusArrayList = items;
		mInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		return menusArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return menusArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ConfigOutputsItem holder = new ConfigOutputsItem();
		final int fragmentContainerPosition = position+123;
		Button onButton;
		Button offButton;
		Button configButton;
		FrameLayout fragmentContainer;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.output_setup_row_template,
					null);
			fragmentContainer = (FrameLayout) convertView
					.findViewById(R.id.output_fragment_container);

		} else {
			holder = (ConfigOutputsItem) getItem(position);
			fragmentContainer = (FrameLayout) convertView
					.findViewById(fragmentContainerPosition);
			// (ConfigOutputsItem) convertView.getItem(position);
		}

		holder.nameTextView = (TextView) convertView
				.findViewById(R.id.output_name_text_view);
		
		holder.setFragmentContainer(fragmentContainer);
		onButton = (Button) convertView.findViewById(R.id.output_on_button);
		offButton = (Button) convertView.findViewById(R.id.output_off_button);
		configButton = (Button) convertView
				.findViewById(R.id.output_config_button);
		fragmentContainer.setId(fragmentContainerPosition);

		holder.setOutputName(menusArrayList.get(position).getOutputName());
		holder.nameTextView.setText(menusArrayList.get(position)
				.getOutputName());
		final ConfigOutputsItem holderFinal = holder;
		
		onButton.setOnClickListener(new Button.OnClickListener() {
			SMSHandler handler = new SMSHandler(currentContext);

			public void onClick(View v) {
				String name = holderFinal.nameTextView.getText().toString();
				String command = String.format(currentContext.getResources()
						.getString(R.string.outputOnCommand), name);
				handler.sendSMS(command);
			}
		});
		offButton.setOnClickListener(new Button.OnClickListener() {
			SMSHandler handler = new SMSHandler(currentContext);

			public void onClick(View v) {
				String name = holderFinal.nameTextView.getText().toString();
				String command = String.format(currentContext.getResources()
						.getString(R.string.outputOffCommand), name);
				handler.sendSMS(command);
			}
		});
		
		configButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				ConfigOutputFragment fragment = new ConfigOutputFragment();
				Bundle args = new Bundle();
				args.putString("name", holderFinal.getOutputName());
				args.putInt("type", holderFinal.getOutputType());
				fragment.setArguments(args);
				FragmentTransaction transaction = ((FragmentActivity) currentContext)
						.getSupportFragmentManager().beginTransaction();
				transaction.add(fragmentContainerPosition,fragment);
				transaction.commit();
			}
		});
		return convertView;
	}
}
