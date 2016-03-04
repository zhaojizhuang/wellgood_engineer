package com.wellgood.contract;

/**
 * 静态常量
 * @author Administrator
 * @date 20151101
 */
public class Contract {
	/**服务器的地址 ip或域名**/
	public static String CONNECT_HOST="http://qzhkyang.imwork.net:8080";
//	public static String CONNECT_HOST="http://111.1.8.117:8080";
	/** 舒梦云主机**/
	//public static String CONNECT_HOST="http://121.41.24.19:8080";
	//public static String CONNECT_HOST="http://192.168.10.101:8080";
    public static String  servAddr = "http://112.12.17.3/";
    public static String updateXML_PATH="http://111.1.8.117:8080/downloads/updateXML";
    public static String userName="dbwl" ;
    public static String password="12345" ;
	
	/**服务器的端口号，默认8080**/
	public static String CONNECT_PORT="8080";
	/**登录成功**/
	public static final int LOGIN_SUCCESS=0;
	public static final int GETUSER_ID_SUCCESS = 1;
	public static final int GETHOST_ID_SUCCESS = 2;
	public static final int LOGIN_FAILED = 3;
	/** 后台服务的action "com.wellgood.backgroundservice"**/
	public static final String ACTION="com.wellgood.backgroundservice";
	//主界面进入之后定位到第几个tabhost
	public static final String INTENT_EXTRAL_TABHOSTINDEX="com.wellgood.extralTabhostIndex";
	
	/** 
	 * 
	 * 报警主机的状态 
	 * **/
	/**报警主机在线**/
	public static final int ALARM_ONLINE = 10;
	/**报警主机离线**/
	public static final int ALARM_OFFLINE = 11;
	/**报警主机撤防**/
	public static final int ALARM_CHEFANG = 12;
	/**报警主机布放**/
	public static final int ALARM_BUFANG = 13;
	
	/**发送撤防信息到服务器成功**/
	public static final int SEND_CHEFANG_MESSAGE_SUCCESS=20;
	/**发送撤防信息到服务器失败**/
	public static final int SEND_CHEFANG_MESSAGE_FAILED=21;
	/**发送布防信息到服务器成功**/
	public static final int SEND_BUFANG_MESSAGE_SUCCESS=22;
	/**发送布防信息到服务器成功**/
	public static final int SEND_BUFANG_MESSAGE_FAILED=23;
	/** 获取信息列表成功  **/
	public static final int GET_MESSAGES_SUCCESS = 31;
	/** 获取信息列表失败  **/
	public static final int GET_MESSAGES_FAILED = 32;
	//加载完成
	public static final int LOAD_COMPLATED = 41;
	public static final int GET_ORDER_NUM_SUCCESS = 42;
	public static final int GET_ORDER_NUM_FAILED = 43;
	/**
	 * 获取0级列表成功，即获取控制中心列表成功
	 */
	public static final int GET_REGIONLIST_0_SUCCESS=44;
	/**
	 * 获取一级列表成功
	 */
	
	public static final int GET_REGIONLIST_1_SUCCESS=45;
	/**
	 * 获取2级列表成功
	 */
	public static final int GET_REGIONLIST_2_SUCCESS=46;
	/**
	 * 获取3级列表成功
	 */
	public static final int GET_REGIONLIST_3_SUCCESS=47;
	/**
	 * 获取0级列表失败
	 */
	public static final int GET_REGIONLIST_0_FAILED=50;
	/**
	 * 获取1级列表失败
	 */
	public static final int GET_REGIONLIST_1_FAILED=51;
	/**
	 * 获取2级列表失败
	 */
	public static final int GET_REGIONLIST_2_FAILED=52;
	/**
	 * 获取3级列表失败
	 */
	public static final int GET_REGIONLIST_3_FAILED=53;

	public static final int PAY_VIP_SUCCESS=54;
	
	public static final int PAY_VIP_FAILED=55;
	public static final int REGISTE_SUCCESS=56;
	public static final int FEEDBACK_SUCCESS=56;
	
}
