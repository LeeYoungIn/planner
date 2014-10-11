//package com.planner.widget;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.ToggleButton;
//
//import com.planner.app.HomeActivity;
//import com.planner.app.R;
//
//public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
//
//	private ArrayList<String> group = null;
//	private ArrayList<ArrayList<String>> child = null;
//	private ArrayList<String> list = null;
//	
//	private Context context;
//	private ViewHolder vh;
//	private LayoutInflater inflater = null;
//	
//	private SimpleDateFormat form = new SimpleDateFormat("yyyy. MM. dd");
//	
//	public ExpandableListViewAdapter(Context context) {
//		this.context = context;
//		this.inflater = LayoutInflater.from(context);
//		
//		group = new ArrayList<String>();
//		child = new ArrayList<ArrayList<String>>();
//		list = new ArrayList<String>();
//		
//		group.add(HomeActivity.me.getString(R.string.time_title));
//
//		list.add(HomeActivity.me.getString(R.string.start_time));
//		list.add(HomeActivity.me.getString(R.string.end_time));
//
//		child.add(list);
//		child.add(list);
//		
////		child.add(new ArrayList<ArrayList<String>>());
////		child.get(0).add(new ArrayList<String>());
////		child.get(0).get(0).add(HomeActivity.me.getString(R.string.start_time));
////		child.get(0).add(new ArrayList<String>());
////		child.get(0).get(1).add(HomeActivity.me.getString(R.string.end_time));
//	}
//
//	@Override
//	public String getChild(int groupPosition, int childPosition) {
//		return child.get(groupPosition).get(childPosition);
//	}
//	
//	@Override
//	public long getChildId(int groupPosition, int childPosition) {
//		return childPosition;
//	}
//
//	@Override
//	public View getChildView(int groupPosition, int childPosition,
//			boolean isLastChild, View convertView, ViewGroup parent) {
//		View v = convertView;
//		
//        if(v == null){
//        	vh = new ViewHolder();
//			v = inflater.inflate(R.layout.time_set, null);
//            vh.stCan = (Button) v.findViewById(R.id.startCancel);
//            vh.start = (TextView) v.findViewById(R.id.start);
//            vh.stDate = (Button) v.findViewById(R.id.startDateButton);
//            vh.stTime = (Button) v.findViewById(R.id.startTimeButton);
//            
////            vh.endCan = (Button) v.findViewById(R.id.endCancel);
////            vh.end = (TextView) v.findViewById(R.id.end);
////            vh.endDate = (Button) v.findViewById(R.id.endDateButton);
////            vh.endTime = (Button) v.findViewById(R.id.endTimeButton);
//            v.setTag(vh);
//        }else{
//			vh = (ViewHolder)v.getTag();
//        }
//         
//        vh.stCan.setText("X");
//        vh.start.setText(getChild(groupPosition, childPosition));
//        
//        Calendar cal = Calendar.getInstance();
//
//        vh.stDate.setText(form.format(cal.getTime()));
////        vh.endDate.setText(form.format(cal.getTime()));
//        
//        int amorpm = cal.get(Calendar.AM_PM);
//        String ampm = "";
//        if (amorpm == Calendar.AM)
//            ampm = HomeActivity.me.getString(R.string.am);
//        else ampm = HomeActivity.me.getString(R.string.pm);
//        
//        vh.stTime.setText(ampm + " " + cal.get(Calendar.HOUR) + "시");
////        cal.add(Calendar.HOUR_OF_DAY, 1);
////        vh.endTime.setText(ampm + " " + cal.get(Calendar.HOUR) + "시");
//        
//        return v;
//	}
//
//	@Override
//	public int getChildrenCount(int groupPosition) {
//		return child.get(groupPosition).size();
//	}
//
//	@Override
//	public String getGroup(int groupPosition) {
//		return group.get(groupPosition);
//	}
//
//	@Override
//	public int getGroupCount() {
//		// TODO Auto-generated method stub
//		return group.size();
//	}
//
//	@Override
//	public long getGroupId(int groupPosition) {
//		// TODO Auto-generated method stub
//		return groupPosition;
//	}
//
//	//그룹뷰 각각 row
//	@Override
//	public View getGroupView(int groupPosition, boolean isExpanded,
//			View convertView, ViewGroup parent) {
//		
//		View v = convertView;
//		
//		if(v == null){
//			vh = new ViewHolder();
//			v = inflater.inflate(R.layout.time_set_title, parent, false);
//			vh.title = (TextView) v.findViewById(R.id.expandableGroup);
//			vh.toggle = (ToggleButton) v.findViewById(R.id.toggle);
//			v.setTag(vh);
//		}else{
//			vh = (ViewHolder)v.getTag();
//		}
//		
//		if(isExpanded){
//			vh.toggle.setChecked(true);;
//		}else{
//			vh.toggle.setChecked(false);
//		}
//		
//		vh.title.setText(getGroup(groupPosition));
//		
//		return v;
//	}
//
//	@Override
//	public boolean hasStableIds() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isChildSelectable(int arg0, int arg1) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//	
//	@Override
//	public boolean areAllItemsEnabled() {
//		return true;
//	}
//	
//	private class ViewHolder {
//		public TextView title;
//		public TextView start;
////		public TextView end;
//		public ToggleButton toggle;
//		public Button stCan, stDate, stTime;
////		public Button endCan, endDate, endTime;
//	}
//
//}
