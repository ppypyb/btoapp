package com.example.factoryapplication.common.base.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.example.factoryapplication.R;
import com.example.factoryapplication.common.widget.CustomProgressDialog;
import com.lidroid.xutils.ViewUtils;

/**
 * 封装APP 标题栏
 * @author dyc
 *
 */
public abstract class BaseActivity extends FragmentActivity implements OnClickListener{
	
	protected ImageView ibLeft ;
	
	protected ImageView ibRight ;
	
	public TextView tvTitle ;

	public CustomProgressDialog progress ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE) ;
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
	}

	public void setContentView(int layoutId) {
		super.setContentView(layoutId);
		
		ibLeft = (ImageView) findViewById(R.id.title_left_ib) ;
		ibRight = (ImageView) findViewById(R.id.title_right_ib) ;
		tvTitle =  (TextView) findViewById(R.id.title_tv) ;
		
		if (ibLeft != null) {
			ibLeft.setOnClickListener(this);
		}
		if (ibLeft != null) {
			ibRight.setOnClickListener(this);
		}
		
		ViewUtils.inject(this);
	}
	
	/**
	 * 设置标题
	 * @param 
	 */
	public void setTitle(String msg){
		if (tvTitle != null) {
			tvTitle.setText(msg);
		}
	}
	
	/**
	 * 设置标题
	 * @param 
	 */
	public void setTitle(int resId){
		if (tvTitle != null) {
			tvTitle.setText(resId);
		}
	}
	
	/**
	 * 设置左边图片
	 * @param resId 0则隐藏
	 */
	public void setLeftImage(int resId){
		if (ibLeft != null) {
			if (resId !=0) {
				ibLeft.setVisibility(View.VISIBLE);
				ibLeft.setImageResource(resId);
			}else{
				ibLeft.setVisibility(View.INVISIBLE);
			}
		}
	}

	/**
	 * 设置右边图片
	 * @param resId 0则隐藏
	 */
	public void setRightImage(int resId){
		if (ibRight != null) {
			if (resId !=0) {
				ibRight.setVisibility(View.VISIBLE);
//				ibRight.setImageResource(resId);
			}else{
				ibRight.setVisibility(View.INVISIBLE);
			}
		}
	}


	public void showProcess(){
		progress = new CustomProgressDialog(this) ;
		progress.show();
	}

	public void dismissProcess(){
		if (progress != null){
			progress.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId() ;
		switch (id) {
		case R.id.title_left_ib:
			onLeftClick();
			break;
		case R.id.title_right_ib:
			onRightClick();
			break;
		}
	}
	
	/**
	 * 响应左按键
	 */
	public void onLeftClick(){
		finish();
	}
	
	/**
	 * 响应右按键
	 */
	public void onRightClick(){
	}
	
}
