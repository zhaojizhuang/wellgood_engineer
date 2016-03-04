package com.wellgood.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wellgood.engineer.R;
import com.wellgood.model.TaskEntity;
import com.wellgood.model.TaskNodeEntity;

/*
 * Author: zhaojizhuang
 * Created Date:2015-5-8
 * Copyright @ 2015 BU
 * Description: »ŒŒÒ  ≈‰∆˜
 *
 * History:
 */
public class NodeAdapter extends CommonAdapter {
	  static class ViewHolder {
	        TextView msg_src;
	        TextView msg_sum;
	        TextView modifyDate;
	    }

	private LayoutInflater inflater;
	//private int count=10;
	private List<TaskNodeEntity> data;
	public NodeAdapter(Context context, List<TaskNodeEntity> data) {
		inflater = LayoutInflater.from(context);
		this.activity = context;
		this.data = data;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public View view(int position, View convertView, ViewGroup parent) {
		
		ViewHolder mViewHolder;
		if (convertView == null) {
			mViewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.task_listview_item, null);
			mViewHolder.msg_src=(TextView) convertView.findViewById(R.id.msg_src);
			mViewHolder.msg_sum=(TextView) convertView.findViewById(R.id.msg_sum);
			mViewHolder.modifyDate=(TextView) convertView.findViewById(R.id.modifyDate);
			convertView.setTag(mViewHolder);
			
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		mViewHolder.msg_src.setText(data.get(position).getId()+"");
		mViewHolder.msg_sum.setText(data.get(position).getName());
		//mViewHolder.modifyDate.setText(data.get(position).getTime());
//		mViewHolder.msg_src.setText(data.get(position).getId());
//		mViewHolder.msg_sum.setText(data.get(position).getName());
//		mViewHolder.modifyDate.setText(data.get(position).getTime());
			//mViewHolder.mImageView.setImageResource(R.drawable.friends_sends_pictures_no);
		//mViewHolder.mImageView.setImageResource(((GridItem)getItem(position)).getImageID());
		return convertView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return super.getView(position, convertView, parent);
	}


}