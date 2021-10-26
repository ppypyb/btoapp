package com.example.factoryapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.fragment.app.FragmentTabHost;

import com.example.factoryapplication.common.base.activity.HttpActivity;
import com.example.factoryapplication.ui.me.MeFragment;

public class MainActivityG extends HttpActivity implements TabHost.OnTabChangeListener {

    private static final String TAG = MainActivityG.class.getName();

    //定义FragmentTabHost对象
    private FragmentTabHost mTabHost;

    private LayoutInflater layoutInflater;

    private Class<?> fragmentArray[] = {DdjdActivityF_old.class, JlcxActivityF.class, MeFragment.class};

    //定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.tab_home_btn,R.drawable.tab_message_btn,R.drawable.tab_more_btn};

    //Tab选项卡的文字
    private String mTextviewArray[] = {"订单进度", "记录查询", "我的"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        initView();
    }

    /**
     * 初始化组件
     */
    private void initView(){
        setLeftImage(0);
//        setRightImage(R.drawable.ic_launcher);

        layoutInflater = LayoutInflater.from(this);

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.setOnTabChangedListener(this);

        int count = fragmentArray.length;

        for(int i = 0; i < count; i++){
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            //设置Tab按钮的背景
//            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }

    }

    /**
     * 给Tab按钮设置图标和文字
     */
    @SuppressLint("InflateParams")
    private View getTabItemView(int index){
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);

        return view;
    }

    public void onRightClick(){
        throw new NullPointerException() ;

    }

    @Override
    public void onRetSuccess(String msg) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRetFailure(String msg) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabChanged(String tabId) {

        setTitle(tabId);
        if (tabId.equals(mTextviewArray[0])) {
            setRightImage(R.drawable.image_progress);
        }else{
            setRightImage(0);
        }
    }

}
