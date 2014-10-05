package com.planner.app;

import java.util.Calendar;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TimeView extends LinearLayout {

	private final Context parent;
	private View v;
	private TextView planT;
	
	public TimeView(Context context, Calendar cal) {
		super(context);
		// TODO Auto-generated constructor stub
		parent = context;
		
		init();
		
		addView(v);
	}

	private void init() {

		v = View.inflate(parent, R.layout.todo, null);
		
//		LayoutInflater inflater = (LayoutInflater) parent.getSystemService(parent.LAYOUT_INFLATER_SERVICE);
//		View layout = inflater.inflate(R.layout.todo, null);
		
//		scroll = (ScrollView) v.findViewById(R.id.scrollLayout);
//		todoRelay = (RelativeLayout) findViewById(R.id.todoRelay);
		planT = (TextView) v.findViewById(R.id.planText);
//		planL = (ListView) findViewById(R.id.planList);
//		todoT = (TextView) findViewById(R.id.todoText);
//		todoL = (ListView) findViewById(R.id.todoList);
		
		planT.setText("Time");

		removeAllViews();
		
	}
}
