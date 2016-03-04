package com.wellgood.activity;

import com.wellgood.application.APP;
import com.wellgood.engineer.R;
import com.wellgood.engineer.R.layout;
import com.wellgood.engineer.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
/**
 * 注册
 * @author zhaojizhuang
 *
 */
public class RegisteActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_registe);
		APP.getIns().addActivity(this);
		
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.try_checknumber:

			break;
		case R.id.registe_button:

			break;
		case R.id.registe_return:
			this.finish();
			break;
		default:
			break;
		}
	}

}
