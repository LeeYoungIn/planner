package com.planner.dialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.planner.app.HomeActivity;
import com.planner.app.MySQLiteOpenHelper;
import com.planner.app.R;
import com.planner.database.ManageSQL;
import com.planner.value.NumSet;

public class AddDialog extends Dialog implements android.view.View.OnClickListener {

	private final ManageSQL sql = new ManageSQL();
	private static MySQLiteOpenHelper helper;
	
	private Context parent;
	private View.OnClickListener clickLis;
	
	private EditText contentT, cateT, stT, endT;
	private Button registerB;
	
	private SimpleDateFormat time_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM-dd");
	
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

	
	private void initComponent() {
		contentT = (EditText) findViewById(R.id.contentText);
		cateT = (EditText) findViewById(R.id.categoryText);
		stT = (EditText) findViewById(R.id.startText);
		endT = (EditText) findViewById(R.id.timeText);
		registerB = (Button) findViewById(R.id.registerButton);
		
		registerB.setOnClickListener(this);
		
		cateT.setText("ÀüÃ¼");
	}

	private void Add() {
		boolean isTodo = false;
		
		if (stT.getText().toString().trim().equals("")) 
			isTodo = true;
		
		if (isTodo) {
			sql.insert(helper, getDate(Calendar.getInstance()), getDate(Calendar.getInstance()),
					cateT.getText().toString().trim(), contentT.getText().toString().trim());
			sql.select(helper, NumSet.listTable);
		}
		
		else {
			Calendar cal = Calendar.getInstance();
			
			stT.setText(getTime(cal));
			endT.setText("30");
			
			sql.insert(helper, isTodo, false, stT.getText().toString().trim(),
					getEndTime(cal, endT.getText().toString()), cateT.getText().toString().trim(),
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
}
