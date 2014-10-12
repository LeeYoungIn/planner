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

	private LinearLayout[] dateL = new LinearLayout[7];
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
		
		dateL[0] = (LinearLayout) v.findViewById(R.id.dateL0);
		dateL[1] = (LinearLayout) v.findViewById(R.id.dateL1);
		dateL[2] = (LinearLayout) v.findViewById(R.id.dateL2);
		dateL[3] = (LinearLayout) v.findViewById(R.id.dateL3);
		dateL[4] = (LinearLayout) v.findViewById(R.id.dateL4);
		dateL[5] = (LinearLayout) v.findViewById(R.id.dateL5);
		dateL[6] = (LinearLayout) v.findViewById(R.id.dateL6);
		
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
			
			setDateBack(NumSet.WEEK_DAYS);
		}
	}
	
	private void setDateBack(int index) {
		for (int i = 0; i < NumSet.WEEK_DAYS; i++) {
			
			if (index == NumSet.WEEK_DAYS) continue;
			
			String curDay = form.format(Calendar.getInstance().getTime());
			
			if (i == 0) {
				dateB[i].setTextColor(ColorSet.sunday);
			}
			else if (i == 6) {
				dateB[i].setTextColor(ColorSet.saturday);
			}
			
			else {
				if (((String) dateB[i].getTag()).compareTo(curDay) < 0) {
					dateB[i].setTextColor(ColorSet.pastDate);
					dateB[i].setBackground(null);
				} else {
					if (((String) dateB[i].getTag()).compareTo(curDay) > 0) {
						dateB[i].setTextColor(Color.BLACK);
						dateB[i].setBackground(null);
					} else {
						dateB[i].setTextColor(Color.BLACK);
						dateB[i].setBackgroundResource(R.drawable.today_circle);
					}
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

		setDateBack(index);
		
		dateB[index].setTextColor(Color.WHITE);
		dateB[index].setBackgroundResource(R.drawable.cur_circle);
		
		Calendar c = ((FrameActivity)FrameActivity.me).change();
		c.add(Calendar.DAY_OF_YEAR, index);
		
		((HomeActivity)HomeActivity.me).setClickedDate(c);
		
		subF = new SubFrameView(parent, c);

		subLay.removeAllViews();
		subLay.addView(subF);
	}
}