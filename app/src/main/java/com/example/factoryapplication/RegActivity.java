package com.example.factoryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.factoryapplication.common.base.activity.HttpActivity;
import com.example.factoryapplication.common.utils.DateUtil;
import com.example.factoryapplication.common.utils.SPUtils;
import com.example.factoryapplication.common.utils.ToastUtil;
import com.example.factoryapplication.entity.LoginRetAllEntity;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**登录
 * Created by dyc on 2016/10/21.
 */
public class RegActivity extends HttpActivity implements View.OnClickListener{

    @ViewInject(R.id.reg_name)
    private EditText labelName ;
    @ViewInject(R.id.reg_pwd)
    private EditText labelPwd ;

    @ViewInject(R.id.reg_btn)
    private Button btnLogin ;

    public static String USER_NAME = "USER_NAME" ;
    public static String USER_PWD = "USER_PWD" ;

    String RESET_SERVE = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reg);
        String url = SPUtils.get(this,"url" ,"").toString();
        if (TextUtils.isEmpty(url)) url = Constant.IP ;
        labelName.setText(url);

        RESET_SERVE = getIntent().getStringExtra("RESET_SERVE") ;
        if("1".equals(RESET_SERVE)){
//            labelPwd.setVisibility(View.INVISIBLE);
        }else{

            if(!TextUtils.isEmpty(url)) {
                Constant.IP = url;
                Constant.WEB_APP = "http://"+Constant.IP+"/btoweb/btoweb" ;
                Constant.resetUrl();
                Intent intent = new Intent(this, LoginActivity.class) ;
                startActivity(intent);
//                finish();
            }
        }

        initView() ;

        String name = labelName.getText().toString() ;
        String pwd = labelPwd.getText().toString() ;
//        labelName.setText(name) ;
//        String pwd = (String) SPUtils.get(this,USER_PWD,"");
//        if ((!TextUtils.isEmpty(name))&&(!TextUtils.isEmpty(pwd))){
//            doLogin(name ,pwd);
//        }
    }

    @Override
    public void onRetSuccess(String result) {
        dismissProcess();
        LogUtils.e(result);
        if(result!=null&&!result.equals("")) {

            if(!result.contains("10000")){
                ToastUtil.showLong(this,"注册失败.");
                return ;
            }else {
                String name = labelName.getText().toString() ;

            }

//            SPUtils.put(LoginActivity.this,"bmmc" , entity.getData().getRyxx().getBmmc());
//            SPUtils.put(LoginActivity.this,"token" , entity.getData().getRyxx().getToken());

//            User entity = JSON.parseObject(jsonArray, User.class);
//                if(entity != null){
                Intent intent = new Intent(this, LoginActivity.class) ;
                startActivity(intent);
                finish();
//            }
        }
    }

    @Override
    public void onRetFailure(String result) {
        SPUtils.remove(this,"url" );
        dismissProcess();
        LogUtils.e(result);
    }

    private void initView() {
        btnLogin.setOnClickListener(this);
        setTitle("生产销售管理系统");
        setLeftImage(R.mipmap.logo);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId() ;
        switch (id){
            case R.id.reg_btn:
                String name = labelName.getText().toString() ;
                String pwd = labelPwd.getText().toString() ;
                SPUtils.put(this,"url" ,name);

                Constant.IP =  name ;
                Constant.WEB_APP = "http://"+Constant.IP+"/btoweb/btoweb" ;
                Constant.resetUrl();



//                String pwdM5 = MD5Utils.MD5(pwd) ;
                doLogin(name, pwd);
                break ;
        }
    }

    private void doLogin(String name, String pwdM5) {
        if("1".equals(RESET_SERVE)){
            Intent intent = new Intent(this, LoginActivity.class) ;
            startActivity(intent);
            finish();
        }else {
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwdM5)) {
                ToastUtil.showLong(this, "请输入服务器地址和注册码.");
//                System.exit(1);
                return;
            }
            String date = DateUtil.getDateString(DateUtil.STR_DATE);
            Integer dateI = Integer.parseInt(date);
//            if (dateI > 20210820) {
//                ToastUtil.show(this, "还没付费?", Toast.LENGTH_LONG);
//                return;
//            }

            showProcess();

            sendByGet(Constant.BUSINESS_REG + "?registrationCode=" + pwdM5);
        }

    }
}
