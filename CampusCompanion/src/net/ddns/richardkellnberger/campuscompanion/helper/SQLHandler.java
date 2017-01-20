package net.ddns.richardkellnberger.campuscompanion.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "CampusCompanion_DB";

	private static final String TABLE_NAME_CONFIG = "Configs";
	private static final String KEY_NAME = "name";
	private static final String KEY_VALUE = "value";

	private static final String TABLE_NAME_TERMINE = "Termine";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_DATE_START = "dateStart";
	private static final String KEY_DATE_END = "dateEnd";

	private static final String TABLE_NAME_FOOD = "Food";
	private static final String KEY_FOOD = "food";
	private static final String KEY_PLACE = "place";
	private static final String KEY_CATEGORY = "category";
	private static final String KEY_PRICE1 = "price1";
	private static final String KEY_PRICE2 = "price2";
	private static final String KEY_PRICE3 = "price3";//TODO
	private static final String KEY_DATE = "date";
	private static final String KEY_VOTE_COUNT = "voteCount";
	private static final String KEY_VOTE = "vote";

	public SQLHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME_CONFIG).append(" (")
				.append(KEY_NAME).append(" TEXT PRIMARY KEY, ").append(KEY_VALUE).append(" TEXT)")
				.toString();
		db.execSQL(sql);

		sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME_TERMINE).append(" (")
				.append(KEY_DESCRIPTION).append(" TEXT, ").append(KEY_DATE_START).append(" TEXT, ").append(KEY_DATE_END)
				.append(" TEXT, PRIMARY KEY (").append(KEY_DESCRIPTION).append(", ")
				.append(KEY_DESCRIPTION).append(", ").append(KEY_DATE_END).append("))")
				.toString();
		db.execSQL(sql);

		sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME_FOOD).append(" (")
				.append(KEY_FOOD).append(" TEXT PRIMARY KEY, ").append(KEY_PLACE).append(" TEXT, ")
				.append(KEY_CATEGORY).append(" TEXT, ").append(KEY_PRICE1).append(" INTEGER, ")
				.append(KEY_PRICE2).append(" INTEGER, ").append(KEY_PRICE3).append(" INTEGER, ")
				.append(KEY_DATE).append(" TEXT, ").append(KEY_VOTE_COUNT).append(" INTEGER, ")
				.append(KEY_VOTE).append(" INTEGER)")
				.toString();
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

}
