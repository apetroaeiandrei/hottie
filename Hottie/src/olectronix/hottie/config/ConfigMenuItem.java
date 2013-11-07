package olectronix.hottie.config;

import android.view.View;

public class ConfigMenuItem {
	private String text = "";
	private Boolean onOff = false;
	private int button_sync_time_visibility = View.GONE;
	private int switch_visibility=View.GONE;
	private int seek_bar_visibility=View.GONE;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getOnOff() {
		return onOff;
	}

	public void setOnOff(Boolean onOff) {
		this.onOff = onOff;
	}

	public int getSwitchVisibility() {
		return switch_visibility;
	}

	public void setSwitchVisibility(int visibility) {
		this.switch_visibility = visibility;
	}

	public int getButton_sync_time_visibility() {
		return button_sync_time_visibility;
	}

	public void setButton_sync_time_visibility(int button_sync_time_visibility) {
		this.button_sync_time_visibility = button_sync_time_visibility;
	}

	public int getSeek_bar_visibility() {
		return seek_bar_visibility;
	}

	public void setSeek_bar_visibility(int seek_bar_visibility) {
		this.seek_bar_visibility = seek_bar_visibility;
	}
}
