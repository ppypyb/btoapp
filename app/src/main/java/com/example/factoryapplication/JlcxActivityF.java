package com.example.factoryapplication;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.alibaba.fastjson.JSON;
import com.example.factoryapplication.adapter.JlcxGxListAdapter;
import com.example.factoryapplication.common.base.activity.HttpActivity;
import com.example.factoryapplication.common.base.activity.HttpFileActivity;
import com.example.factoryapplication.common.utils.FileUtil;
import com.example.factoryapplication.common.utils.SPUtils;
import com.example.factoryapplication.common.utils.ToastUtil;
import com.example.factoryapplication.entity.JlcxGxEntity;
import com.example.factoryapplication.entity.JlcxGxListEntity;
import com.example.factoryapplication.entity.JlcxRetEntity;
import com.example.factoryapplication.entity.departmentProgressList;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**记录查询
 * Created by dyc on 2016/10/12.
 */

//public class JlcxActivityF extends BaseFragment implements AdapterView.OnItemClickListener , AdapterView.OnItemSelectedListener {
public class JlcxActivityF extends HttpFileActivity implements AdapterView.OnItemClickListener , AdapterView.OnItemSelectedListener {

    private static final String TAG = JlcxActivityF.class.getName();

    public Dialog dialog ;
    private LayoutInflater layoutInflater;

    private String mTextviewArray[];

    public static final int TAB_HANDING = 1 ;
    public static final int TAB_HAND_OVER = 2 ;
    public static int curSelTab = TAB_HANDING;

    int clickCur = 0 ;
    public String startTime ,endTime ;


    boolean queryFalgNRead = true ;

    @ViewInject(R.id.jl_ddbh)
    private TextView ddbh ;
    @ViewInject(R.id.jl_cpmc)
    private TextView cpmc ;
    @ViewInject(R.id.jl_bbth)
    private TextView bbth ;
    @ViewInject(R.id.jl_cz)
    private TextView cz ;
    @ViewInject(R.id.jl_sc)
    private TextView scrq ;

    @ViewInject(R.id.jl_scbm)
    private TextView scbm ;
    @ViewInject(R.id.jl_scgr)
    private TextView scgr ;

    @ViewInject(R.id.jl_listview)
    private ListView ddjd_listview;

    @ViewInject(R.id.jlcx_ll_add)
    private LinearLayout jlcx_ll_add ;

    @ViewInject(R.id.btn_camera)
    private Button btn_camera ;

    @ViewInject(R.id.btn_eoms_query)
    private Button btnQuery ;

    @ViewInject(R.id.jlcx_img)
    private ImageView jlcx_img ;

    List<JlcxGxEntity> list ;
    Map<String ,JlcxGxEntity> map = new HashMap<String ,JlcxGxEntity>() ;

    private Activity activity ;
    private View view ;

    String ddbhStr = null,cptu = null;

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        this.activity = activity ;
//    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent() ;
        ddbhStr = intent.getStringExtra("ddbh") ;
        cptu= intent.getStringExtra("cptp") ;
        setContentView(R.layout.activity_jlcx);

        activity = this;

        setTitle("记录查询");

        initView() ;
    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.activity_jlcx, container, false);
//        ddjd_listview = (ListView) view.findViewById(R.id.jl_listview);
//
//       ddbh = view.findViewById(R.id.jl_ddbh);;
//       cpmc  = view.findViewById(R.id.jl_cpmc);
//       bbth  = view.findViewById(R.id.jl_bbth);
//       cz  = view.findViewById(R.id.jl_cz);
//       scrq  = view.findViewById(R.id.jl_sc);
//
//       scbm  = view.findViewById(R.id.jl_scbm);
//
//       scgr  = view.findViewById(R.id.jl_scgr);
//       jlcx_ll_add = view.findViewById(R.id.jlcx_ll_add);;
//        initView();
//        return view ;
//    }



    /**
     * 取消选择
     */
    public void onCancelSel() {
        if (dialog != null){
            dialog.dismiss();
        }
    }

    private void initView() {

        BitmapUtils bitmapUtils = new BitmapUtils(this) ;
        bitmapUtils.display(jlcx_img,cptu);

//        ddjd_listview.setOnItemClickListener(this);
//        ddjd_listview.setOnItemSelectedListener(this);

//        layoutInflater = LayoutInflater.from(this);
        btnQuery.setOnClickListener(this);
        btn_camera.setOnClickListener(this);

//        showProcess();
        Map<String,String> map = new HashMap();
        String rybh = SPUtils.get(activity,"yhbh","").toString() ;

        sendByGet(Constant.BUSINESS_JLCX +"?ddbh="+ ddbhStr+"&rybh=" + rybh);
    }

    @ViewInject(R.id.etScan)
    EditText etScan ;

    public static final int TAKE_PHOTO = 1;//声明一个请求码，用于识别返回的结果
