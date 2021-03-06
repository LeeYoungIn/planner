package com.goodshop.app;

import java.util.ArrayList;
import java.util.HashMap;

import com.goodshop.app.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ListView;

public class SearchActivity extends Activity {
	
	private ArrayList<Integer> Array_Data = new ArrayList<Integer>();
	private ShopListAdapter adapter;
	private ListView mList;	
	@Override
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		EditText findText=(EditText)findViewById(R.id.input);
		findText.setOnFocusChangeListener(new EditTextOnFocusChangeListener());
		findText.addTextChangedListener(new TextWatcher(){
			public void afterTextChanged(Editable s){
				findName(s.toString());
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void onTextChanged(CharSequence s, int start, int before, int count){}
		});
	
		mList = (ListView) findViewById(R.id.Cusom_List);
		adapter = new ShopListAdapter(this,	android.R.layout.simple_list_item_1, Array_Data);
		mList.setAdapter(adapter);	
		for(int i=0; i < ShopListClass.nShop; i++)
			Array_Data.add(i);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Builder d = new AlertDialog.Builder(this);
		d.setMessage("정말 종료하시겠습니까?");
		d.setPositiveButton("예", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		d.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		d.show();
	}

	public void findName(final String findString){
		Array_Data.clear();
		
		if(findString.length()>0){
			for(int i=0; i<ShopListClass.nShop;i++){
				if(SoundSearcher.matchString(ShopListClass.shopName[i], findString)){
					Array_Data.add(i);
				}
			}
		}
		else{
			for(int i=0;i<ShopListClass.nShop;i++){
				Array_Data.add(i);
			}
		}
		adapter.notifyDataSetChanged();
	}
	
	public static class SoundSearcher 
	{ 
		private static final char HANGUL_BEGIN_UNICODE = 44032; // 가 
		private static final char HANGUL_LAST_UNICODE = 55203; // 힣
		private static final char HANGUL_BASE_UNIT = 588;//각자음 마다 가지는 글자수
	 //자음
		private static final char[] INITIAL_SOUND = { 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' }; 
	 
	 
	 /**
	  * 해당 문자가 INITIAL_SOUND인지 검사.
	  * @param searchar
	  * @return
	  */

	 /**
	  * 해당 문자의 자음을 얻는다.
	  *  
	  * @param c 검사할 문자
	  * @return
	  */
		private static char getInitialSound(char c) { 
			int hanBegin = (c - HANGUL_BEGIN_UNICODE); 
			int index = hanBegin / HANGUL_BASE_UNIT; 
			return INITIAL_SOUND[index]; 
		} 
	 
	 
	 
	 /** * 검색을 한다. 초성 검색 완벽 지원함. 
	  * @param value : 검색 대상 ex> 초성검색합니다 
	  * @param search : 검색어 ex> ㅅ검ㅅ합ㄴ 
	  * @return 매칭 되는거 찾으면 true 못찾으면 false. */ 
		public static boolean matchString(String value, String search){ 
			 int t = 0; 
			 int seof = value.length() - search.length(); 
			 int slen = search.length(); 
			 char searchar;
			 char valchar;
			 if(seof < 0) 
				 return false; //검색어가 더 길면 false를 리턴한다. 
			 for(int i = 0; i <= seof; i++){ 
				 t = 0; 
				 while(t < slen){ 
					 searchar = search.charAt(t);
					 valchar = value.charAt(i+t);
					 if( (0x3131 <= searchar && searchar <= 0x314E) && (HANGUL_BEGIN_UNICODE <= valchar && valchar <= HANGUL_LAST_UNICODE) ){ 
						 //만약 현재 char이 초성이고 value가 한글이면
						 if(getInitialSound(valchar)==searchar) 
							 //각각의 초성끼리 같은지 비교한다
							 t++; 
						 else 
							 break; 
					 } else { 
						 //char이 초성이 아니라면
						 if(Character.toLowerCase(valchar) == Character.toLowerCase(searchar)) 
						 //그냥 같은지 비교한다. 
							 t++; 
						 else 
							 break; 
					 } 
				 } 
				 if(t == slen) 
					 return true; //모두 일치한 결과를 찾으면 true를 리턴한다. 
			 } 
			 return false; //일치하는 것을 찾지 못했으면 false를 리턴한다.
	 	}
	}
}


