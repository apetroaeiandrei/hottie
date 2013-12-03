package olectronix.hottie.config;

import java.util.ArrayList;

import olectronix.hottie.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ConfigFragment extends Fragment{

	 /** 
     * The fragment argument representing the section number for this 
     * fragment. 
     */  
    public static final String ARG_SECTION_NUMBER = "section_number";  
 // This is the Adapter being used to display the list's data
 	SimpleCursorAdapter mAdapter;
 	private ListView lv;
 	private Context context;
 	ArrayList<ConfigMenuItem> lvItems;
 	
    public ConfigFragment() {  
    }  
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
        View rootView = inflater.inflate(R.layout.activity_config, container, false); 

        lv = (ListView) rootView.findViewById(R.id.config_list_view);
		// Instanciating an array list (you don't need to do this, you already
		// have yours)
		ArrayList<ConfigMenuItem> your_array_list = new ArrayList<ConfigMenuItem>();
		your_array_list =populateConfigMenu();
		lvItems=your_array_list;
		
		lv.setAdapter(new ConfigMenuAdapter(context, your_array_list));
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ConfigMenuItem selectedItem =(ConfigMenuItem) (lv.getItemAtPosition(position));
				String selectedFromList = selectedItem.textView.getText().toString();

				if (selectedFromList.equals(getResources().getString(
						R.string.registerPhoneText))) {
					Intent intent = new Intent(context,
							RegisterPhoneActivity.class);
					startActivity(intent);
				}
					if (selectedFromList.equals(getResources().getString(
							R.string.setHeaterIgnText))) {
						Intent intent = new Intent(context,
								HeaterIgnitionActivity.class);
						startActivity(intent);
				}
					if (selectedFromList.equals(getResources().getString(
							R.string.setTypeText))) {
						Intent intent = new Intent(context,
								HeaterTypeActivity.class);
						startActivity(intent);
				}
					if (selectedFromList.equals(getResources().getString(
							R.string.setOutputsText))) {
						Intent intent = new Intent(context,
								ConfigOutputsActivity.class);
						startActivity(intent);
				}
			}
		});
        return rootView;  
    }  
    private ArrayList<ConfigMenuItem> populateConfigMenu(){
		ArrayList<ConfigMenuItem> result = new ArrayList<ConfigMenuItem>();
		ConfigMenuItem configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.registerPhoneText));
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setHeaterIgnText));
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setOutputsText));
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setTypeText));
		result.add(configMenuItem);
		
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setIgnUpdateText));
		configMenuItem.setSeek_bar_visibility(View.VISIBLE);
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setLightTimeoutText));
		configMenuItem.setSeek_bar_visibility(View.VISIBLE);
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setWttErrorNoteText));
		configMenuItem.setSwitch_visibility(View.VISIBLE);
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setComErrorNoteText));
		configMenuItem.setSwitch_visibility(View.VISIBLE);
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setLedText));
		configMenuItem.setSwitch_visibility(View.VISIBLE);
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setTimeText));
		configMenuItem.setButton_go_visibility(View.VISIBLE);
		result.add(configMenuItem);
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.setDefaultText));
		configMenuItem.setButton_go_visibility(View.VISIBLE);
		result.add(configMenuItem);	
		
		configMenuItem = new ConfigMenuItem();
		configMenuItem.setText(getResources().getString(R.string.saveSettingsText));
		configMenuItem.setButton_go_visibility(View.VISIBLE);
		result.add(configMenuItem);	
		return result;
	}
}  