//    private Uri imageUri;
//    private final String filePath = Environment.getExternalStorageDirectory() + File.separator + "output_image.jpg";

    private Uri imageUri ,imageUri1;
    private final String filePath = Environment.getExternalStorageDirectory() + File.separator + "output_image.jpg";
    private final String filePath1 = Environment.getExternalStorageDirectory() + File.separator + "output_image1.jpg";

    private static final int REQUEST_EXTERNAL_STORAGE = 11;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    //处理返回结果的函数，下面是隐示Intent的返回结果的处理方式，具体见以前我所发的intent讲解
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {

                case TAKE_PHOTO:
                    if (resultCode == RESULT_OK) {
                        try {
                            RequestParams params = new RequestParams();
//                            File file = FileUtil.from(this,imageUri);
//                            params.addBodyParameter("file",file , "image/jpg");

                            File file = FileUtil.from(this,imageUri);

                            saveBitmapToFile(file,filePath1);
                            File outputImage1 = new File(filePath1);

                            if (Build.VERSION.SDK_INT >= 24) {
                                imageUri1 = FileProvider.getUriForFile(this,
                                        "com.example.factoryapplication", outputImage1);
                            } else {
                                imageUri1 = Uri.fromFile(outputImage1);
                            }
                            File file1 = FileUtil.from(this,imageUri1);

                            params.addBodyParameter("file",file1 , "image/jpg");
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


    private void requestCamera() {
        File outputImage = new File(filePath);
                /*
                创建一个File文件对象，用于存放摄像头拍下的图片，我们把这个图片命名为output_image.jpg
                并把它存放在应用关联缓存目录下，调用getExternalCacheDir()可以得到这个目录，为什么要
                用关联缓存目录呢？由于android6.0开始，读写sd卡列为了危险权限，使用的时候必须要有权限，
                应用关联目录则可以跳过这一步
                 */
        try//判断图片是否存在，存在则删除在创建，不存在则直接创建
        {
            if (!outputImage.getParentFile().exists()) {
                outputImage.getParentFile().mkdirs();
            }
            if (outputImage.exists()) {
                outputImage.delete();
            }

            outputImage.createNewFile();

            if (Build.VERSION.SDK_INT >= 24) {
                imageUri = FileProvider.getUriForFile(this,
                        "com.example.factoryapplication", outputImage);
            } else {
                imageUri = Uri.fromFile(outputImage);
            }
            //使用隐示的Intent，系统会找到与它对应的活动，即调用摄像头，并把它存储
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, TAKE_PHOTO);
            //调用会返回结果的开启方式，返回成功的话，则把它显示出来
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //动态请求权限
    private void requestPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
        } else {
            //调用
            requestCamera();
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId() ;
        switch (id){
            case R.id.btn_eoms_query:
                showProcess();
                String ddbhStrT = etScan.getText().toString() ;
                if(TextUtils.isEmpty(ddbhStrT)){
                    ToastUtil.show(this,"请输入订单号", Toast.LENGTH_SHORT);
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

    @Override
    public void onRetSuccess(String result) {
        if(result.contains("orderProcessList")){
        String jsonArray = String.valueOf(JSON.parse(result));
        JlcxRetEntity entity = JSON.parseObject(jsonArray, JlcxRetEntity.class);
//        dismissProcess();
        if(entity != null) {
            ddbh.setText("订单编号:" + entity.getData().getDdbh());
            cpmc.setText("产品名称:" + entity.getData().getCpmc());
            bbth.setText("版本图号:" + entity.getData().getCpth());
            cz.setText("材质:" + entity.getData().getCpcz());
            scrq.setText("生产日期:" + entity.getData().getJhrq() + " 排产数量:" + entity.getData().getPcsl());

            scbm.setText("生产部门:" + entity.getData().getBmmc());
            scgr.setText("生产工人:" + entity.getData().getRyxm());
            LogUtils.d("---" + entity.getData().getCpcz());

            list = entity.getData().getOrderProcessList();

            JlcxGxListAdapter jlcxGxListAdapter = new JlcxGxListAdapter(activity,
                    entity.getData().getJobProcessList(), R.layout.item_jlcx);
            ddjd_listview.setAdapter(jlcxGxListAdapter);

            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    map.put(list.get(i).getGxmc(), list.get(i));
                    Button btn = new Button(activity);
                    btn.setText(list.get(i).getGxmc());
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView tv = (TextView) v;
                            String gxbh = map.get(tv.getText()).getGxbh();

                            sendByGet(Constant.BUSINESS_GWGX +"?ddbh="+ddbhStr+"&rybh="
                                    +SPUtils.get(JlcxActivityF.this,"yhbh","")+ "&gxbh=" + gxbh);
                        }
                    });
                    jlcx_ll_add.addView(btn);
                }
            }
        }


//            DdjdListAdapter adapter = new DdjdListAdapter(this,
//                    entity.getData().getDepartmentProgressList(), R.layout.item_ddjd);
//            ddjd_listview.setAdapter(adapter);
        }else if(result.contains("djrq")){
            String jsonArray = String.valueOf(JSON.parse(result));
            JlcxGxListEntity entity = JSON.parseObject(jsonArray, JlcxGxListEntity.class);

            JlcxGxListAdapter jlcxGxListAdapter = new JlcxGxListAdapter(activity,
                    entity.getData(), R.layout.item_jlcx);
            ddjd_listview.setAdapter(jlcxGxListAdapter);
        }
    }

    @Override
    public void onRetFailure(String msg) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(activity,GxmxActivity.class) ;
        departmentProgressList depart = (departmentProgressList) parent.getAdapter().getItem(position);
        intent.putExtra("ddbh" ,depart.getDdbh()) ;
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(activity,GxmxActivity.class) ;
        departmentProgressList depart = (departmentProgressList) parent.getAdapter().getItem(position);
        intent.putExtra("ddbh" ,depart.getDdbh()) ;
        startActivity(intent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static String saveBitmapToFile(File file, String newpath) {
        try {
            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image
            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();
            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;
            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);
            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();
            // here i override the original image file
            File aa = new File(newpath);
            FileOutputStream outputStream = new FileOutputStream(aa);
            //choose another format if PNG doesn't suit you
            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            String filepath = aa.getAbsolutePath();
            Log.e("getAbsolutePath", aa.getAbsolutePath());
            return filepath;
        } catch (Exception e) {
            return null;
        }
    }
}
