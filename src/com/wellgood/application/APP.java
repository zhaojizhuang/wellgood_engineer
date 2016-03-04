package com.wellgood.application;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;

import com.android.pc.ioc.app.Ioc;
import com.android.pc.util.Handler_SharedPreferences;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.tencent.android.tpush.XGNotifaction;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushNotifactionCallback;

/**
 * ���ȫ�ֱ��� {@link SharedPreferences} ����
 * 
 * @author ZhaoJizhuang
 * 
 */
public class APP extends Application {
	public static String CLASS_NAME = "APP";
	public static APP app;
	//activity�б������˳���������
	private List<Activity> mList = new LinkedList<Activity>();
	// �ٶȶ�λclient
	public LocationClient mLocationClient;
	// λ�ü���listener
	public MyLocationListener mMyLocationListener;
	// ��
	public Vibrator mVibrator;

	// ��·״̬��Ϊ�յĻ���ʾ����
	public static NetworkInfo netInfo;
	// �����Ͷ���
	public static Boolean NetAvalible;
	// ����״̬������
	BroadcastReceiver netBroadcastReceiver = new MyReceiver();

	// ��ַ����
	public static String addr_info = null;
	// ����
	public static String Longitude = null;
	// γ��
	public static String Latitude = null;
	@Override
	public void onCreate() {

		// mLocationClient = new LocationClient(this.getApplicationContext());
		// mMyLocationListener = new MyLocationListener();
		// ע��λ�ü�����
		// mLocationClient.registerLocationListener(mMyLocationListener);
		mVibrator = (Vibrator) getApplicationContext().getSystemService(
				Service.VIBRATOR_SERVICE);

		app = this;
		// ��ʼ��ioc���
		// Ioc.getIoc().init(this);
		super.onCreate();
		// mLocationClient.start();
		registerDateTransReceiver();
		netInfo = getNetStatus();
		if (netInfo != null) {
			NetAvalible = true;
		}

		// �������������Ÿ���ص�����
		if (isMainProcess()) {
			// Ϊ��֤����֪ͨǰһ�����ñ���������Ҫ��application��onCreateע��
			// �յ�֪ͨʱ������ñ��ص�������
			// �൱������ص����������Ÿ�ĵ���֪֮ͨǰ����ȡ
			// һ���������Ҫ��ȡ֪ͨ���ݡ����⣬����֪ͨ�������ת�߼��ȵ�
			XGPushManager
					.setNotifactionCallback(new XGPushNotifactionCallback() {

						@Override
						public void handleNotify(XGNotifaction xGNotifaction) {
							Log.i("test", "�����Ÿ�֪ͨ��" + xGNotifaction);
							// ��ȡ��ǩ�����ݡ��Զ�������
							String title = xGNotifaction.getTitle();
							String content = xGNotifaction.getContent();
							String customContent = xGNotifaction
									.getCustomContent();
							// �����Ĵ���
							// �����Ҫ����֪ͨ����ֱ�ӵ������´�����Լ�����Notifaction�����򣬱�֪ͨ�����ᵯ����֪ͨ���С�
							xGNotifaction.doNotify();
						}
					});
		}
	}

	public boolean isMainProcess() {
		ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
		List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		String mainProcessName = getPackageName();
		int myPid = android.os.Process.myPid();
		for (RunningAppProcessInfo info : processInfos) {
			if (info.pid == myPid && mainProcessName.equals(info.processName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ��ȡ��¼�豸mac��ַ
	 * 
	 * @return
	 */
	public String getMac() {
		WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		String mac = wm.getConnectionInfo().getMacAddress();
		return mac == null ? "" : mac;
	}

	/**
	 * ���ݴ洢���������ݿ� ������loonAndroid�Ĺ��߿�� android.pc.utils���
	 * 
	 * @author Windows 7
	 * @param key
	 * @param value
	 * @return void
	 */
	public void setData(String key, String value) {
		Handler_SharedPreferences.WriteSharedPreferences(
				getApplicationContext(), "Cache", key, value);
	}

	/**
	 * ȡ���������� ������loonAndroid�Ĺ��߿�� android.pc.utils���
	 * 
	 * @author Windows 7
	 * @param key
	 * @return
	 * @return String
	 */
	public String getData(String key) {
		return Handler_SharedPreferences.getValueByName(
				getApplicationContext(), "Cache", key,
				Handler_SharedPreferences.STRING).toString();
	}

	/*
	 * �õ�appʵ��
	 */
	public static APP getIns() {
		return app;
	}
	/**
	 * ÿ��activityoncreate��ʱ�����
	 * @param activity
	 */
	public void addActivity(Activity activity) {
	      mList.add(activity);
	}
/**
 * ����ϵͳ�˳���ʱ�����
 */
	    public void exit() {
	        try {
	           for (Activity activity : mList) {
	              if (activity != null)
	                 activity.finish();
	                 }
	             } catch (Exception e) {
	                 e.printStackTrace();
	             } finally {
	                System.exit(0);
	             }
	}
	public NetworkInfo getNetStatus() {
		NetworkInfo info = getnetstatus();
		return info;
	}

	private NetworkInfo getnetstatus() {
		ConnectivityManager mConnMgr;
		mConnMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo aActiveInfo = mConnMgr.getActiveNetworkInfo(); // ��ȡ�����������Ϣ
		return aActiveInfo;
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
		filter.setPriority(1000);
		registerReceiver(netBroadcastReceiver, filter);
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

				netInfo = getNetStatus();
				if (netInfo != null) {
					NetAvalible = true;
					Log.d(CLASS_NAME, "�������");
				} else {
					Log.d(CLASS_NAME, "�Ѿ�����");
					NetAvalible = false;
				}
				Log.d(CLASS_NAME, "aActiveInfo" + netInfo);
				return;
			}
		}

	}

	/**
	 * ʵ��ʵʱλ�ûص�����
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());

			Longitude = "" + location.getLongitude();
			// γ��
			Latitude = "" + location.getLatitude();

			if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS��λ���
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());// ��λ������ÿСʱ
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\nheight : ");
				sb.append(location.getAltitude());// ��λ����
				sb.append("\ndirection : ");
				sb.append(location.getDirection());
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());

				sb.append("\ndescribe : ");
				sb.append("gps��λ�ɹ�");

			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// ���綨λ���
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// ��Ӫ����Ϣ
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				sb.append("\ndescribe : ");
				sb.append("���綨λ�ɹ�");
			} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
				sb.append("\ndescribe : ");
				sb.append("���߶�λ�ɹ������߶�λ���Ҳ����Ч��");
			} else if (location.getLocType() == BDLocation.TypeServerError) {
				sb.append("\ndescribe : ");
				sb.append("��������綨λʧ�ܣ����Է���IMEI�źʹ��嶨λʱ�䵽loc-bugs@baidu.com��������׷��ԭ��");
			} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
				sb.append("\ndescribe : ");
				sb.append("���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");
			} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
				sb.append("\ndescribe : ");
				sb.append("�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ�����������������ֻ�");
			}
			addr_info = location.getAddrStr();
			Log.i("APP", sb.toString());
			Log.d(CLASS_NAME, Longitude + ";" + Latitude + ":" + addr_info);
			// mLocationClient.setEnableGpsRealTimeTransfer(true);
		}

	}
	@Override
	public void onLowMemory() {
	    super.onLowMemory();
	      System.gc();
	}
}
