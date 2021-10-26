package com.example.factoryapplication;

import android.app.Activity;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import com.example.factoryapplication.common.widget.CustomProgressDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;

import java.util.Map;

/**
 * http功能Fragment
 * @author wlx
 *
 */
public abstract class BaseFragment extends Fragment {
	/**
	 * 连接超时时间
	 */
	private static final int CONNTIMEOUT = 100000;
	
	HttpUtils httpUtils ;
	
	//等待对话框
	CustomProgressDialog progressDia ;
	private Activity mActivity;
	public void init(){
		httpUtils = new HttpUtils(CONNTIMEOUT) ;
		httpUtils.configSoTimeout(CONNTIMEOUT) ;
		httpUtils.configRequestRetryCount(0) ;
		
		progressDia = new CustomProgressDialog(mActivity) ;
	}
	 @Override  
	    public void onAttach(Activity activity){  
	        super.onAttach(activity);  
	        mActivity = activity;
	    }  
	      
	    @Override  
	    public void onCreate(Bundle savedInstanceState){  
	        super.onCreate(savedInstanceState); 
	        init() ; 
	    } 

	public void onHttpStart(){
		progressDia.show();
	}
	
	/**
	 * @param msg
	 * 正常Http返回处理
	 */
	public abstract void onRetSuccess(String result) ;
	/**
	 *失败Http 处理 
	 * @param msg
	 */
	public abstract void onRetFailure(String msg) ;
	
	public void sendByGet(String url){
		
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>(){
			@Override
			public void onStart() {
				super.onStart();
				onHttpStart();
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				progressDia.dismiss();
				LogUtils.w("接口返回失败："+arg1);
				onRetFailure(arg1);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				progressDia.dismiss();
				onRetSuccess(arg0.result);
			}}) ;
	}
	public void sendByGet(String url, Map<String, String> map) {
		RequestParams params = new RequestParams();
		for (String key : map.keySet()) {
			params.addBodyParameter(key, map.get(key));
		}

		httpUtils.send(HttpMethod.GET, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
						super.onStart();
						onHttpStart();
					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						onRetFailure(arg1);
						progressDia.dismiss();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						onRetSuccess(arg0.result);
						progressDia.dismiss();
					}
				});
	}

	public void sendByPost(String url, Map<String, String> map) {
		RequestParams params = new RequestParams();
		for (String key : map.keySet()) {
			params.addBodyParameter(key, map.get(key));
		}

		if (httpUtils == null) init();
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
						super.onStart();
						onHttpStart();
					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						onRetFailure(arg1);
						if(progressDia.isShowing())
							progressDia.dismiss();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						LogUtils.i("fragment recv:" + arg0.result);
						onRetSuccess(arg0.result);
						progressDia.dismiss();
					}
				});
	}
}
