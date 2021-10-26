package com.example.factoryapplication.common.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.factoryapplication.Constant;
import com.lidroid.xutils.BitmapUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ViewHolder {

	private SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	private Context context ;

	public ViewHolder(Context context,ViewGroup parent,int layoutId,int position) {
		this.mPosition = position;
		this.context = context ;
		this.mViews = new SparseArray<View>();
		
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,false);
		mConvertView.setTag(this);
	}
	
	public int getmPosition() {
		return mPosition;
	}

	public static ViewHolder get(Context context,View convertView,ViewGroup parent,int layoutId,int position){
		if(convertView == null){
			return new ViewHolder(context, parent, layoutId, position);
		}else{
			ViewHolder vh = (ViewHolder)convertView.getTag();
			vh.mPosition = position;
			return vh;
		}
	}
	/**
	 * 通过viewId获取控件
	 * @param viewId
	 * @return
	 */
	public <T extends View>T getView(int viewId){
		View view = mViews.get(viewId);
		if(view == null){
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T)view;
	}
	
	public View getConvertView(){
		return mConvertView;
	}
	/**
	 * 设置TextView的值
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setText(int viewId,String text){
		TextView tv = getView(viewId);
		tv.setText(text);
		return this;
	}
	/**
	 * 设置ImageView的值
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setImgResource(int viewId,int resId){
		ImageView imgView = getView(viewId);
		imgView.setImageResource(resId);
		return this;
	}


	/**
	 * 从服务器取图片
	 *http://bbs.3gstdy.com
	 * @param url
	 * @return
	 */
	public static Bitmap getHttpBitmap(String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
//			Log.d(TAG, url);
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setConnectTimeout(0);
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

//	public ViewHolder setImgResource(int viewId,String resId){
//		ImageView imgView = getView(viewId);
//		Bitmap bitmap =
//				getHttpBitmap(Constant.BUSINESS_IMG + resId);
//		imgView.setImageBitmap(bitmap);
//		return this;
//	}

	public ViewHolder setImgResource(int viewId,String url){
		ImageView imgView = getView(viewId);
//		ImageRequest imageRequest
		BitmapUtils bitmapUtils = new BitmapUtils(context) ;
		bitmapUtils.display(imgView,url);
//		Bitmap bitmap =
//				getHttpBitmap(url);
//		imgView.setImageBitmap(bitmap);
		return this;
	}
}