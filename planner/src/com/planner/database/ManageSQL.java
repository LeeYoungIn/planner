package com.planner.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.planner.app.HomeActivity;
import com.planner.app.MySQLiteOpenHelper;
import com.planner.value.DB;
import com.planner.value.NumSet;

public class ManageSQL {
	
	private SQLiteDatabase db;
	
	//insert into Schedule
	public void insert(MySQLiteOpenHelper helper, boolean todo, boolean finish, String start, String end, String category, String content) {
		db = helper.getWritableDatabase();
		
//		String sql = DB.INSERT + DB.SCHE + " " + DB.tableVal(NumSet.scheduleTable) + 
//				"'" + todo + "', '" + finish + "', '" + start + "', '" + end + "', '" + category + "', '" + content + "');";
//		
//		db.execSQL(sql);
		
		ContentValues values = new ContentValues();
		
		values.put(DB.TODO, todo);
		values.put(DB.FINISH, finish);
		values.put(DB.START_TIME, start);
		values.put(DB.END_TIME, end);
		values.put(DB.CATEGORY, category);
		values.put(DB.CONTENT, content);
		
		db.insert(DB.SCHE, null, values);
	}
	
	//insert into List
	public void insert(MySQLiteOpenHelper helper, String start, String end, String category, String content) {
		db = helper.getWritableDatabase();
		
		String sql = DB.INSERT + DB.LIST + " " + DB.tableVal(NumSet.listTable) + 
				"'" + start + "', '" + end + "', '" + category + "', '" + content + "');";
		
		db.execSQL(sql);
	}
	
	//insert into Category
	public void insert(MySQLiteOpenHelper helper, String categoryName, String colorName) {
		db = helper.getWritableDatabase();
		
		String sql = DB.INSERT + DB.CATE + " " + DB.tableVal(NumSet.categoryTable) + 
				"'" + categoryName + "', '" + colorName + "');";
		
		db.execSQL(sql);
	}
	
	//delete
	public void delete(MySQLiteOpenHelper helper, int tableNum, String arg) {
		db = helper.getWritableDatabase();
		
		String table = DB.tableName(tableNum);

		if (tableNum == NumSet.categoryTable)
			db.delete(table, DB.CATEGORY + "=?", new String[]{arg});
		else db.delete(table, DB.ID + "=?", new String[]{arg});
	}
	
	//update Schedule
	public void update(MySQLiteOpenHelper helper, String id, boolean todo, boolean finish, String start, String end, String category, String content) {
		db = helper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put(DB.TODO, todo);
		values.put(DB.FINISH, finish);
		values.put(DB.START_TIME, start);
		values.put(DB.END_TIME, end);
		values.put(DB.CATEGORY, category);
		values.put(DB.CONTENT, content);
		
		db.update(DB.SCHE, values, DB.ID + "=?", new String[]{id});
	}
	
	//update List
	public void update(MySQLiteOpenHelper helper, String id, String start, String end, String category, String content) {
		db = helper.getWritableDatabase();

		ContentValues values = new ContentValues();
		
		values.put(DB.START_DATE, start);
		values.put(DB.END_DATE, end);
		values.put(DB.CATEGORY, category);
		values.put(DB.CONTENT, content);
		
		db.update(DB.LIST, values, DB.ID + "=?", new String[]{id});
	}
	
	//update Category
	public void update(MySQLiteOpenHelper helper, String categoryName, String colorName) {
		db = helper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put(DB.CATEGORY, categoryName);
		values.put(DB.COLOR, colorName);
		
		db.update(DB.CATE, values, DB.CATEGORY + "=?", new String[]{categoryName});
	}
	
	//select
	public Cursor select(MySQLiteOpenHelper helper, int tableNum) {
		db = helper.getReadableDatabase();
		
		String table = DB.tableName(tableNum);
		Cursor c = db.query(table, null, null, null, null, null, null);
		Cursor cursor = c;
		
		switch(tableNum) {
		case NumSet.scheduleTable :
			while (c.moveToNext()) {
				int id = c.getInt(c.getColumnIndex(DB.ID));
				int todo = c.getInt(c.getColumnIndex(DB.TODO));
				int fin = c.getInt(c.getColumnIndex(DB.FINISH));
				String st = c.getString(c.getColumnIndex(DB.START_TIME));
				String end = c.getString(c.getColumnIndex(DB.END_TIME));
				String cat = c.getString(c.getColumnIndex(DB.CATEGORY));
				String con = c.getString(c.getColumnIndex(DB.CONTENT));
				
				Log.d("LOG", DB.ID + " : " + id + "\n" + DB.TODO + " : " + todo + "\n" + DB.FINISH + " : " + fin + "\n" +
						DB.START_TIME + " : " + st + "\n" + DB.END_TIME + " : " + end + "\n" +
						DB.CATEGORY + " : " + cat + "\n" + DB.CONTENT + " : " + con);
			}
			
		case NumSet.listTable :
			while (c.moveToNext()) {
				int id = c.getInt(c.getColumnIndex(DB.ID));
				String st = c.getString(c.getColumnIndex(DB.START_DATE));
				String end = c.getString(c.getColumnIndex(DB.END_DATE));
				String cat = c.getString(c.getColumnIndex(DB.CATEGORY));
				String con = c.getString(c.getColumnIndex(DB.CONTENT));
				
				Log.d("LOG", DB.ID + " : " + id + "\n" + 
						DB.START_DATE + " : " + st + "\n" + DB.END_DATE + " : " + end + "\n" +
						DB.CATEGORY + " : " + cat + "\n" + DB.CONTENT + " : " + con);
			}
			
		case NumSet.categoryTable :
			while (c.moveToNext()) {
				String cat = c.getString(c.getColumnIndex(DB.CATEGORY));
				String con = c.getString(c.getColumnIndex(DB.CONTENT));
				
				Log.d("LOG", DB.CATEGORY + " : " + cat + "\n" + DB.CONTENT + " : " + con);
			}
		}
		
		return cursor;
	}
}
