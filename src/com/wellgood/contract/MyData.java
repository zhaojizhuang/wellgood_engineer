package com.wellgood.contract;

import com.wellgood.application.APP;

/**ȫ�ֱ�����������
 * ���ڽ����õı����浽sheredpreference**/
public class MyData {
	/**�洢�û���������**/
	public static void saveName(String name){
		APP.app.setData("usr_name",name );
	}
	/**ȡ��������û���**/
	public static String getName(){
		String name =APP.app.getData("usr_name");
		return name;
	}
	/**�洢�û���������**/
	public static void savePassword(String password){
		APP.app.setData("usr_password",password );
	}
	/**ȡ��������û���**/
	public static String getPassword(){
		String name =APP.app.getData("usr_password");
		return name;
	}
	
	
	
	/**�洢��ס�˺�״̬������**/
	public static void saveRememberFlag(Boolean isRemember){
		String temp=Boolean.toString(isRemember);
		APP.app.setData("isRemember",temp );
	}
	/**ȡ�ü�ס�˺�״̬ **/
	public static Boolean getRememberFlag(){
		String temp=APP.app.getData("isRemember");
		Boolean isRemeber=Boolean.valueOf(temp).booleanValue();
		return isRemeber;
	}
	
	/**�洢�û���������**/
	public static void saveHostID(String host_id){
		APP.app.setData("host_id",host_id );
	}
	/**ȡ��������û���**/
	public static String getHostID(){
		String host_id =APP.app.getData("host_id");
		return host_id;
	}
	/**�洢����ͷid������**/
	public static void saveCameraID(String cameraID){
		APP.app.setData("cameraID",cameraID );
	}
	/**ȡ�����������ͷid**/
	public static String getCameraID(){
		String host_id =APP.app.getData("cameraID");
		return host_id;
	}
	/**�洢�û�user_id������**/
	public static void saveUserID(String user_id){
		APP.app.setData("user_id",user_id );
	}
	/**ȡ��������û�user_id**/
	public static String getUserID(){
		String user_id =APP.app.getData("user_id");
		return user_id;
	}
	/**�洢��ס����״̬������**/
	public static void saveHostStatus(Boolean host_status){
		String temp=Boolean.toString(host_status);
		APP.app.setData("host_status",temp );
	}
	/**ȡ�ü�ס����״̬ **/
	public static Boolean getHostStatus(){
		String temp=APP.app.getData("host_status");
		Boolean host_status=Boolean.valueOf(temp).booleanValue();
		return host_status;
	}
	/**�洢��ס������״̬������**/
	public static void saveRCStatus(Boolean RC_status){
		String temp=Boolean.toString(RC_status);
		APP.app.setData("RC_status",temp );
	}
	/**ȡ�ü�ס������״̬ **/
	public static Boolean getRCStatus(){
		String temp=APP.app.getData("RC_status");
		Boolean RC_status=Boolean.valueOf(temp).booleanValue();
		return RC_status;
	}
	
}
