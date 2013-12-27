package olectronix.hottie.config;

import android.widget.FrameLayout;
import android.widget.TextView;

public class ConfigOutputsItem {
	private String outputName = "";
	public TextView nameTextView;
	private int outputType =0;
	private FrameLayout fragmentContainer;

	public String getOutputName() {
		return outputName;
	}

	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	public int getOutputType() {
		return outputType;
	}

	public void setOutputType(int outputType) {
		this.outputType = outputType;
	}

	public FrameLayout getFragmentContainer() {
		return fragmentContainer;
	}

	public void setFragmentContainer(FrameLayout fragmentContainer) {
		this.fragmentContainer = fragmentContainer;
	}

}
