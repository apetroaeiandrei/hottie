package olectronix.hottie.config;

import android.widget.EditText;
import android.widget.TextView;

public class ConfigOutputsItem {
	private String outputName = "";
	public TextView nameTextView;

	public String getOutputName() {
		return outputName;
	}

	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

}
