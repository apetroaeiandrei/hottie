package olectronix.hottie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

public class BasicTabFragment extends Fragment {
	Context context;
	Activity parentActivity;
	SMSHandler smsHandler;
	private XYPlot plot;

	public BasicTabFragment() {
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		parentActivity = activity;
		context = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_basic_tab,
				container, false);
		// initialize our XYPlot reference:
		plot = (XYPlot) rootView.findViewById(R.id.mySimpleXYPlot);
		plot.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(parentActivity,
						ReportGraphActivity.class);
				startActivity(intent);
			}
		});
		
		SharedPreferences sharedPref = parentActivity.getSharedPreferences(
				"syncPrefs", Context.MODE_PRIVATE);
		// Get interval
		String startDate = sharedPref.getString("reportGraphStart", "2014.0.1");
		String stopDate = sharedPref.getString("reportGraphStop", "2014.0.1");
		// Get values from database
		String[] startParts = startDate.split("\\.");
		String[] stopParts = stopDate.split("\\.");
		ReportDataSource datasource = new ReportDataSource(parentActivity);
		datasource.open();
//		List<ReportModel> reports = datasource.getReportsInInterval(
//				Integer.parseInt(startParts[0]),
//				Integer.parseInt(startParts[1]),
//				Integer.parseInt(startParts[2]),
//				Integer.parseInt(stopParts[0]), Integer.parseInt(stopParts[1]),
//				Integer.parseInt(stopParts[2]));
		List<ReportModel> reports = datasource.getAllReports();
		datasource.close();

		// Get the series to display on the report graph
				// 0 = Ext; 1=Int; 2=Hottie; 3=WTT; 4=Voltage
				
		int series = sharedPref.getInt("reportGraphSerie", 0);
		List<Number> plotList = new ArrayList<Number>();
		switch (series) {
		case 0:
			for (ReportModel item : reports)
				plotList.add(item.getExterior());
			break;
		case 1:
			for (ReportModel item : reports)
				plotList.add(item.getInterior());
			break;
		case 2:
			for (ReportModel item : reports)
				plotList.add(item.getHottie());
			break;
		case 3:
			for (ReportModel item : reports)
				plotList.add(item.getWtt());
			break;
		case 4:
			for (ReportModel item : reports)
				plotList.add(item.getVoltage()/1000);
			break;

		}

		// Turn the above arrays into XYSeries':
		XYSeries series1 = new SimpleXYSeries(plotList, 
				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, 
				"Series1"); 

		// Create a formatter to use for drawing a series using
		// LineAndPointRenderer
		// and configure it from xml:
		LineAndPointFormatter series1Format = new LineAndPointFormatter();
		series1Format.setPointLabelFormatter(new PointLabelFormatter());
		series1Format.configure(parentActivity.getApplicationContext(),
				R.xml.line_point_formatter_with_plf1);

		//plot.setDomainRightMax(plotList.size());
		//plot.setDomainBoundaries(0, 5, BoundaryMode.AUTO);
		// plot.setDomainStepValue(1);
		//plot.setRangeBoundaries(0,40,BoundaryMode.AUTO);
		// add a new series' to the xyplot:
		plot.addSeries(series1, series1Format);
		
		// reduce the number of range labels
		plot.setTicksPerRangeLabel(3);
		plot.getGraphWidget().setDomainLabelOrientation(-45);

		///////////////////////////////////////////////////////////////
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
