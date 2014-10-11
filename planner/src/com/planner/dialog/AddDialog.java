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

import com.planner.app.HomeActivity;
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
	private ToggleButton toggleB;
	private LinearLayout timeLay, startLay, endLay;
	private Button stCan, stD, stT;
	private Button endCan, endD, endT;
	private Button okB, cancelB;
	
	private SimpleDateFormat time_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat form = new SimpleDateFormat("yyyy. MM. dd");
	
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
		cateLay = (LinearLayout) findViewById(R.id.categoryLayout);
		cateGroup = (LinearLayout) findViewById(R.id.categoryGroupLayout);
		cateB1 = (Button) findViewById(R.id.cate1Button);
		cateB2 = (Button) findViewById(R.id.cate2Button);
		toggleB = (ToggleButton) findViewById(R.id.toggle);
		timeLay = (LinearLayout) findViewById(R.id.timeLayout);
		startLay = (LinearLayout) findViewById(R.id.startLayout);
		stCan = (Button) findViewById(R.id.startCancel);
		stD = (Button) findViewById(R.id.startDateButton);
		stT = (Button) findViewById(R.id.startTimeButton);
		endLay = (LinearLayout) findViewById(R.id.endLayout);
		endCan = (Button) findViewById(R.id.endCancel);
		endD = (Button) findViewById(R.id.endDateButton);
		endT = (Button) findViewById(R.id.endTimeButton);
		
		toggleB.setChecked(true);
		toggleB.setOnCheckedChangeListener(this);
		
		Calendar cal = Calendar.getInstance();

        stD.setText(form.format(cal.getTime()));
        endD.setText(form.format(cal.getTime()));
        
        int amorpm = cal.get(Calendar.AM_PM);
        String ampm = "";
        if (amorpm == Calendar.AM)
            ampm = HomeActivity.me.getString(R.string.am);
        else ampm = HomeActivity.me.getString(R.string.pm);
        
        stT.setText(ampm + " " + cal.get(Calendar.HOUR) + "시");
        cal.add(Calendar.HOUR_OF_DAY, 1);
        endT.setText(ampm + " " + cal.get(Calendar.HOUR) + "시");
		
		okB = (Button) findViewById(R.id.registerButton);
		cancelB = (Button) findViewById(R.id.cancelButton);
		
		okB.setOnClickListener(this);
		cancelB.setOnClickListener(this);
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

		case R.id.cancelButton :
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
