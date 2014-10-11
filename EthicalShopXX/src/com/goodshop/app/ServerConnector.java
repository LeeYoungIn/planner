package com.goodshop.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ServerConnector {
	

	private static BufferedReader reader = null;
	
	private static final String TAG = "SHOP";
	private static OutputStreamWriter wr;
	private static int status;
    private static final int SUC_STATUS = 200;
	private static String receive;
	
	public static final String USER = "username";
	public static final String PASSWD = "password";
	public static final String EMAIL = "email";
	public static final String POINT = "point";
	public static final String IS_LOGIN = "is_login";
	public static final String MODI = "lastModified";
	public static final String SUC = "success";
	public static final String FAIL = "failure";
	
	private static final String SERV_IP = "http://175.126.100.133/";
	private static final String[] URLS = { "login.php", "signup.php", "eventlist.php", "shoplist.php" };
	
	private static final int LOGIN = 0;
	private static final int SIGN = 1;
	public static final int EVENT = 2;
	public static final int SHOP = 3;

	private static String idData = "", passData = "", eData = "", modiData = "";
	private static QueryCallBack call;
	
	private static void connect(int mode) {

        String suc = "", email = "null", point = "null";
        
		try {

			String add = URLS[mode];
			int request = 0;
			
            URL url = new URL(SERV_IP + add);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        	
        	conn.setDoOutput(true);
        	conn.setDoInput(true);
        	conn.setRequestMethod("POST");
            wr = new OutputStreamWriter(conn.getOutputStream()); 
            
            switch(mode){
            case LOGIN :
            	wr.write(idData);
                wr.write(passData);
                break;
            case SIGN :
            	wr.write(idData);
                wr.write(passData);
                wr.write(eData);
                break;
			case SHOP :	
			case EVENT :
				wr.write(modiData);
				break;
			}
    		
    		wr.flush();
    		
    		status = conn.getResponseCode();
        	Log.d(TAG, "status : "+status);
        	
        	if (conn.getResponseCode() != SUC_STATUS) 
        		reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        	else reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        	StringBuilder sb = new StringBuilder();
        	String line = null;
    		
            while ((line = reader.readLine()) != null) {
            	sb.append(line + ""); }
            receive = sb.toString();
            
            Log.d(TAG, "receive : " + receive);
			
            if (mode == LOGIN) try {
            	JSONArray jsonarray = new JSONArray(receive);
            	JSONObject json = jsonarray.getJSONObject(0);
    			
    			suc = json.optString(IS_LOGIN).toString();
    			email = json.optString(EMAIL).toString();
        		point = json.optString(POINT).toString();
    		} catch (Exception e) {}
            
		} catch (Exception e) {
			e.printStackTrace();
			call.failure(e.getMessage());
		} finally {
            try {
                reader.close(); 
            } catch(Exception ex) {} 
        }
		
		try {
			switch (mode) {
			case LOGIN :
				if (suc.equals(SUC))
					call.success(email, point);
				else
					call.failure(FAIL);
				
			case SIGN :
			case EVENT :
			case SHOP :
				if (status == SUC_STATUS)
					call.success(receive);
				else call.failure(FAIL);
				break;
			}
			
		} catch (Exception e) {}
	}
	
	static void login(final String id, final String password, final QueryCallBack callback){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				call = callback;
				
				idData += urlEncode(USER, String.valueOf(id));
                passData += urlEncode(PASSWD, String.valueOf(password));

                connect(LOGIN);
			}
		}).start();
	}
	
	static void registerUser(final String username, final String passwd, final String email, final QueryCallBack callback){
		new Thread(new Runnable(){
			
			@Override
			public void run() {
				
				idData += urlEncode(USER, username);
                passData += urlEncode(PASSWD, passwd);
                eData += urlEncode(EMAIL, email);

                connect(SIGN);
			}
			
		}).start();
	}
	
	static void getRecentData(final Long lastmodified, final int relation, final QueryCallBack callback){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				switch(relation){
				case SHOP :	
				case EVENT :
					connect(relation);
					break;
				default: 
					callback.failure("Wrong Query - Query to unknown Table");
					return;
				}
				
				modiData += urlEncode(MODI, String.valueOf(lastmodified));
			}
        }).start();
	}
	
	static void changeEmail(final String username, final String newEmail, final QueryCallBack callback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
//					String idData = "", passData = "", email = "";
//					idData += urlEncode("username", username);
//	                passData += urlEncode("password", passwd);
//	                email += urlEncode("email", email);
//	                
//	                URL url = new URL(SERV_IP + "signup.php");
//	            	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//	            	
//	            	conn.setDoOutput(true);
//                	conn.setDoInput(true);
//                	conn.setRequestMethod("POST");
//                    wr = new OutputStreamWriter(conn.getOutputStream()); 
//            	
//	                wr.write(idData);
//	                wr.write(passData);
//	                wr.write(email);
//            		
//            		wr.flush();
//            		
//            		status = conn.getResponseCode();
//                	Log.d(TAG, "status : "+status);
//                	
//                	if (conn.getResponseCode() != SUC_STATUS) 
//                		reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//                	else reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//                	StringBuilder sb = new StringBuilder();
//                	String line = null;
//            		
//                    while ((line = reader.readLine()) != null) {
//                    	sb.append(line + ""); }
//                    receive = sb.toString();
//                    
//                    Log.d(TAG, receive);
//                    
//                    try {
//            			json = new JSONObject(receive);
//            		} catch (JSONException e) {}
                    
					
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost( SERV_IP + "update/changeEmail.php" );		
				    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				    nameValuePairs.add(new BasicNameValuePair("username", username));
				    nameValuePairs.add(new BasicNameValuePair("newEmail", newEmail));
				    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			    	//HttpResponse response = httpclient.execute(httppost);
			    	ResponseHandler<String> responseHandler = new BasicResponseHandler();
			    	String responseBody = httpclient.execute(httppost, responseHandler);
			    	callback.success(responseBody);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					callback.failure(e.getMessage());
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					callback.failure(e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					callback.failure(e.getMessage());
				} 
//				finally {
//	                try {
//	                    reader.close(); 
//	                } catch(Exception ex) {} 
//	            }
//				
//				try {
//	    			
//	    			if (status == SUC_STATUS) {
//	    				Log.d(TAG, "successed");
//	    				callback.success("success");
//	    			} else {
//	    				Log.d(TAG, "failed");
//	    				callback.failure("failure");
//				    }
//	    			
//	    		} catch (Exception e) {}
			}
        }).start();		
	}
	
	public interface QueryCallBack{
		void success(String responseBody);
		void failure(String responseBody);
		void success(String email, String point);
	}
	
	private static String urlEncode(String where, String what) {
    	String result = "";
    	
    	try {
    		result = "&" + URLEncoder.encode(where, "UTF-8") + "=" + what;
    	} catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(); } 
    	
    	return result;
    }
}