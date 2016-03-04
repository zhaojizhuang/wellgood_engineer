package com.wellgood.activity;

import com.wellgood.application.APP;
import com.wellgood.contract.Contract;
import com.wellgood.engineer.R;
import com.wellgood.engineer.R.layout;
import com.wellgood.engineer.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
/**
 *忘记密码
 * @author zhaojizhuang
 *
 */
public class ResetPassword extends Activity {
	public static String CLASS_NAME="ResetPassword";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reset_password);
		APP.getIns().addActivity(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reset_password, menu);
		return true;
	}
	
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.resetpwd_button:
			Log.d(CLASS_NAME, "点击了确认");
			//Intent intent=new Intent(LoginActivity.this, RegisteActivity.class);
			//startActivity(intent);
		case R.id.reset_password_return:
			this.finish();
			break;
		default:
			break;
		}
		
		
	}
}
