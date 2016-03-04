package com.wellgood.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.alipay.android.phone.mrpc.core.u;
import com.wellgood.DAO.TaskEntityDAO;
import com.wellgood.DAO.TaskNodeEntityDAO;
import com.wellgood.DAO.UserDao;
import com.wellgood.activity.MainActivity;
import com.wellgood.activity.TaskInfoActivity;
import com.wellgood.adapter.TaskAdapter;
import com.wellgood.application.APP;
import com.wellgood.engineer.R;
import com.wellgood.model.TaskEntity;
import com.wellgood.model.TaskNodeEntity;
import com.wellgood.model.TempData;
import com.wellgood.model.User;
import com.wellgood.pulltorefresh.PullToRefreshView;
import com.wellgood.pulltorefresh.PullToRefreshView.OnFooterRefreshListener;
import com.wellgood.pulltorefresh.PullToRefreshView.OnHeaderRefreshListener;

public class TaskAccept extends Fragment implements OnItemClickListener,OnHeaderRefreshListener,OnFooterRefreshListener{
	public static String CLASS_NAME="TaskAccept";
	View view;
	List<TaskEntity> taskEntities;
	ListView listView;
	PullToRefreshView mPullToRefreshView;
	TaskAdapter adapter;
	// 消息列表
	public  ArrayList<ContentValues> messagesList=new ArrayList<ContentValues>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view==null) {
			view = inflater.inflate(R.layout.fragment_taskaccept, container, false);
		}
		
		//缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        } 
        
		User user=new User();
		user.setId(0);
		user.setName("wwww");
		new UserDao(getActivity()).add(user);
		List<User> users=new UserDao(getActivity()).queryForAll();
		Log.i(CLASS_NAME, "得到的users"+user);
		
		initView();
		
		return view;
	}

	public void initView(){
		listView=(ListView) view.findViewById(R.id.taskacceptlistview);
//		ContentValues values=new ContentValues();
//		values.put("msg_type", "111");
//		values.put("msg_src", "111");
//		values.put("msg_sum", "111");
//		values.put("msg_content", "111");
//		values.put("modifyDate", "111");
//		messagesList.add(values);
//		messagesList.add(values);
//		messagesList.add(values);
//		messagesList.add(values);
//		messagesList.add(values);
		taskEntities=new TaskEntityDAO(getActivity()).queryForAll();

		Log.d(CLASS_NAME, "tasklist:"+taskEntities);
		adapter = new TaskAdapter(getActivity(), taskEntities);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		
		
		mPullToRefreshView = (PullToRefreshView) view
				.findViewById(R.id.main_pull_refresh_view);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		
	}
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mPullToRefreshView.onFooterRefreshComplete();
		mPullToRefreshView.onHeaderRefreshComplete();
		
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mPullToRefreshView.onFooterRefreshComplete();
		mPullToRefreshView.onHeaderRefreshComplete();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), "点击了", Toast.LENGTH_LONG).show();
		//点击了当前的任务，设置缓存数据用于跳转
		
		TempData.getIns().setTaskEntity(taskEntities.get(arg2));
		Log.d(CLASS_NAME, "传递的数据："+TempData.getIns());
		Intent intent =new Intent(getActivity(),TaskInfoActivity.class);
		startActivity(intent);
	}

}
