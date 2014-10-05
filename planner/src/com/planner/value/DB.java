package com.planner.value;

public class DB {

	public static final String DBname = "database.db";
	
	public static final String INSERT = "insert into ";
	public static final String DELETE = "delete from ";
	
	//Schedule table
	public static final String SCHE = "Schedule";
	public static final String ID = "ID";
	public static final String TODO = "TODO";
	public static final String FINISH = "FINISH";
	public static final String START_TIME = "START_TIME";
	public static final String END_TIME = "END_TIME";
	public static final String CATEGORY = "CATEGORY";
	public static final String CONTENT = "CONTENT";
	
	//List table
	public static final String LIST = "List";
	public static final String START_DATE = "START_DATE";
	public static final String END_DATE = "END_DATE";
	
	//Category table
	public static final String CATE = "Category";
	public static final String COLOR = "COLOR";
	
	public static String tableName(int tableNum) {
		
		switch (tableNum) {
		case NumSet.scheduleTable :
			return SCHE;
		case NumSet.listTable :
			return LIST;
		case NumSet.categoryTable :
			return CATE;
		}
		
		return null;
	}
	
	public static String tableVal(int tableNum) {
		
		String sql = null;
		
		switch (tableNum) {
		case NumSet.scheduleTable :
			sql = "(" + TODO + ", " + FINISH + ", " + START_TIME + ", " + END_TIME + ", " + CATEGORY + ", " + CONTENT + ") values(";
			break;
		case NumSet.listTable :
			break;
		case NumSet.categoryTable :
			break;
		}
		
		return sql;
	}
}
