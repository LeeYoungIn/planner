package com.planner.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.planner.value.DB;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	
	//create table
	private final String CREATE_TABLE = "create table ";
	
	private final String boolType = " boolean default false not null";
	private final String idType = " integer auto_increment primary key";
	private final String datetimeType = " datetime";
	private final String dateType = " date not null";
	private final String categoryType = " varchar(50)";
	private final String contentType = " varchar(255) not null";
	private final String colorType = " varchar(7) default '#000000' not null";
	
	public MySQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		String scheduleSql = CREATE_TABLE + DB.SCHE + "(" +
				DB.ID + idType + ", " +
				DB.TODO + boolType + ", " +
				DB.FINISH + boolType + ", " +
				DB.START_TIME + datetimeType + " not null, " +
				DB.END_TIME + ", " +
				DB.CATEGORY + categoryType + ", " +
				DB.CONTENT + contentType + ");";
		
		String listSql = CREATE_TABLE + DB.LIST + "(" +
				DB.ID + idType + ", " +
				DB.START_DATE + dateType + ", " +
				DB.END_DATE + dateType + ", " +
				DB.CATEGORY + categoryType + ", " +
				DB.CONTENT + contentType + ");";
		
		String categorySql = CREATE_TABLE + DB.CATE + "(" +
				DB.CATEGORY + categoryType + " not null, " +
				DB.COLOR + colorType + ");";
		
		String defaultCat = "insert into " + DB.CATE + " values ('ÀüÃ¼', '#000000');";

		db.execSQL(scheduleSql);
		db.execSQL(listSql);
		db.execSQL(categorySql);
		db.execSQL(defaultCat);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		String scheduleSql = "drop table if exists Schedule;";
		String listSql = "drop table if exists List;";
		String categorySql = "drop table if exists Category;";
		
		db.execSQL(scheduleSql);
		db.execSQL(listSql);
		db.execSQL(categorySql);
		
		onCreate(db);
	}

}
