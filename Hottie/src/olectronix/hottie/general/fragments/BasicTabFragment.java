package olectronix.hottie.general.fragments;

import java.util.ArrayList;
import java.util.Iterator;
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

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import olectronix.hottie.R;
import olectronix.hottie.general.data.ReportDataSource;
import olectronix.hottie.general.data.ReportModel;
import olectronix.hottie.general.access.SMSHandler;
import olectronix.hottie.general.activities.ReportGraphActivity;

public class BasicTabFragment extends Fragment implements OnClickListener {
    private Context context;
    private Activity parentActivity;
    private SMSHandler smsHandler;
    private XYPlot plot;
    private Button on;
    private Button off;
    private Button report;

    public BasicTabFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = activity;
        context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_basic_tab, container, false);
        on = (Button) rootView.findViewById(R.id.on_button);
        off = (Button) rootView.findViewById(R.id.off_button);
        report = (Button) rootView.findViewById(R.id.report_button);
       // plot = (XYPlot) rootView.findViewById(R.id.mySimpleXYPlot);

        // Initialize SMS handler
        smsHandler = new SMSHandler(context);

        on.setOnClickListener(this);
        off.setOnClickListener(this);
        report.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Initializes the plot
        //initializePlot();
    }

    @Override
    public void onClick(View v) {
        if (v == on) {
            String message = getResources().getString(R.string.onCommand);
            smsHandler.sendSMS(message);
        } else if (v == off) {
            String message = getResources().getString(R.string.offCommand);
            smsHandler.sendSMS(message);
        } else if (v == report) {
            String message = getResources().getString(R.string.reportCommand);
            smsHandler.sendSMS(message);
        }
    }

    private void initializePlot() {

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
        String plotName = "Value";
        switch (series) {
            case 0:
                plotName = "Exterior Temp";
                for (ReportModel item : reports)
                    plotList.add(item.getExterior());
                break;
            case 1:
                plotName = "Interior Temp";
                for (ReportModel item : reports)
                    plotList.add(item.getInterior());
                break;
            case 2:
                plotName = "Hottie Temp";
                for (ReportModel item : reports)
                    plotList.add(item.getHottie());
                break;
            case 3:
                plotName = "Webasto Temp";
                for (ReportModel item : reports)
                    plotList.add(item.getWtt());
                break;
            case 4:
                plotName = "Voltage";
                for (ReportModel item : reports)
                    plotList.add(item.getVoltage() / 1000);
                break;
        }
        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(plotList,
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                plotName);

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

        if (plot.getSeriesSet().size()!=0)
        {Iterator<XYSeries> i = plot.getSeriesSet().iterator();
            plot.removeSeries(i.next());
        }
        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);

        // reduce the number of range labels
        plot.setTicksPerRangeLabel(3);
        plot.getGraphWidget().setDomainLabelOrientation(-45);
    }
}
