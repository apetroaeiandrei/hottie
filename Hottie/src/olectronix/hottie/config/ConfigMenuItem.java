package olectronix.hottie.config;

import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class ConfigMenuItem {
	private String text = "";
	private int button_go_visibility = View.GONE;
	private int switch_visibility=View.GONE;
	private int seek_bar_visibility=View.GONE;
	
	public TextView textView;
	public Switch switch1;
	public Button button_go;
	public SeekBar seek_bar1;
	public TextView seek_bar_valueTextView;
	
	public void assignObject(ConfigMenuItem holder)
	{
		this.textView =holder.textView;
		this.button_go =holder.button_go;
		this.seek_bar1=holder.seek_bar1;
		this.switch1=holder.switch1;
		this.seek_bar_valueTextView=holder.seek_bar_valueTextView;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getButton_go_visibility() {
		return button_go_visibility;
	}

	public void setButton_go_visibility(int button_go_visibility) {
		this.button_go_visibility = button_go_visibility;
	}

	public int getSwitch_visibility() {
		return switch_visibility;
	}

	public void setSwitch_visibility(int switch_visibility) {
		this.switch_visibility = switch_visibility;
	}

	public int getSeek_bar_visibility() {
		return seek_bar_visibility;
	}

	public void setSeek_bar_visibility(int seek_bar_visibility) {
		this.seek_bar_visibility = seek_bar_visibility;
	}
}
