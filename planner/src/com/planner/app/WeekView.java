package com.planner.app;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.planner.value.ColorSet;
import com.planner.value.NumSet;


public class WeekView extends LinearLayout implements OnClickListener {

	private final LinearLayout.LayoutParams FFparam = new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
	private final SimpleDateFormat form = new SimpleDateFormat("yyyyMMdd");
	
	private Button[] dateB = new Button[7];
	public static LinearLayout dateLay;
	private LinearLayout subLay;
	
	private final Calendar cal;
	private final Context parent;
	
	private View v;
	private SubFrameView subF;
	
	public WeekView(Context context) {
		super(context);
		this.parent = context;
		cal = Calendar.getInstance();
	}
	
	public WeekView(Context context, Calendar cal) {
		super(context);
		this.parent = context;
		this.cal = cal;
		
		v = View.inflate(parent, R.layout.week, null);
		
		dateLay = (LinearLayout) v.findViewById(R.id.dateLayout);
		
		dateB[0] = (Button) v.findViewById(R.id.date0);
		dateB[1] = (Button) v.findViewById(R.id.date1);
		dateB[2] = (Button) v.findViewById(R.id.date2);
		dateB[3] = (Button) v.findViewById(R.id.date3);
		dateB[4] = (Button) v.findViewById(R.id.date4);
		dateB[5] = (Button) v.findViewById(R.id.date5);
		dateB[6] = (Button) v.findViewById(R.id.date6);
		
		subLay = (LinearLayout) v.findViewById(R.id.subLay);
		
		setDateTabs();
		init();
		
		addView(v);
	}
	
	private void setDateTabs() {
		for (int i = 0; i < NumSet.WEEK_DAYS; i++) {
			Calendar date = cal;
			date.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
			date.add(Calendar.DAY_OF_YEAR, i);
			
			dateB[i].setOnClickListener(this);
			dateB[i].setText(date.get(Calendar.DATE) + "");
			dateB[i].setTag(form.format(date.getTime()));
			
			String curDay = form.format(Calendar.getInstance().getTime());
			
			if (((String) dateB[i].getTag()).compareTo(curDay) < 0) {
				dateB[i].setTextColor(ColorSet.pastDate);
			} else {
				if (((String) dateB[i].getTag()).compareTo(curDay) > 0)
					dateB[i].setTextColor(Color.BLACK);
				else {
					dateB[i].setTextColor(Color.WHITE);
					dateB[i].setBackgroundColor(ColorSet.todayBack);
				}
			}
		}
	}
	
	private void init() {
		Calendar c = cal;
		c.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		
		int index = 0;
		for (index = 0; index < NumSet.WEEK_DAYS; index ++) {
			if (cal.equals(c)) break;
			else c.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		dateB[index].callOnClick();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		int index = 0;
		
		for (index = 0; index < NumSet.WEEK_DAYS; index++)
			if (v.equals(dateB[index])) break;
		
		Calendar c = ((FrameActivity)FrameActivity.me).change();
		c.add(Calendar.DAY_OF_YEAR, index);
		
		((HomeActivity)HomeActivity.me).setClickedDate(c);
		
		subF = new SubFrameView(parent, c);

		subLay.removeAllViews();
		subLay.addView(subF);
	}
}