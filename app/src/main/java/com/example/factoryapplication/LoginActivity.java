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


import java.util.HashMap;
import java.util.Map;

/**登录
 * Created by dyc on 2016/10/21.
 */
public class LoginActivity extends HttpActivity implements View.OnClickListener{

    @ViewInject(R.id.login_name)
    private EditText labelName ;
    @ViewInject(R.id.login_pwd)
    private EditText labelPwd ;

    @ViewInject(R.id.login_btn)
    private Button btnLogin ;

    public static String USER_NAME = "USER_NAME" ;
    public static String USER_PWD = "USER_PWD" ;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView() ;

        String name = (String) SPUtils.get(this,USER_NAME,"");
        if(!TextUtils.isEmpty(name)) labelName.setText(name) ;
        String pwd = (String) SPUtils.get(this,USER_PWD,"");
        if ((!TextUtils.isEmpty(name))&&(!TextUtils.isEmpty(pwd))){
            doLogin(name ,pwd);
        }
    }

    @Override
    public void onRetSuccess(String result) {
        dismissProcess();
        LogUtils.e(result);
        if(result!=null&&!result.equals("")) {

            if(!result.contains("ryxx")){
                ToastUtil.showLong(this,"用户名或密码错误.");
                return ;
            }

            String jsonArray = String.valueOf(JSON.parse(result));
            LoginRetAllEntity entity = JSON.parseObject(jsonArray, LoginRetAllEntity.class);

            SPUtils.put(LoginActivity.this,"yhbh" , entity.getData().getRyxx().getRy_bh());
            SPUtils.put(LoginActivity.this,"yhxm" , entity.getData().getRyxx().getRy_xm());
            SPUtils.put(LoginActivity.this,"bmbh" , entity.getData().getRyxx().getBm_bh());
            SPUtils.put(LoginActivity.this,"bmzw" , entity.getData().getRyxx().getRy_zw());

            SPUtils.put(LoginActivity.this,"ry_scgx" , entity.getData().getRyxx().getRy_scgx()==null?"1":entity.getData().getRyxx().getRy_scgx());
//            SPUtils.put(LoginActivity.this,"bmmc" , entity.getData().getRyxx().getBmmc());
//            SPUtils.put(LoginActivity.this,"token" , entity.getData().getRyxx().getToken());

//            User entity = JSON.parseObject(jsonArray, User.class);
                if(entity != null){
                Intent intent = new Intent(this, MainActivity.class) ;
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onRetFailure(String result) {
        dismissProcess();
        ToastUtil.show(this,"与服务器连接异常",Toast.LENGTH_SHORT);
        LogUtils.e(result);
        Intent intent = new Intent(this, RegActivity.class) ;
        intent.putExtra("RESET_SERVE" ,"1") ;
        startActivity(intent);
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
            case R.id.login_btn:
                String name = labelName.getText().toString() ;
                String pwd = labelPwd.getText().toString() ;
//                name = "";
//                pwd = "1" ;
//                String pwdM5 = MD5Utils.MD5(pwd) ;

                doLogin(name, pwd);

                break ;
        }
    }

    private void doLogin(String name, String pwdM5) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pwdM5)){
            ToastUtil.showLong(this, "请输入完整的用户名和密码.");
            return ;
        }
        String date = DateUtil.getDateString(DateUtil.STR_DATE) ;
        Integer dateI = Integer.parseInt(date) ;
//        if(dateI > 20210905) {
//            ToastUtil.show(this,"还没付费?" , Toast.LENGTH_LONG);
//            System.exit(1);
//            return ;
//        }

        showProcess();
        Map<String,String> map = new HashMap();
        map.put("userAccount",name);
        map.put("userPwd", pwdM5);
//        sendByPost(Constant.BUSINESS_LOGIN, map);
        sendByGet(Constant.BUSINESS_LOGIN + "?yhmc="+ name + "&yhmm=" + pwdM5);

        SPUtils.put(this, USER_NAME, name);
        SPUtils.put(this,USER_PWD , pwdM5);
    }
}
