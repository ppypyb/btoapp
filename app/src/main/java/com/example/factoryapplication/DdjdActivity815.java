package com.example.factoryapplication;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.example.factoryapplication.adapter.DdjdListAdapter;
import com.example.factoryapplication.common.base.activity.HttpFileActivity;
import com.example.factoryapplication.common.utils.ToastUtil;
import com.example.factoryapplication.entity.DdjdRetEntity;
import com.example.factoryapplication.entity.departmentProgressList;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**进度查询
 * Created by dyc on 2016/10/12.
 */
public class DdjdActivity815 extends HttpFileActivity implements AdapterView.OnItemClickListener , AdapterView.OnItemSelectedListener {

    private static final String TAG = DdjdActivity815.class.getName();

    public Dialog dialog ;
    private LayoutInflater layoutInflater;

    private String mTextviewArray[];

    public static final int TAB_HANDING = 1 ;
    public static final int TAB_HAND_OVER = 2 ;
    public static int curSelTab = TAB_HANDING;

    int clickCur = 0 ;
    public String startTime ,endTime ;

    @ViewInject(R.id.et_eoms_begion)
    private TextView etBegin ;

    @ViewInject(R.id.btn_camera)
    private Button btn_camera ;

    @ViewInject(R.id.btn_eoms_query)
    private Button btnQuery ;

    private TextView tview[];

    boolean queryFalgNRead = true ;

    @ViewInject(R.id.ddbh)
    private TextView ddbh ;
    @ViewInject(R.id.cpbh)
    private TextView cpbh ;
    @ViewInject(R.id.bbth)
    private TextView bbth ;
    @ViewInject(R.id.cz)
    private TextView cz ;

    @ViewInject(R.id.ddjd_listview)
    private ListView ddjd_listview;

    @ViewInject(R.id.etScan)
    EditText etScan ;

    String ddbhStr = null;

    public static final int TAKE_PHOTO = 1;//声明一个请求码，用于识别返回的结果
    private Uri imageUri;
    private final String filePath = Environment.getDownloadCacheDirectory() + File.separator + "output_image.jpg";

    List<departmentProgressList> listBm ;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddcx);

        Intent intent = getIntent() ;
        ddbhStr = intent.getStringExtra("ddbh") ;
        LogUtils.e("ddbh" + ddbhStr);

        setTitle("订单进度");

        initView() ;
//        bottomView.setSelIndex(4);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId() ;
        switch (id){
            case R.id.btn_eoms_query:
                showProcess();
                String ddbhStrT = etScan.getText().toString() ;
                if(TextUtils.isEmpty(ddbhStrT)){
                    ToastUtil.show(this,"请输入订单号",Toast.LENGTH_SHORT);
                    return ;
                }
                ddbhStr = ddbhStrT ;
                sendByGet(Constant.BUSINESS_DDJDCX + "?ddbh="+ddbhStr);
                break;
            case R.id.btn_camera:
                requestCamera() ;
                showProcess();
                sendByGet(Constant.BUSINESS_DDJDCX + "?ddbh="+ddbhStr);
                break;

        }
    }
    private static final int REQUEST_EXTERNAL_STORAGE = 11;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private void requestCamera() {
        File outputImage = new File(filePath);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)  != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this,     new String[]{Manifest.permission.CAMERA},   TAKE_PHOTO);
        }

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(this,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
        }

        try//判断图片是否存在，存在则删除在创建，不存在则直接创建
        {
            if (!outputImage.getParentFile().exists()) {
                outputImage.getParentFile().mkdirs();
            }
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
//            if (Build.VERSION.SDK_INT >= 24) {
//                imageUri = FileProvider.getUriForFile(this,
//                        "com.example.factoryapplication", outputImage);
//            } else {
                imageUri = Uri.fromFile(outputImage);
//            }

            Intent intent=new Intent();              // 指定开启系统相机的Action
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, TAKE_PHOTO);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    requestCamera();
                } else {
                    Toast.makeText(this, "You denied the peimission",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    /**
     * 取消选择
     */
    public void onCancelSel() {
        if (dialog != null){
            dialog.dismiss();
        }
    }

    private void initView() {
        etBegin.setOnClickListener(this);

        btnQuery.setOnClickListener(this);
        btn_camera.setOnClickListener(this);
        ddjd_listview.setOnItemClickListener(this);
        ddjd_listview.setOnItemSelectedListener(this);

        layoutInflater = LayoutInflater.from(this);


        showProcess();
        Map<String,String> map = new HashMap();

        sendByGet(Constant.BUSINESS_DDJDCX + "?ddbh="+ddbhStr);
    }

    @Override
    public void onRetSuccess(String result) {
        String jsonArray = String.valueOf(JSON.parse(result));
        DdjdRetEntity entity = JSON.parseObject(jsonArray, DdjdRetEntity.class);
        dismissProcess();
        if(entity != null){
            ddbh.setText("订单编号:" + entity.getData().getDdbh());
            cpbh.setText("产品编号:" + entity.getData().getCpbh());
            bbth.setText("版本图号:" + entity.getData().getCpth());
            cz.setText("材质:" + entity.getData().getCpcz());
            LogUtils.d("---" + entity.getData().getCpcz());


            listBm = entity.getData().getDepartmentProgressList() ;
            DdjdListAdapter adapter = new DdjdListAdapter(this,
                    entity.getData().getDepartmentProgressList(), R.layout.item_ddjd);
            ddjd_listview.setAdapter(adapter);
        }
    }

    @Override
    public void onRetFailure(String msg) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,GxmxActivity.class) ;
        departmentProgressList depart = (departmentProgressList) parent.getAdapter().getItem(position);

        intent.putExtra("ddbh" ,depart.getDdbh()) ;
        intent.putExtra("bmbh" ,depart.getBmbh()) ;
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(this,GxmxActivity.class) ;
//        departmentProgressList depart = (departmentProgressList) parent.getAdapter().getItem(position);
//
//        intent.putExtra("ddbh" ,depart.getBmbh()) ;
//        startActivity(intent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onRetSuccessFile(String msg) {
        dismissProcess();
        etScan.setText(msg);
//        ToastUtil.show(this,msg,Toast.LENGTH_LONG);
    }

    @Override
    public void onRetFailureFile(String msg) {
        dismissProcess();
    }


    //处理返回结果的函数，下面是隐示Intent的返回结果的处理方式，具体见以前我所发的intent讲解
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {

                case TAKE_PHOTO:
                    if (resultCode == RESULT_OK) {
                        try {
                            RequestParams params = new RequestParams();
                            params.addBodyParameter("file", new File(filePath), "image/jpg");
                            showProcess();
                            sendFilePost(Constant.BUSINESS_OCR, params);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
