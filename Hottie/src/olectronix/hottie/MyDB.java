package olectronix.hottie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDB extends SQLiteOpenHelper{
	public static final String TABLE_REPORTS = "reports";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_EXTERIOR = "exterior";
	  public static final String COLUMN_INTERIOR = "interior";
	  public static final String COLUMN_HOTTIE = "hottie";
	  public static final String COLUMN_WTT = "wtt";
	  public static final String COLUMN_VOLTAGE = "voltage";
	  public static final String COLUMN_STATUS = "status";
	  public static final String COLUMN_HEATING_TIME = "heating_time";
	  public static final String COLUMN_CURRENT_YEAR = "current_year";
	  public static final String COLUMN_CURRENT_MONTH = "current_month";
	  public static final String COLUMN_CURRENT_DAY = "current_day";
	  

	  private static final String DATABASE_NAME = "reports.db";
	  private static final int DATABASE_VERSION = 1;
	  
	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_REPORTS + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " 
	      + COLUMN_EXTERIOR + " real, "
	      + COLUMN_INTERIOR + " real, "
	      + COLUMN_HOTTIE + " real, "
	      + COLUMN_WTT + " real not null, "
	      + COLUMN_VOLTAGE + " real not null, "
	      + COLUMN_STATUS+ " real not null, "
	      + COLUMN_HEATING_TIME + " text not null, "
	      + COLUMN_CURRENT_YEAR + " int not null, "
	      + COLUMN_CURRENT_MONTH + " int not null, "
	      + COLUMN_CURRENT_DAY + " int not null);";

	public MyDB(Context context) {
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 Log.w(MyDB.class.getName(),
			        "Upgrading database from version " + oldVersion + " to "
			            + newVersion + ", which will destroy all old data");
			    db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORTS);
			    onCreate(db);
	}
}
