package olectronix.hottie.config;

import java.util.ArrayList;

import olectronix.hottie.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ConfigOutputsAdapter extends BaseAdapter {

	private static ArrayList<ConfigOutputsItem> menusArrayList;
	private LayoutInflater mInflater;
	private Context currentContext;
	public ConfigOutputsAdapter(Context context, ArrayList<ConfigOutputsItem> items){
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
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.output_setup_row_template, null);
			holder.nameTextView =(TextView) convertView.findViewById(R.id.output_name_text_view);
			
		} else {
			holder = (ConfigOutputsItem) convertView.getTag();
		}
		holder.setOutputName(menusArrayList.get(position).getOutputName());
		holder.nameTextView.setText(menusArrayList.get(position).getOutputName());
		return convertView;
	}

}
