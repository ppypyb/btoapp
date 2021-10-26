package com.example.factoryapplication.common.widget;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CommonAdapter<T> extends BaseAdapter {
	private Context mContext;
	private List<T> mDatas;
	private LayoutInflater mInflater;
	private int mLayoutId;
	
	public CommonAdapter(Context context,List<T> datas,int layoutId) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		this.mDatas = datas;
		this.mLayoutId = layoutId;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder holder = ViewHolder.get(mContext, convertView, parent, mLayoutId, position);	
		convert(holder,getItem(position));
		return holder.getConvertView();
	}
	/**
	 * 给控件赋值
	 * @param holder
	 * @param t
	 */
	public abstract void convert(ViewHolder holder,T t);
}
