package com.example.factoryapplication.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.example.factoryapplication.R;


public class LoadListView extends ListView implements OnScrollListener{

	private View footer; //底部布局
	private int totalItemCount;	//总数量
	private int lastVisibleItem;	//最后一个可见的item
	private Boolean isLoading = false;	//加载状态
	private ILoadListener iLoadListener;	//回调接口
	
	public LoadListView(Context context) {
		super(context);
		initView(context);
	}
	public LoadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}
	public LoadListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}
	/**
	 * 初始化
	 * @param context
	 */
	private void initView(Context context){
		LayoutInflater inflater = LayoutInflater.from(context);
		//实例化底部布局
		footer = inflater.inflate(R.layout.footer_layout, null);
		//隐藏底部布局【加载更多】
		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
		//添加底部加载提示布局到listView
		this.addFooterView(footer);
		//设置滚动监听
		this.setOnScrollListener(this);
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		//如果滚动到最底端 并且停止滚动
		if(totalItemCount == lastVisibleItem && scrollState == SCROLL_STATE_IDLE){
			if(!isLoading){
				isLoading = true;
				//显示底部加载提示布局【加载更多】
				footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
				//加载更多
				iLoadListener.onLoad();
			}
		}
	}
	/**
	 * firstVisibleItem:第一个可见的item位置
	 * visibleItemCount：可见的item的数量
	 * totalItemCount：item总数量
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		//获取最后一个可见的item
		this.lastVisibleItem = firstVisibleItem + visibleItemCount;
		this.totalItemCount = totalItemCount;
	}
	/**
	 * 加载完成
	 */
	public void loadComplete(){
		isLoading = false;
		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
	}
	public void setInterface(ILoadListener loadListener){
		this.iLoadListener = loadListener;
	}
	//加载更多数据的回调接口
	public interface ILoadListener{
		public void onLoad();
	}
}
