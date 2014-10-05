package com.planner.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView idText, passText;
	private Button loginB;
	
	protected void checkUser(CharSequence id, CharSequence pass) {
		if (id.equals("a") && pass.equals("a"))
			startActivity(new Intent(MainActivity.this, HomeActivity.class)); 
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		idText = (TextView) findViewById(R.id.idText);
		passText = (TextView) findViewById(R.id.passText);
		loginB = (Button) findViewById(R.id.loginB);
		
		loginB.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkUser(idText.getText(), passText.getText());
			}
		});
	}
}