package com.wellgood.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;
import com.wellgood.adapter.NodeAdapter;
import com.wellgood.application.APP;
import com.wellgood.engineer.R;
import com.wellgood.model.TaskEntity;
import com.wellgood.model.TaskNodeEntity;
import com.wellgood.model.TempData;
import com.wellgood.pulltorefresh.PullToRefreshView;
import com.wellgood.pulltorefresh.PullToRefreshView.OnFooterRefreshListener;
import com.wellgood.pulltorefresh.PullToRefreshView.OnHeaderRefreshListener;

/**
 * 任务详情
 * 
 * @author zhaojizhuang
 * 
 */
public class TaskInfoActivity extends Activity implements OnItemClickListener,
		OnHeaderRefreshListener, OnFooterRefreshListener {
	public static String CLASS_NAME = "TaskInfoActivity";
	// 任务详情
	TaskEntity taskinfo;
	TextView taskInfoTitle;
	ForeignCollection<TaskNodeEntity> taskNodeEntities;
	List<TaskNodeEntity> taskNodeList=new ArrayList<TaskNodeEntity>();
	ListView listView;
	PullToRefreshView mPullToRefreshView;
	NodeAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_task_info);

		APP.getIns().addActivity(this);
		taskinfo = TempData.getIns().getTaskEntity();
		Log.d(CLASS_NAME, "任务详情：" + taskinfo);
		Log.d(CLASS_NAME, "任务节点" + taskinfo.getTaskNodes());
		initView();
	}

	public void initView() {
		taskInfoTitle = (TextView) findViewById(R.id.task_info_title);
		// 设置任务标题
		taskInfoTitle.setText(taskinfo.getName());
		listView = (ListView) findViewById(R.id.nodesListview);
		mPullToRefreshView=(PullToRefreshView) findViewById(R.id.task_info_pull_refresh_view);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		
		refreshNodeData();
		
		// taskNodeEntities=
		 adapter=new NodeAdapter(this, taskNodeList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}
//更新数据，更新taskNodeList
	public void refreshNodeData() {
		taskNodeEntities = taskinfo.getTaskNodes();

		CloseableIterator<TaskNodeEntity> iterator = taskNodeEntities
				.closeableIterator();
		Log.d(CLASS_NAME, "任务有下面几个节点");
		try {
			taskNodeList = null;
			taskNodeList = new ArrayList<TaskNodeEntity>();
			while (iterator.hasNext()) {
				TaskNodeEntity tasknode = iterator.next();
				Log.d(CLASS_NAME, "节点：" + tasknode);

				taskNodeList.add(tasknode);
			}
		} finally {
			try {
				iterator.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void onClick(View view) {
		switch (view.getId()) {
		// 返回按钮
		case R.id.task_info_return:
			this.finish();
			break;

		default:
			break;
		}

	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		mPullToRefreshView.onFooterRefreshComplete();
		mPullToRefreshView.onHeaderRefreshComplete();
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		mPullToRefreshView.onFooterRefreshComplete();
		mPullToRefreshView.onHeaderRefreshComplete();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "点击了"+arg2, Toast.LENGTH_LONG).show();
	}

}
