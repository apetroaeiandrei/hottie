package olectronix.hottie;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ReportDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MyDB dbHelper;
	private String[] allColumns = { MyDB.COLUMN_ID, MyDB.COLUMN_EXTERIOR,
			MyDB.COLUMN_INTERIOR, MyDB.COLUMN_HOTTIE, MyDB.COLUMN_WTT,
			MyDB.COLUMN_VOLTAGE, MyDB.COLUMN_STATUS, MyDB.COLUMN_HEATING_TIME,
			MyDB.COLUMN_CURRENT_YEAR, MyDB.COLUMN_CURRENT_MONTH,
			MyDB.COLUMN_CURRENT_DAY };

	public ReportDataSource(Context context) {
		dbHelper = new MyDB(context);

	}

	// Used for debugging reasons.
	// TODO to remove this
	public void delete() {
		dbHelper.onUpgrade(database, 1, 2);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public ReportModel createReport(double exterior, double interior,
			double hottie, double wtt, double voltage, int status,
			String heatingTime, int currentYear, int currentMonth,
			int currentDay) {

		ContentValues values = new ContentValues();
		values.put(MyDB.COLUMN_EXTERIOR, exterior);
		values.put(MyDB.COLUMN_INTERIOR, interior);
		values.put(MyDB.COLUMN_HOTTIE, hottie);
		values.put(MyDB.COLUMN_WTT, wtt);
		values.put(MyDB.COLUMN_VOLTAGE, voltage);
		values.put(MyDB.COLUMN_STATUS, status);
		values.put(MyDB.COLUMN_HEATING_TIME, heatingTime);
		values.put(MyDB.COLUMN_CURRENT_YEAR, currentYear);
		values.put(MyDB.COLUMN_CURRENT_MONTH, currentMonth);
		values.put(MyDB.COLUMN_CURRENT_DAY, currentDay);
		long insertId = database.insert(MyDB.TABLE_REPORTS, null, values);
		Cursor cursor = database.query(MyDB.TABLE_REPORTS, allColumns,
				MyDB.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		ReportModel newReport = cursorToReport(cursor);
		cursor.close();
		return newReport;
	}

	public void deleteReport(ReportModel report) {
		long id = report.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(MyDB.TABLE_REPORTS, MyDB.COLUMN_ID + " = " + id, null);
	}

	public List<ReportModel> getAllReports() {
		List<ReportModel> reports = new ArrayList<ReportModel>();

		Cursor cursor = database.query(MyDB.TABLE_REPORTS, allColumns, null,
				null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ReportModel report = cursorToReport(cursor);
			reports.add(report);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return reports;
	}

	public List<ReportModel> getReportsInInterval(int startYear,
			int startMonth, int startDay, int stopYear, int stopMonth,
			int stopDay) {
		List<ReportModel> reports = new ArrayList<ReportModel>();
		String whereClause = MyDB.COLUMN_CURRENT_YEAR+">="+String.valueOf(startYear)
				+ " AND " + MyDB.COLUMN_CURRENT_YEAR+"<="+String.valueOf(stopYear)
				+ " AND " + MyDB.COLUMN_CURRENT_MONTH+">="+String.valueOf(startMonth)
				+ " AND " + MyDB.COLUMN_CURRENT_MONTH+"<="+String.valueOf(stopMonth)
				+ " AND " + MyDB.COLUMN_CURRENT_DAY+">="+String.valueOf(startDay)
				+ " AND " + MyDB.COLUMN_CURRENT_DAY+"<="+String.valueOf(stopDay);
		Cursor cursor = database.query(MyDB.TABLE_REPORTS, allColumns, whereClause,
				null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ReportModel report = cursorToReport(cursor);
			reports.add(report);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return reports;
	}

	private ReportModel cursorToReport(Cursor cursor) {
		ReportModel report = new ReportModel();
		report.setId(cursor.getLong(0));
		report.setExterior(cursor.getDouble(1));
		report.setInterior(cursor.getDouble(2));
		report.setHottie(cursor.getDouble(3));
		report.setWtt(cursor.getDouble(4));
		report.setVoltage(cursor.getDouble(5));
		report.setStatus(cursor.getDouble(6));
		report.setHeating_time(cursor.getString(7));
		report.setCurrent_year(cursor.getInt(8));
		report.setCurrent_month(cursor.getInt(9));
		report.setCurrent_day(cursor.getInt(10));
		return report;
	}
}
