package olectronix.hottie.general.data;

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
	private DatabaseHelper dbHelper;
	private String[] allColumns = { DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_EXTERIOR,
			DatabaseHelper.COLUMN_INTERIOR, DatabaseHelper.COLUMN_HOTTIE, DatabaseHelper.COLUMN_WTT,
			DatabaseHelper.COLUMN_VOLTAGE, DatabaseHelper.COLUMN_STATUS, DatabaseHelper.COLUMN_HEATING_TIME,
			DatabaseHelper.COLUMN_CURRENT_YEAR, DatabaseHelper.COLUMN_CURRENT_MONTH,
			DatabaseHelper.COLUMN_CURRENT_DAY };

	public ReportDataSource(Context context) {
		dbHelper = new DatabaseHelper(context);
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
		values.put(DatabaseHelper.COLUMN_EXTERIOR, exterior);
		values.put(DatabaseHelper.COLUMN_INTERIOR, interior);
		values.put(DatabaseHelper.COLUMN_HOTTIE, hottie);
		values.put(DatabaseHelper.COLUMN_WTT, wtt);
		values.put(DatabaseHelper.COLUMN_VOLTAGE, voltage);
		values.put(DatabaseHelper.COLUMN_STATUS, status);
		values.put(DatabaseHelper.COLUMN_HEATING_TIME, heatingTime);
		values.put(DatabaseHelper.COLUMN_CURRENT_YEAR, currentYear);
		values.put(DatabaseHelper.COLUMN_CURRENT_MONTH, currentMonth);
		values.put(DatabaseHelper.COLUMN_CURRENT_DAY, currentDay);
		long insertId = database.insert(DatabaseHelper.TABLE_REPORTS, null, values);
		Cursor cursor = database.query(DatabaseHelper.TABLE_REPORTS, allColumns,
				DatabaseHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		ReportModel newReport = cursorToReport(cursor);
		cursor.close();
		return newReport;
	}

	public void deleteReport(ReportModel report) {
		long id = report.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(DatabaseHelper.TABLE_REPORTS, DatabaseHelper.COLUMN_ID + " = " + id, null);
	}

	public List<ReportModel> getAllReports() {
		List<ReportModel> reports = new ArrayList<ReportModel>();

		Cursor cursor = database.query(DatabaseHelper.TABLE_REPORTS, allColumns, null,
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
		String whereClause = DatabaseHelper.COLUMN_CURRENT_YEAR+">="+String.valueOf(startYear)
				+ " AND " + DatabaseHelper.COLUMN_CURRENT_YEAR+"<="+String.valueOf(stopYear)
				+ " AND " + DatabaseHelper.COLUMN_CURRENT_MONTH+">="+String.valueOf(startMonth)
				+ " AND " + DatabaseHelper.COLUMN_CURRENT_MONTH+"<="+String.valueOf(stopMonth)
				+ " AND " + DatabaseHelper.COLUMN_CURRENT_DAY+">="+String.valueOf(startDay)
				+ " AND " + DatabaseHelper.COLUMN_CURRENT_DAY+"<="+String.valueOf(stopDay);
		Cursor cursor = database.query(DatabaseHelper.TABLE_REPORTS, allColumns, whereClause,
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
		report.setHeatingTime(cursor.getString(7));
		report.setCurrentYear(cursor.getInt(8));
		report.setCurrentMonth(cursor.getInt(9));
		report.setCurrentDay(cursor.getInt(10));
		return report;
	}
}
