package com.planner.app;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.planner.value.NumSet;

public class FrameView extends ViewGroup {

	public static Context me;
	
	public static final int CURRENT = 1;
	
	private VelocityTracker velocity = null;
	
	private Scroller scroller = null;
	private PointF lastPoint =null;
	private int curPage = CURRENT;
	
	private int touchState;
	
	public FrameView(Context con) {
		super(con);
		FrameView.me = con;
		init();
	}
	
	private void init() {
		scroller = new Scroller(getContext());
		lastPoint = new PointF();
		
	}
	
	protected void onMeasure(int width, int height) {
		super.onMeasure(width, height);
		
		for(int i = 0; i < getChildCount(); i++)
			getChildAt(i).measure(width, height);
	}
	
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
		for (int i = 0; i < getChildCount(); i++) {
			int child_left = getChildAt(i).getMeasuredWidth() * i;
			getChildAt(i).layout(child_left, t, child_left + getChildAt(i).getMeasuredWidth(), getChildAt(i).getMeasuredHeight());
		}
		
		this.scrollTo(getWidth(), 0);
	}
	
	public boolean onTouchEvent(MotionEvent e) {
		
		if(velocity == null)
			velocity = VelocityTracker.obtain();
		
		velocity.addMovement(e);
		
		switch(e.getAction()) {
		case MotionEvent.ACTION_DOWN :
			if(!scroller.isFinished()) scroller.abortAnimation();
			lastPoint.set(e.getX(), e.getY());
			break;
			
		case MotionEvent.ACTION_MOVE :
			int x = (int) (e.getX() - lastPoint.x);
			scrollBy(-x, 0);
			invalidate();
			lastPoint.set(e.getX(), e.getY());
			break;
			
		case MotionEvent.ACTION_UP : 
			velocity.computeCurrentVelocity(NumSet.ONE_SEC);
			int v = (int) velocity.getXVelocity();
			
			int gap = getScrollX() - curPage * getWidth();
			int nextPage = curPage;
			
			if ((v > NumSet.SNAP_VELOCITY || gap < -getWidth() / 2) && curPage > 0)
				nextPage--;
			else if ((v < -NumSet.SNAP_VELOCITY || gap > getWidth() / 2) && curPage < getChildCount() - 1)
				nextPage++;
			
			int move = 0;
			if (curPage != nextPage) move = getChildAt(0).getWidth() * nextPage - getScrollX();
			else move = getWidth() * curPage - getScrollX();
			scroller.startScroll(getScrollX(), 0, move, 0, Math.abs(move));
			
			invalidate();
			curPage = nextPage;
			
			if (curPage != CURRENT) {
				((FrameActivity)FrameActivity.me).Reset(curPage);
				return true;
			}
			
			touchState = NumSet.TOUCH_STATE_NORMAL;
			velocity.recycle();
			velocity = null;
			break;
		}
		
		return true;
	}
	
	public void computeScroll() {
		super.computeScroll();
		
		if (scroller.computeScrollOffset()) {
			scrollTo(scroller.getCurrX(), scroller.getCurrY());
			invalidate();
		}
	}
	
	public boolean onInterceptTouchEvent(MotionEvent e) {
		
		Rect touch = new Rect();
		WeekView.dateLay.getLocalVisibleRect(touch);
		
		if (e.getY() > touch.bottom) return false;
		
		Log.d("LOG", "onInterceptTouchEvent : " + e.getAction());
		int action = e.getAction();
		int x = (int) e.getX();
		int y = (int) e.getY();
		
		switch(action) {
		case MotionEvent.ACTION_DOWN :
			touchState = scroller.isFinished()? NumSet.TOUCH_STATE_NORMAL : NumSet.TOUCH_STATE_SCROLLING;
			lastPoint.set(x, y);
			break;
			
		case MotionEvent.ACTION_MOVE :
			int move_x = Math.abs(x - (int) lastPoint.x);
			if (move_x > NumSet.touchSlop) {
				touchState = NumSet.TOUCH_STATE_SCROLLING;
				lastPoint.set(x, y);
			}
			
			break;
		}
		
		return touchState == NumSet.TOUCH_STATE_SCROLLING;
	}
}
