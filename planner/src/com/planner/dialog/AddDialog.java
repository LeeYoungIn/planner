package com.planner.dialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.planner.app.HomeActivity;
import com.planner.app.R;
import com.planner.value.NumSet;

public class AddDialog extends Dialog implements android.view.View.OnClickListener {

	private Context parent;
	private View.OnClickListener clickLis;
	
	private EditText contentT, stT, endT;
	private Button registerB;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public AddDialog(Context context, View.OnClickListener click) {
		super(context);
		// TODO Auto-generated constructor stub
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add);
		
		this.setCanceledOnTouchOutside(false);
		this.setCancelable(true);
		
		parent = context;
		clickLis = click;
		
		initComponent();
	}

	
	private void initComponent() {
		contentT = (EditText) findViewById(R.id.contentText);
		stT = (EditText) findViewById(R.id.startText);
		endT = (EditText) findViewById(R.id.timeText);
		registerB = (Button) findViewById(R.id.registerButton);
		
		registerB.setOnClickListener(this);
		
	}

	private void Add() {
		boolean isTodo = false;
		
		if (stT.getText().equals(null)) 
			isTodo = true;
		
		String start = sdf.format(getDate());
		((HomeActivity)HomeActivity.me).insert(true, false, start, endT.getText().toString(), "전체", "과제");
		
		if (isTodo) ((HomeActivity)HomeActivity.me).select(NumSet.listTable);
		else ((HomeActivity)HomeActivity.me).select(NumSet.scheduleTable);
		
	}
	
	private Date getDate() {

		Calendar c = Calendar.getInstance();
		int year, month, date, hour, min;
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		date = c.get(Calendar.DATE);
		hour = c.get(Calendar.HOUR);
		min = c.get(Calendar.MINUTE);
		
		Date d = new Date(year - 1900, month - 1, date, hour, min);
		return d;
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
