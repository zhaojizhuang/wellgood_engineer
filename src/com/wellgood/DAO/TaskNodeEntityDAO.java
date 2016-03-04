package com.wellgood.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.wellgood.model.TaskEntity;
import com.wellgood.model.TaskNodeEntity;

/**
 * �������ݷ��ʶ��󣬶�ָ���ı������ɾ�Ĳ����
 * @author 
 *
 */
public class TaskNodeEntityDAO {
	public static String TAG="SQLite";
    private Dao<TaskNodeEntity, Integer> taskNodeEntityDao;
    private DatabaseHelper dbHelper;

    /**
     * ���췽��
     * ������ݿ������ʵ����ͨ������Class����õ���Ӧ��Dao
     * @param context
     */
    public TaskNodeEntityDAO(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            taskNodeEntityDao = dbHelper.getDao(TaskNodeEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ���һ����¼
     * @param theme
     */
    public void add(TaskNodeEntity taskNodeEntity) {
    	
        try {
        	taskNodeEntityDao.create(taskNodeEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ɾ��һ����¼
     * @param theme
     */
    public void delete(TaskNodeEntity taskNodeEntity) {
        try {
            taskNodeEntityDao.delete(taskNodeEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * ����һ����¼
     * @param theme
     */
    public void update(TaskNodeEntity taskEntity) {
        try {
            taskNodeEntityDao.update(taskEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ѯһ����¼
     * @param id
     * @return
     */
    public TaskNodeEntity queryForId(int id) {
    	Log.i(TAG, "��ѯһ����¼������id:"+id);
        TaskNodeEntity taskNodeEntity = null;
        try {
            taskNodeEntity = taskNodeEntityDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskNodeEntity;
    }


    /**
     * ��ѯ���м�¼
     * @return
     */
    public List<TaskNodeEntity> queryForAll() {
    	Log.i(TAG, "��ѯTaskNodeEntity���м�¼");
        List<TaskNodeEntity> taskNodeEntitys = new ArrayList<TaskNodeEntity>();
        try {
        	taskNodeEntitys = taskNodeEntityDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskNodeEntitys;
    }

}