package com.wellgood.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
import com.wellgood.DAO.TaskEntityDAO;
import com.wellgood.DAO.TaskNodeEntityDAO;
import com.wellgood.application.APP;
import com.wellgood.contract.Contract;
import com.wellgood.engineer.R;
import com.wellgood.engineer.R.id;
import com.wellgood.fragment.Message;
import com.wellgood.fragment.Mysetting;
import com.wellgood.fragment.Task;
import com.wellgood.model.TaskEntity;
import com.wellgood.model.TaskNodeEntity;

/**
 * @author zhaojizhuang 
 * @discription ������������������� maintab ͨ������5��fragment ����fragmenttabhost�����л�
 */
public class MainActivity extends FragmentActivity {
	
	public static String CLASS_NAME;
	
	//��ʼ��λ���ĸ�tab
	int tabIndex=-1;            				//	��ʼ��λ���ڶ���
	/** �ٶȶ�λ��صĶ��� **/
	
	// ��λ���� �߾���
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	// ��λ����ϵ
	private String tempcoor = "gcj02";
	// ��λclient
	private LocationClient mLocationClient;

	// ����FragmentTabHost���󣬲���
	//@InjectView(android.R.id.tabhost)
	private FragmentTabHost mTabHost;

	// ����һ������
	private LayoutInflater layoutInflater;
	// ������ն�����recevier
	private BroadcastReceiver netBroadcastReceiver = new MyReceiver();
	// �������������Fragment����
	private Class fragmentArray[] = {

			Task.class, 					// �������
			Message.class, 		// ���˰��
			Mysetting.class, 				// �������
		
	};

	// �������������tab��ťͼƬ
	private int mImageViewArray[] = {
			R.drawable.tab_public_btn,		//����
			R.drawable.tab_message_btn, 	//��Ϣ
			R.drawable.tab_settings_btn };	//����

	// Tabѡ�������
	private String mTextviewArray[] = { "����", "��Ϣ", "�ҵ�" };

	// ���캯���õ�����
	public MainActivity() {
		CLASS_NAME = getClass().getName();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȡ��������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_tab_layout);
		APP.getIns().addActivity(this);
		// ��ʼ��tabhost
		initView();
		// ��λ���ڼ���tabhost
		tabIndex=getIntent().getIntExtra(Contract.INTENT_EXTRAL_TABHOSTINDEX, 1);
		if ((tabIndex>-1)&&(tabIndex<fragmentArray.length)) {
			mTabHost.setCurrentTab(tabIndex);
			Log.d(CLASS_NAME, "����һ����������ֵ����ǰindex����Ϊ��ʾ�ڣ�"+tabIndex+"��");
		}
		// ��ʼ��baidu��ͼ��λ�ͻ���
		//mLocationClient = ((APP) getApplication()).mLocationClient;
		//initLocation();

		//mLocationClient.start();// ��λSDK
								// start֮���Ĭ�Ϸ���һ�ζ�λ���󣬿����������ж�isstart����������request
		//mLocationClient.requestLocation();

