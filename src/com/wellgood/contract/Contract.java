package com.wellgood.contract;

/**
 * ��̬����
 * @author Administrator
 * @date 20151101
 */
public class Contract {
	/**�������ĵ�ַ ip������**/
	public static String CONNECT_HOST="http://qzhkyang.imwork.net:8080";
//	public static String CONNECT_HOST="http://111.1.8.117:8080";
	/** ����������**/
	//public static String CONNECT_HOST="http://121.41.24.19:8080";
	//public static String CONNECT_HOST="http://192.168.10.101:8080";
    public static String  servAddr = "http://112.12.17.3/";
    public static String updateXML_PATH="http://111.1.8.117:8080/downloads/updateXML";
    public static String userName="dbwl" ;
    public static String password="12345" ;
	
	/**�������Ķ˿ںţ�Ĭ��8080**/
	public static String CONNECT_PORT="8080";
	/**��¼�ɹ�**/
	public static final int LOGIN_SUCCESS=0;
	public static final int GETUSER_ID_SUCCESS = 1;
	public static final int GETHOST_ID_SUCCESS = 2;
	public static final int LOGIN_FAILED = 3;
	/** ��̨�����action "com.wellgood.backgroundservice"**/
	public static final String ACTION="com.wellgood.backgroundservice";
	//���������֮��λ���ڼ���tabhost
	public static final String INTENT_EXTRAL_TABHOSTINDEX="com.wellgood.extralTabhostIndex";
	
	/** 
	 * 
	 * ����������״̬ 
	 * **/
	/**������������**/
	public static final int ALARM_ONLINE = 10;
	/**������������**/
	public static final int ALARM_OFFLINE = 11;
	/**������������**/
	public static final int ALARM_CHEFANG = 12;
	/**������������**/
	public static final int ALARM_BUFANG = 13;
	
	/**���ͳ�����Ϣ���������ɹ�**/
	public static final int SEND_CHEFANG_MESSAGE_SUCCESS=20;
	/**���ͳ�����Ϣ��������ʧ��**/
	public static final int SEND_CHEFANG_MESSAGE_FAILED=21;
	/**���Ͳ�����Ϣ���������ɹ�**/
	public static final int SEND_BUFANG_MESSAGE_SUCCESS=22;
	/**���Ͳ�����Ϣ���������ɹ�**/
	public static final int SEND_BUFANG_MESSAGE_FAILED=23;
	/** ��ȡ��Ϣ�б�ɹ�  **/
	public static final int GET_MESSAGES_SUCCESS = 31;
	/** ��ȡ��Ϣ�б�ʧ��  **/
	public static final int GET_MESSAGES_FAILED = 32;
	//�������
	public static final int LOAD_COMPLATED = 41;
	public static final int GET_ORDER_NUM_SUCCESS = 42;
	public static final int GET_ORDER_NUM_FAILED = 43;
	/**
	 * ��ȡ0���б�ɹ�������ȡ���������б�ɹ�
	 */
	public static final int GET_REGIONLIST_0_SUCCESS=44;
	/**
	 * ��ȡһ���б�ɹ�
	 */
	
	public static final int GET_REGIONLIST_1_SUCCESS=45;
	/**
	 * ��ȡ2���б�ɹ�
	 */
	public static final int GET_REGIONLIST_2_SUCCESS=46;
	/**
	 * ��ȡ3���б�ɹ�
	 */
	public static final int GET_REGIONLIST_3_SUCCESS=47;
	/**
	 * ��ȡ0���б�ʧ��
	 */
	public static final int GET_REGIONLIST_0_FAILED=50;
	/**
	 * ��ȡ1���б�ʧ��
	 */
	public static final int GET_REGIONLIST_1_FAILED=51;
	/**
	 * ��ȡ2���б�ʧ��
	 */
	public static final int GET_REGIONLIST_2_FAILED=52;
	/**
	 * ��ȡ3���б�ʧ��
	 */
	public static final int GET_REGIONLIST_3_FAILED=53;

	public static final int PAY_VIP_SUCCESS=54;
	
	public static final int PAY_VIP_FAILED=55;
	public static final int REGISTE_SUCCESS=56;
	public static final int FEEDBACK_SUCCESS=56;
	
}
