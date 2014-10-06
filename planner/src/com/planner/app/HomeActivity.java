package com.planner.app;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.ActivityGroup;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.planner.anim.CloseAnimation;
import com.planner.anim.OpenAnimation;
import com.planner.dialog.AddDialog;
import com.planner.value.DB;
import com.planner.value.NumSet;
import com.planner.value.StringSet;

public class HomeActivity extends ActivityGroup implements OnClickListener {
	
	private static int mode;
	private SimpleDateFormat mon_day_form = new SimpleDateFormat("MM¿ù ddÀÏ");

	public static Context me;
	
	public static SQLiteDatabase db;
	public static MySQLiteOpenHelper helper;
	
	private Calendar cal;
	
	private TabHost tabHost;

	private int leftMenuWidth;
	private LinearLayout mainLay, menuLay, empty, tabLay;
	private static boolean isLeftExpanded;
	private DisplayMetrics metrics = new DisplayMetrics();
	private Animation openAni, closeAni;
	
	private TextView todayT;
	private Button menuB, modeB, addB;
	
	private AddDialog addD;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.me = this;
		
		helper = new MySQLiteOpenHelper(HomeActivity.this, DB.DBname, null, NumSet.version);
		
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(this.getLocalActivityManager());
		
		todayT = (TextView) findViewById(R.id.todayDate);
		menuB = (Button) findViewById(R.id.menuButton);
		modeB = (Button) findViewById(R.id.modeButton);
		addB = (Button) findViewById(R.id.addButton);
		
		mainLay = (LinearLayout) findViewById(R.id.mainLayout);
		menuLay = (LinearLayout) findViewById(R.id.menuLayout);
		tabLay = (LinearLayout) findViewById(R.id.tabLayout);
		empty = (LinearLayout) findViewById(R.id.empty);
		
		openAni = AnimationUtils.loadAnimation(this, R.anim.slide_open);
		closeAni = AnimationUtils.loadAnimation(this, R.anim.slide_close);
		
		init();
		setTop();
		
