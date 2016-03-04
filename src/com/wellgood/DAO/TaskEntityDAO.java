package com.wellgood.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.wellgood.model.TaskEntity;

/**
 * �������ݷ��ʶ��󣬶�ָ���ı������ɾ�Ĳ����
 * @author 
 *
 */
public class TaskEntityDAO {
	public static String TAG="SQLite";
    private Dao<TaskEntity, Integer> taskEntityDao;
    private DatabaseHelper dbHelper;

    /**
     * ���췽��
     * ������ݿ������ʵ����ͨ������Class����õ���Ӧ��Dao
     * @param context
     */
    public TaskEntityDAO(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            taskEntityDao = dbHelper.getDao(TaskEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ���һ����¼
     * @param theme
     */
    public void add(TaskEntity TaskEntity) {
    	
        try {
            taskEntityDao.create(TaskEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ɾ��һ����¼
     * @param theme
     */
    public void delete(TaskEntity TaskEntity) {
        try {
            taskEntityDao.delete(TaskEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * ����һ����¼
     * @param theme
     */
    public void update(TaskEntity TaskEntity) {
        try {
            taskEntityDao.update(TaskEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ѯһ����¼
     * @param id
     * @return
     */
    public TaskEntity queryForId(int id) {
    	Log.i(TAG, "��ѯһ����¼������id:"+id);
        TaskEntity TaskEntity = null;
        try {
            TaskEntity = taskEntityDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return TaskEntity;
    }


    /**
     * ��ѯ���м�¼
     * @return
     */
    public List<TaskEntity> queryForAll() {
    	Log.i(TAG, "��ѯTaskEntity���м�¼");
        List<TaskEntity> TaskEntitys = new ArrayList<TaskEntity>();
        try {
            TaskEntitys = taskEntityDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return TaskEntitys;
    }

}