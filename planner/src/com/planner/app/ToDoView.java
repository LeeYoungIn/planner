package com.planner.app;

import java.util.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ToDoView extends LinearLayout {

	private final Context parent;
	private final Calendar cal;
	
	private View v;
	
	private ScrollView scroll;
	private RelativeLayout todoRelay;
	private TextView planT, todoT;
	private ListView planL, todoL;
	
	public ToDoView(Context context, Calendar cal) {
		super(context);
		this.parent = context;
		this.cal = cal;
		
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
		
		planT.setText("To do");

		removeAllViews();
		
	}
	
	private void setSubFrame(SubFrameView sub) {
//		subLay.removeAllViews();
//		main.removeView(subLay);
//		
//		subLay.addView(sub, FFparam);
//		main.addView(subLay, BWparam);
	}
	
}