		setIntent();
	}
	
	private void setIntent() {
		Intent intent = new Intent(HomeActivity.this, FrameActivity.class);
		intent.putExtra(StringSet.MODE, mode);
		
		tabHost.addTab(tabHost.newTabSpec(StringSet.MAIN).setIndicator(StringSet.MAIN).setContent(intent));
	}
	
	private void init() {
		cal = Calendar.getInstance();
		mode = NumSet.DAILY;
		
		RelativeLayout.LayoutParams leftMenuLayoutPrams;
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		leftMenuWidth = (int) ((metrics.widthPixels) * NumSet.MENU_RATE);
		
		leftMenuLayoutPrams = (RelativeLayout.LayoutParams) menuLay.getLayoutParams();
		leftMenuLayoutPrams.width = leftMenuWidth;
		menuLay.setLayoutParams(leftMenuLayoutPrams);
		
		menuLay.setVisibility(View.INVISIBLE);
		empty.setVisibility(View.GONE);
	}
	
	private void setTop() {
		menuB.setOnClickListener(this);
		
		todayT.setText(mon_day_form.format(cal.getTime()));
		modeB.setOnClickListener(this);
		
		addB.setOnClickListener(this);
	}
	
	private void menuLeftSlideAnimationToggle() {

		if (!isLeftExpanded) {
			isLeftExpanded = true;

			menuLay.startAnimation(openAni);
			menuLay.setVisibility(View.VISIBLE);

			// Expand
//			new OpenAnimation(menuLay, leftMenuWidth,
//					Animation.RELATIVE_TO_SELF, 0.0f - leftMenuWidth,
//					Animation.RELATIVE_TO_SELF, 0, 0, mainLay.getHeight(), 0, mainLay.getHeight());
//			
//			new OpenAnimation(empty, leftMenuWidth,
//					Animation.RELATIVE_TO_SELF, 0.0f,
//					Animation.RELATIVE_TO_SELF, NumSet.MENU_RATE, 0, mainLay.getHeight(), 0, mainLay.getHeight());

			enableDisableViewGroup(tabLay, false);

			empty.setVisibility(View.VISIBLE);
			empty.setEnabled(true);
			empty.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					menuLeftSlideAnimationToggle();
					return true;
				}
			});
			

			
			

		} else {
			isLeftExpanded = false;

			menuLay.startAnimation(closeAni);
			menuLay.setVisibility(View.INVISIBLE);

			// close
//			new CloseAnimation(empty, leftMenuWidth,
//					TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
//					TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, mainLay.getHeight(), 0, 0.0f);

			enableDisableViewGroup(tabLay, true);

			empty.setVisibility(View.INVISIBLE);
			empty.setEnabled(false);
		}
	}
	
	public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
		int childCount = viewGroup.getChildCount();
		
		for (int i = 0; i < childCount; i++) {
			View view = viewGroup.getChildAt(i);
			
			if (view.getId() != R.id.menuButton) {
				view.setEnabled(enabled);
				if (view instanceof ViewGroup)
					enableDisableViewGroup((ViewGroup) view, enabled);
			}
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.menuButton :
			menuLeftSlideAnimationToggle();
			break;
			
		case R.id.modeButton :
//			Intent intent = getIntent();
//			finish();
//			startActivity(intent);
			break;
			
		case R.id.addButton :
			addD = new AddDialog(me, this);
			addD.show();
			break;
		}
	}
	
	public void setClickedDate(Calendar cur) {
		todayT.setText(mon_day_form.format(cur.getTime()));
	}
	
	//insert into Schedule
	public void insert(boolean todo, boolean finish, String start, String end, String category, String content) {
		db = helper.getWritableDatabase();
		
		String sql = DB.INSERT + DB.SCHE + " " + DB.tableVal(NumSet.scheduleTable) + 
				"'" + todo + "', '" + finish + "', '" + start + "', '" + end + "', '" + category + "', '" + content + "');";
		
		db.execSQL(sql);
	}
	
	//insert into List
	public void insert(String start, String end, String category, String content) {
		db = helper.getWritableDatabase();
		
		String sql = DB.INSERT + DB.LIST + " " + DB.tableVal(NumSet.listTable) + 
				"'" + start + "', '" + end + "', '" + category + "', '" + content + "');";
		
		db.execSQL(sql);
		
//		values.put(DB.START_DATE, start.getTime().toString());
//		values.put(DB.END_DATE, end.getTime().toString());
//		values.put(DB.CATEGORY, category);
//		values.put(DB.CONTENT, content);
//		
//		db.insert(DB.LIST, null, values);
	}
	
	//insert into Category
	public void insert(String categoryName, String colorName) {
		db = helper.getWritableDatabase();
		
		String sql = DB.INSERT + DB.CATE + " " + DB.tableVal(NumSet.categoryTable) + 
				"'" + categoryName + "', '" + colorName + "');";
		
		db.execSQL(sql);
		
//		ContentValues values = new ContentValues();
//		
//		values.put(DB.CATEGORY, categoryName);
//		values.put(DB.COLOR, colorName);
//		
//		db.insert(DB.CATE, null, values);
	}
	
	//delete
	public void delete(int tableNum, String arg) {
		db = helper.getWritableDatabase();
		
		String table = DB.tableName(tableNum);

		if (tableNum == NumSet.categoryTable)
			db.delete(table, DB.CATEGORY + "=?", new String[]{arg});
		else db.delete(table, DB.ID + "=?", new String[]{arg});
	}
	
	//update Schedule
	public void update(String id, boolean todo, boolean finish, String start, String end, String category, String content) {
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
	public void update(String id, String start, String end, String category, String content) {
		db = helper.getWritableDatabase();

		ContentValues values = new ContentValues();
		
		values.put(DB.START_DATE, start);
		values.put(DB.END_DATE, end);
		values.put(DB.CATEGORY, category);
		values.put(DB.CONTENT, content);
		
		db.update(DB.LIST, values, DB.ID + "=?", new String[]{id});
	}
	
	//update Category
	public void update(String categoryName, String colorName) {
		db = helper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put(DB.CATEGORY, categoryName);
		values.put(DB.COLOR, colorName);
		
		db.update(DB.CATE, values, DB.CATEGORY + "=?", new String[]{categoryName});
	}
	
	//select
	public Cursor select(int tableNum) {
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