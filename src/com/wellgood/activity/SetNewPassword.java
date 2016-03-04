package com.wellgood.activity;

import com.wellgood.application.APP;
import com.wellgood.engineer.R;
import com.wellgood.engineer.R.layout;
import com.wellgood.engineer.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

public class SetNewPassword extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_set_new_password);
		APP.getIns().addActivity(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_new_password, menu);
		return true;
	}

}
