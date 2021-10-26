package com.example.factoryapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.factoryapplication.common.base.activity.HttpActivity;
import com.example.factoryapplication.common.utils.SPUtils;
import com.example.factoryapplication.common.utils.ToastUtil;
import com.example.factoryapplication.ui.me.MeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = MainActivity.class.getName();
//
//    //定义FragmentTabHost对象
//    private FragmentTabHost mTabHost;
//
//    private LayoutInflater layoutInflater;
//
//    private Class<?> fragmentArray[] = {DdjdActivityF_old.class, JlcxActivityF.class, MeFragment.class};
//
//    //定义数组来存放按钮图片
//    private int mImageViewArray[] = {R.drawable.tab_home_btn,R.drawable.tab_message_btn,R.drawable.tab_more_btn};
//
//    //Tab选项卡的文字
//    private String mTextviewArray[] = {"订单进度", "记录查询", "我的"};
//
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.main);
//
//        initView();
//    }
//
//    /**
//     * 初始化组件
//     */
//    private void initView(){
//        setLeftImage(0);
////        setRightImage(R.drawable.ic_launcher);
//
//        layoutInflater = LayoutInflater.from(this);
//
//        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
//        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
//
//        mTabHost.setOnTabChangedListener(this);
//
//        int count = fragmentArray.length;
//
//        for(int i = 0; i < count; i++){
//            //为每一个Tab按钮设置图标、文字和内容
//            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
//            mTabHost.addTab(tabSpec, fragmentArray[i], null);
//            //设置Tab按钮的背景
////            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
//        }
//
//    }
//
//    /**
//     * 给Tab按钮设置图标和文字
//     */
//    @SuppressLint("InflateParams")
//    private View getTabItemView(int index){
//        View view = layoutInflater.inflate(R.layout.tab_item_view, null);
//
//        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
//        imageView.setImageResource(mImageViewArray[index]);
//
//        TextView textView = (TextView) view.findViewById(R.id.textview);
//        textView.setText(mTextviewArray[index]);
//
//        return view;
//    }
//
//    public void onRightClick(){
//        throw new NullPointerException() ;
//
//    }
//
//    @Override
//    public void onRetSuccess(String msg) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void onRetFailure(String msg) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void onTabChanged(String tabId) {
//
//        setTitle(tabId);
//        if (tabId.equals(mTextviewArray[0])) {
//            setRightImage(R.drawable.image_progress);
//        }else{
//            setRightImage(0);
//        }
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

//        ToastUtil.show(this,"211", Toast.LENGTH_SHORT);

        navView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                ToastUtil.show(MainActivity.this,"111", Toast.LENGTH_SHORT);

                SPUtils.put(MainActivity.this,"CUR_SEL" ,menuItem.getTitle());

            }
        });

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                ToastUtil.show(MainActivity.this,"222", Toast.LENGTH_SHORT);
                SPUtils.put(MainActivity.this,"CUR_SEL" ,menuItem.getTitle());
                return false;
            }
        });

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_me)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(navView, navController);
    }

}
