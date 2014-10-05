package com.planner.app;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.planner.value.NumSet;
import com.planner.value.StringSet;

public class FrameActivity extends Activity {

	private int changed = 0;
	private Calendar curToday;
	
	public static Context me;
	
	private static int mode;
	private final Calendar cal = Calendar.getInstance();
	
	private FrameView fv;
	private WeekView last, cur, next;
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FrameActivity.me = this;
		
		bundle = getIntent().getExtras();
		if (bundle != null)
    		FrameActivity.mode = (Integer) bundle.get(StringSet.MODE);

		countDate(cal);
		
		addToFrameView(last, cur, next);
	}
	
	private void countDate(Calendar today) {

		cur = new WeekView(this, today);
		
		today.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		today.add(Calendar.DAY_OF_YEAR, -1);
		last = new WeekView(this, today);

		today.add(Calendar.DAY_OF_WEEK, NumSet.WEEK_DAYS + 1);;
		next = new WeekView(this, today);
	}
	
	private void addToFrameView(WeekView a, WeekView b, WeekView c) {
		fv = new FrameView(this);
		
		fv.addView(a);
		fv.addView(b);
		fv.addView(c);
		
		setContentView(fv);
	}
	
	public void Reset(int request) {
		fv.removeAllViews();
		
		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.DAY_OF_WEEK, Calendar.getInstance().getFirstDayOfWeek());
		
		if (request > FrameView.CURRENT) {
			changed++;
			last = cur;
			cur = next;
			calendar.add(Calendar.DAY_OF_YEAR, NumSet.WEEK_DAYS * (changed + 1));
			next = new WeekView(this, calendar);
			
		} else {
			changed--;
			next = cur;
			cur = last;
			calendar.add(Calendar.DAY_OF_YEAR, NumSet.WEEK_DAYS * (changed - 1));
			last = new WeekView(this, calendar);
		}
		
		Log.d("LOG", "changed : " + changed);
		addToFrameView(last, cur, next);
	}
	
	public Calendar change() {
		curToday = Calendar.getInstance();
		curToday.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		
		if (changed == 0) return curToday;
		else curToday.add(Calendar.DAY_OF_YEAR, changed * NumSet.WEEK_DAYS);
		
		return curToday; 
	}
	
	public Calendar current() { return cal; }
}
