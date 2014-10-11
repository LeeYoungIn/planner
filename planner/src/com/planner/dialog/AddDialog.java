package com.planner.dialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.planner.app.MySQLiteOpenHelper;
import com.planner.app.R;
import com.planner.database.ManageSQL;
import com.planner.value.NumSet;

public class AddDialog extends Dialog implements android.view.View.OnClickListener, OnCheckedChangeListener {

	private final ManageSQL sql = new ManageSQL();
	private static MySQLiteOpenHelper helper;
	
	private Context parent;
	private View.OnClickListener clickLis;
	
	private EditText contentT;
	private LinearLayout cateLay, cateGroup;
	private Button cateB1, cateB2;
	private LinearLayout timeLay, startLay, endLay;
//	private ExpandableListView timeGroup;
//	private ExpandableListView mListView;
	private ToggleButton toggleB;
	private Button stD, stT, endD, endT;
	private Button okB, cancelB;
	
	private SimpleDateFormat time_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM-dd");
	
//	private ArrayList<String> mGroupList = null;
//	private ArrayList<ArrayList<String>> mChildList = null;
//	private ArrayList<String> mChildListContent = null;
	
	public AddDialog(Context context, View.OnClickListener click, MySQLiteOpenHelper helper) {
		super(context);
		// TODO Auto-generated constructor stub
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add);
		
		this.setCanceledOnTouchOutside(false);
		this.setCancelable(true);
		
		this.helper = helper;
		parent = context;
		clickLis = click;
		
		initComponent();
	}

	private void expandableData() {
//		mGroupList = new ArrayList<String>();
//		mChildList = new ArrayList<ArrayList<String>>();
//		mChildListContent = new ArrayList<String>();
//
//		mGroupList.add(HomeActivity.me.getString(R.string.time_title));
//		mGroupList.add("group2");
//		mGroupList.add("group3");
//
//		mChildListContent.add(HomeActivity.me.getString(R.string.start_time));
//		mChildListContent.add(HomeActivity.me.getString(R.string.end_time));
//		mChildListContent.add("3");
//
//		mChildList.add(mChildListContent);
//		mChildList.add(mChildListContent);
//		mChildList.add(mChildListContent);
	}
	
	private void initComponent() {
		contentT = (EditText) findViewById(R.id.contentText);
		cateLay = (LinearLayout) findViewById(R.id.categoryLayout);
		cateGroup = (LinearLayout) findViewById(R.id.categoryGroupLayout);
		cateB1 = (Button) findViewById(R.id.cate1Button);
		cateB2 = (Button) findViewById(R.id.cate2Button);
		toggleB = (ToggleButton) findViewById(R.id.toggle);
		timeLay = (LinearLayout) findViewById(R.id.timeLayout);
		startLay = (LinearLayout) findViewById(R.id.startLayout);
		endLay = (LinearLayout) findViewById(R.id.endLayout);
//		endLay = (LinearLayout) findViewById(R.id.endLayout);
//		timeGroup = (ExpandableListView) findViewById(R.id.timesetGroup);
//		mListView = (ExpandableListView) findViewById(R.id.timesetGroup);
//		
//		timeGroup.setAdapter(new ExpandableListViewAdapter(parent));
//		timeGroup.setOnGroupClickListener(new OnGroupClickListener() {
//			@Override
//			public boolean onGroupClick(ExpandableListView pa, View v,
//					int groupPosition, long id) {
//				Toast.makeText(parent, "g click = " + groupPosition, 
//						Toast.LENGTH_SHORT).show();
//				return false;
//			}
//		});
		
		toggleB.setChecked(true);
		toggleB.setOnCheckedChangeListener(this);
		
		
		okB = (Button) findViewById(R.id.registerButton);
		cancelB = (Button) findViewById(R.id.cancelButton);
		
		okB.setOnClickListener(this);
		cancelB.setOnClickListener(this);
		
		expandableData();

//		mListView.setAdapter(new com.planner.widget.BaseExpandableAdapter(parent, mGroupList, mChildList));
//		mListView.setOnGroupClickListener(new OnGroupClickListener() {
//			@Override
//			public boolean onGroupClick(ExpandableListView p, View v,
//					int groupPosition, long id) {
//				Toast.makeText(parent.getApplicationContext(), "g click = " + groupPosition, 
//						Toast.LENGTH_SHORT).show();
//				return false;
//			}
//		});
	}

	private void Add() {
		boolean isTodo = false;
		
		if (stT.getText().toString().trim().equals("")) 
			isTodo = true;
		
		if (isTodo) {
			sql.insert(helper, getDate(Calendar.getInstance()), getDate(Calendar.getInstance()),
					"카테고리".trim(), contentT.getText().toString().trim());
			sql.select(helper, NumSet.listTable);
		}
		
		else {
			Calendar cal = Calendar.getInstance();
			
			stT.setText(getTime(cal));
			endT.setText("30");
			
			sql.insert(helper, isTodo, false, stT.getText().toString().trim(),
					getEndTime(cal, endT.getText().toString()), "카테고리".trim(),
					contentT.getText().toString().trim());
			sql.select(helper, NumSet.scheduleTable);
		}
	}
	
	private String getDate(Calendar c) {
		int year, month, date, hour, min;
		
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		date = c.get(Calendar.DATE);
		
		Date d = new Date(year - 1900, month, date);
		return date_sdf.format(d);
	}
	
	private String getTime(Calendar c) {
		int year, month, date, hour, min;
		
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		date = c.get(Calendar.DATE);
		hour = c.get(Calendar.HOUR);
		min = c.get(Calendar.MINUTE);
		
		Date d = new Date(year - 1900, month, date, hour, min);
		return time_sdf.format(d);
	}
	
	private String getEndTime(Calendar start, String time) {
		int add = Integer.parseInt(time);
		start.add(Calendar.MINUTE, add);

		return getTime(start);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.registerButton :
			Add();
			this.dismiss();
			break;
		}
	}
	
	public void show() {
		super.show();
	}
	
	public void dismiss() {
		super.dismiss();
	}
	
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean check) {
		// TODO Auto-generated method stub
		
		if (!check) {
			timeLay.setVisibility(View.GONE);
		} else timeLay.setVisibility(View.VISIBLE);
		
	}
}