		// ע��ӿ�
		XGPushManager.registerPush(getApplicationContext(),"201510272052070169",
				new XGIOperateCallback() {
					@Override
					public void onSuccess(Object data, int flag) {
						Log.w(Constants.LogTag,
								"+++ register push sucess. token:" + data);
//						m.obj = "+++ register push sucess. token:" + data;
//						m.sendToTarget();
					}

					@Override
					public void onFail(Object data, int errCode, String msg) {
						Log.w(Constants.LogTag,
								"+++ register push fail. token:" + data
										+ ", errCode:" + errCode + ",msg:"
										+ msg);
//
//						m.obj = "+++ register push fail. token:" + data
//								+ ", errCode:" + errCode + ",msg:" + msg;
//						m.sendToTarget();
					}
				});
		
		
	//	testData();
		

	}

	
	public void testData(){
		for (int i = 0; i < 5; i++) {
			TaskEntity taskEntity=new TaskEntity();
			taskEntity.setId(i);
			taskEntity.setName("name"+i);
			new TaskEntityDAO(this).add(taskEntity);
			for (int j = 0; j < 3; j++) {
				TaskNodeEntity taskNode=new TaskNodeEntity();
				taskNode.setId(j);
				taskNode.setName("nodename"+j);
				taskNode.setTaskinfo(taskEntity);
				new TaskNodeEntityDAO(this).add(taskNode);
				taskNode=null;
			}
			taskEntity=null;
			
		}
		
		
	}
	/**
	 * ���ؼ����˳�
	 */

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {

				// forceStopAPK("com.wellgood.activity");
				exitAll();

			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * �˳�����Ӧ��,����service
	 */
	public void exitAllWithoutService() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	/**
	 * �˳�����Ӧ��
	 */
	public void exitAll() {
		//stopService(new Intent(MainActivity.this, BackGroundService.class));
		APP.getIns().exit();

	}

	private void initView() {
		// ʵ�������ֶ���
		layoutInflater = LayoutInflater.from(this);
		mTabHost=(FragmentTabHost) findViewById(android.R.id.tabhost);
		// ʵ����TabHost���󣬵õ�TabHost
		// mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		// setup�ڼ�����view֮�����
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		// �õ�fragment�ĸ���
		int count = fragmentArray.length;

		for (int i = 0; i < count; i++) {
			// Ϊÿһ��Tab��ť����ͼ�ꡢ���ֺ�����
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			// ��Tab��ť��ӽ�Tabѡ��� ��fragment
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			// ����Tab��ť�ı���
			mTabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.selector_tab_background);

		}
		// ����Ĭ�ϵ�������ʾtab
		mTabHost.setCurrentTab(2);

	}

	/**
	 * ��Tab��ť����ͼ�������
	 */
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);

		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextviewArray[index]);
		return view;
	}

	/**
	 * ��������״̬��receiver ��action
	 */
	public static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

	/**
	 * ע����������receiver
	 */
	private void registerDateTransReceiver() {
		Log.d(CLASS_NAME, "register receiver " + CONNECTIVITY_CHANGE_ACTION);
		IntentFilter filter = new IntentFilter();
		filter.addAction(CONNECTIVITY_CHANGE_ACTION);
		// ��apppstart�е�recevier����
		filter.setPriority(1002);
		registerReceiver(netBroadcastReceiver, filter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		//Intent intent = new Intent(MainActivity.this, BackGroundService.class);
		//stopService(intent);
		super.onDestroy();
		// unregisterReceiver(netBroadcastReceiver);
	}

	/**
	 * ��������״̬��receiver������״̬һ�����仯�ͻ����
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			Log.d(CLASS_NAME, "PfDataTransReceiver receive action " + action);
			if (TextUtils.equals(action, CONNECTIVITY_CHANGE_ACTION)) {// ����仯��ʱ��ᷢ��֪ͨ
				Log.d(CLASS_NAME, "����仯��");
				ConnectivityManager mConnMgr = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo aActiveInfo = mConnMgr.getActiveNetworkInfo(); // ��ȡ�����������Ϣ
				Log.d(CLASS_NAME, "aActiveInfo" + aActiveInfo);
				// ������
				if (aActiveInfo == null) {
					Log.e(CLASS_NAME, "������");
					dialog();
					// dialog(context);
				}
				// ��������
				else {
					Log.e(CLASS_NAME, "������");

				}
				return;
			}
		}

	}

	// �ɶԳ���
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		registerDateTransReceiver();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(netBroadcastReceiver);
	}

	/** ȷ��dialog **/
	private void dialog() {
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setMessage("���ڶ���״̬��������������");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// ��ת����������
				Intent wifiSettingsIntent = new Intent(
						"android.settings.WIFI_SETTINGS");
				startActivity(wifiSettingsIntent);

			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});

		builder.create().show();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub

	//	mLocationClient.stop();
		super.onStop();
	}
	/*
	 * ��ʼ��location ������
	 */
	private void initLocation() {
		Log.d(CLASS_NAME, "initLocation()");
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);// ��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType(tempcoor);// ��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ��
		int span = 1000;
		option.setScanSpan(span);// ��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(true);// ��ѡ�������Ƿ���Ҫ��ַ��Ϣ
		option.setOpenGps(true);// ��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setLocationNotify(true);// ��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		option.setIgnoreKillProcess(true);// ��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��

		mLocationClient.setLocOption(option);
		Log.d(CLASS_NAME, "initLocation");
	}

}
