package com.wellgood.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.wellgood.model.User;

/**
 * �������ݷ��ʶ��󣬶�ָ���ı������ɾ�Ĳ����
 * @author 
 *
 */
public class UserDao {
	public static String TAG="SQLite";
    private Dao<User, Integer> userDao;
    private DatabaseHelper dbHelper;

    /**
     * ���췽��
     * ������ݿ������ʵ����ͨ������Class����õ���Ӧ��Dao
     * @param context
     */
    public UserDao(Context context) {
        try {
        	Log.i(TAG, "UserDAOʵ����");
            dbHelper = DatabaseHelper.getHelper(context);
            userDao = dbHelper.getDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ���һ����¼
     * @param theme
     */
    public void add(User user) {
    	
        try {
        	Log.i(TAG, "���һ����¼:"+user);
            userDao.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ɾ��һ����¼
     * @param theme
     */
    public void delete(User user) {
        try {
        	Log.i(TAG, "ɾ��һ����¼:"+user);
            userDao.delete(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * ����һ����¼
     * @param theme
     */
    public void update(User user) {
    	Log.i(TAG, "����һ����¼:"+user);
        try {
            userDao.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ѯһ����¼
     * @param id
     * @return
     */
    public User queryForId(int id) {
    	Log.i(TAG, "��ѯһ����¼������id:"+id);
        User user = null;
        try {
            user = userDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    /**
     * ��ѯ���м�¼
     * @return
     */
    public List<User> queryForAll() {
    	Log.i(TAG, "��ѯuser���м�¼");
        List<User> users = new ArrayList<User>();
        try {
            users = userDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}