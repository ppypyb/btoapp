package com.example.factoryapplication;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.alibaba.fastjson.JSON;
import com.example.factoryapplication.common.base.activity.HttpFileActivity;
import com.example.factoryapplication.common.utils.DateUtil;
import com.example.factoryapplication.common.utils.FileUtil;
import com.example.factoryapplication.common.utils.SPUtils;
import com.example.factoryapplication.common.utils.ScreenUtil;
import com.example.factoryapplication.common.utils.TimeUtil;
import com.example.factoryapplication.common.utils.ToastUtil;
import com.example.factoryapplication.entity.JlcxGxEntity;
import com.example.factoryapplication.entity.departmentProgressList;
import com.example.factoryapplication.entity.scdj.GwgxItemEntity;
import com.example.factoryapplication.entity.scdj.GwgxRetEneity;
import com.example.factoryapplication.entity.scdj.GzdItemEntity;
import com.example.factoryapplication.entity.scdj.JjdGzdRetEntity;
import com.example.factoryapplication.entity.scdj.JjdItemEntity;
import com.example.factoryapplication.entity.scdj.JobProcList;
import com.example.factoryapplication.entity.scdj.JobProcListItem;
import com.example.factoryapplication.entity.scdj.ScdjMsgEntity;
import com.example.factoryapplication.entity.scdj.VerifyGzdhList;
import com.example.factoryapplication.entity.scdj.VerifyGzdhListItem;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ultrapower.tomas.ui.wheelview.adapters.ArrayWheelAdapter;
import com.ultrapower.tomas.ui.wheelview.views.WheelView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**生产登记
 * Created by dyc on 2016/10/12.
 */


public class ScdjActivityF extends HttpFileActivity implements AdapterView.OnItemClickListener , AdapterView.OnItemSelectedListener {

    private static final String TAG = ScdjActivityF.class.getName();

    public Dialog dialog ;
    private LayoutInflater layoutInflater;

    private String mTextviewArray[];

    public static final int TAB_HANDING = 1 ;
    public static final int TAB_HAND_OVER = 2 ;
    public static int curSelTab = TAB_HANDING;

    public static final int EDIT_OK = 1000;

    int clickCur = 0 ;
    public String startTime ,endTime ;


    boolean queryFalgNRead = true ;

    @ViewInject(R.id.sc_ddbh)
    private TextView ddbh ;
    @ViewInject(R.id.sc_cpmc)
    private TextView cpmc ;
    @ViewInject(R.id.sc_bbth)
    private TextView bbth ;
    @ViewInject(R.id.sc_cz)
    private TextView cz ;
    @ViewInject(R.id.sc_sc)
    private TextView scrq ;

    @ViewInject(R.id.scdj_img)
    private ImageView scdj_img ;

    @ViewInject(R.id.sc_scbm)
    private TextView sc_scbm ;
    @ViewInject(R.id.sc_scgr)
    private TextView sc_scgr ;

    @ViewInject(R.id.fpsl_input)
    private EditText fpsl_input ;
    @ViewInject(R.id.cpsl_input)
    private EditText cpsl_input ;

    @ViewInject(R.id.jlcx_ll_add)
    private LinearLayout jlcx_ll_add ;

//    @ViewInject(R.id.fpsl_input)
//    EditText fpsl_input ;

    @ViewInject(R.id.scdj_fpyy_btn)
    Button scdj_fpyy_btn ;

    @ViewInject(R.id.etGzd)
    EditText etGzd ;

    @ViewInject(R.id.etJjd)
    EditText etJjd ;

    @ViewInject(R.id.scdj_wcsl)
    EditText scdj_wcsl ;
    @ViewInject(R.id.scdj_cpyy_btn)
    Button scdj_cpyy_btn ;

    @ViewInject(R.id.scdj_sub_btn)
    Button scdj_sub_btn ;
    @ViewInject(R.id.scdj_can_btn)
    Button scdj_can_btn ;

//    @ViewInject(R.id.spin_gzd)
//    Spinner spin_gzd ;
//    @ViewInject(R.id.spin_jjd)
//    Spinner spin_jjd ;

//    @ViewInject(R.id.spin_gzd)
//    EditText spin_gzd ;
//    @ViewInject(R.id.spin_jjd)
//    Spinner spin_jjd ;

    @ViewInject(R.id.spin_gx)
    Spinner spin_gx ;

    @ViewInject(R.id.etScan)
    EditText etScan ;

    @ViewInject(R.id.btn_camera)
    private Button btn_camera ;

    @ViewInject(R.id.btn_eoms_query)
    private Button btn_eoms_query ;

    @ViewInject(R.id.btn_camera_jjd)
    private Button btn_camera_jjd ;

    @ViewInject(R.id.btn_camera_gzd)
    private Button btn_camera_gzd ;

    List<GwgxItemEntity> list ;
    Map<String ,JlcxGxEntity> map = new HashMap<String ,JlcxGxEntity>() ;

    private Activity activity ;
    private View view ;

    String ddbhStr = null;
    String cpbhStr = null ,cptu;
    String rybhStr = null ,jjdh = null,gzdh= null;

    String djbhStr = null;

    @ViewInject(R.id.sc_scrq)
    private TextView sc_scrq ;

    @ViewInject(R.id.sc_wcs)
    private TextView sc_wcs ;
    @ViewInject(R.id.sc_jjs)
    private TextView sc_jjs ;

    String gxbh = "" ;
    String gxlb = "" ;

    List<GzdItemEntity> gzdList ;
    List<JjdItemEntity> jjdList ;

    public static final int TAKE_PHOTO = 1;//声明一个请求码，用于识别返回的结果
    public static final int TAKE_PHOTO_JJD = 2;//声明一个请求码，用于识别返回的结果
    public static final int TAKE_PHOTO_GZD = 3;//声明一个请求码，用于识别返回的结果
//    private Uri imageUri;
//    private final String filePath = Environment.getExternalStorageDirectory() + File.separator + "output_image.jpg";

    private Uri imageUri ,imageUri1;
    private final String filePath = Environment.getExternalStorageDirectory() + File.separator + "output_image.jpg";
    private final String filePath1 = Environment.getExternalStorageDirectory() + File.separator + "output_image1.jpg";



    //    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        this.activity = activity ;
//    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent() ;
        ddbhStr = intent.getStringExtra("ddbh") ;
        rybhStr = SPUtils.get(this,"yhbh","").toString() ;
        cpbhStr = intent.getStringExtra("cpbh") ;

        cptu= intent.getStringExtra("cptp") ;
//        ddbhStr = "DD21022004";
//        cpbhStr = "100600710021";
//        rybhStr = "0003" ;

        setContentView(R.layout.activity_scdj);

        activity = this;

        setTitle("生产登记");

        initView() ;
    }


    public View generateDatePick3(String strSelDate) {
        // 加载卡类型PopupWindow
        LayoutInflater inflater = LayoutInflater.from(activity
                .getApplicationContext());
        // 引入窗口
        View cardTypePopupView = inflater.inflate(
                R.layout.y_m_d, null);

        final WheelView wheelYear = (com.ultrapower.tomas.ui.wheelview.views.WheelView) cardTypePopupView.findViewById(R.id.sel_year) ;
        final WheelView wheelMonth = (WheelView) cardTypePopupView.findViewById(R.id.sel_month) ;
        final WheelView wheelDay = (WheelView) cardTypePopupView.findViewById(R.id.sel_day) ;
//        final WheelView wheelHour = (WheelView) cardTypePopupView.findViewById(R.id.sel_hour) ;

        Calendar cal = Calendar.getInstance() ;
        Date date = DateUtil.stringToDate(strSelDate, DateUtil.NOW_DATE);
        cal.setTime(date );

        int arr = 2 ;
        int yearCur = cal.get(Calendar.YEAR) +1;

        final String []strArrYear = new String[arr +1];
        for (int i = 2; i>=0; i--) {
            strArrYear[arr - i] = (yearCur - i ) + "年";
//            strArrYear[arr - i] = (yearCur - i )%1000 + "年";
        }
        ArrayWheelAdapter<String> strArrYearAdapt = new ArrayWheelAdapter<String>(activity, strArrYear) ;
        wheelYear.setViewAdapter(strArrYearAdapt) ;
        //TODO 1ge bug
        wheelYear.setCurrentItem(1);

        int index = 0 ;
        final String []strArrMonth = new String[12];
        for (int i = 0; i < strArrMonth.length; i++) {
            strArrMonth[i] = 1 + i + "月" ;
            if ((cal.get(Calendar.MONTH)+1) == (1+ i)) {
                index = i ;
            }
        }
        ArrayWheelAdapter<String> strArrMonthAdapt = new ArrayWheelAdapter<String>(activity, strArrMonth) ;
        wheelMonth.setViewAdapter(strArrMonthAdapt) ;
        wheelMonth.setCurrentItem(index);

        final String []strArrDay = new String[31];
        for (int i = 0; i < strArrDay.length; i++) {

            Calendar cal1 = Calendar.getInstance() ;

            cal1.setTime(new Date());
            int week=cal1.get(Calendar.DAY_OF_WEEK)-1;

            strArrDay[i] = 1 + i + "日" ;
            if ((cal.get(Calendar.DAY_OF_MONTH)) == (1+ i)) {
                index = i ;
            }
        }
        ArrayWheelAdapter<String> strArrDayAdapt = new ArrayWheelAdapter<String>(activity, strArrDay) ;
        wheelDay.setViewAdapter(strArrDayAdapt) ;
        wheelDay.setCurrentItem(index);

        View.OnClickListener onClickDatePick = new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int id = arg0.getId() ;
                switch (id) {
                    case R.id.datePickCancel:
                        onCancelSel();
                        break;
                    case R.id.datePickOk:
                        String strYear = strArrYear[wheelYear.getCurrentItem()].substring(0, strArrYear[wheelYear.getCurrentItem()].length()-1);
                        String strMonth = strArrMonth[wheelMonth.getCurrentItem()].substring(0, strArrMonth[wheelMonth.getCurrentItem()].length()-1);
                        String strDay = strArrDay[wheelDay.getCurrentItem()].substring(0, strArrDay[wheelDay.getCurrentItem()].length()-1);
//                        String strHour = strArrHour[wheelHour.getCurrentItem()].substring(0, strArrHour[wheelHour.getCurrentItem()].length()-1);

                        if (strMonth != null && strMonth.length() < 2) {
                            strMonth = "0" + strMonth ;
                        }
                        if (strDay != null && strDay.length() < 2) {
                            strDay = "0" + strDay ;
                        }
//                        if (strHour != null && strHour.length() < 2) {
//                            strHour = "0" + strHour ;
//                        }
//                        String strSelDate = strYear +  strMonth + strDay + strHour ;
                        String strSelDate = strYear + "-" + strMonth + "-" + strDay   ;

                        onSelDate(strSelDate);
                        break;
                }
            }
        };

        Button btnCancel = (Button) cardTypePopupView.findViewById(R.id.datePickCancel) ;
        Button btnOk = (Button) cardTypePopupView.findViewById(R.id.datePickOk) ;
        btnCancel.setOnClickListener(onClickDatePick) ;
        btnOk.setOnClickListener(onClickDatePick) ;

        return cardTypePopupView;
    }


    /**
     * 选择日期后
     * @param dateSel
     */
    public void onSelDate(String dateSel) {
        if (clickCur == 0 ){
            sc_scrq.setText(dateSel);
        }
        onCancelSel();
    }



    public void showTimeDialog(Date date) {
        String curDate = DateUtil.getDateString(date, DateUtil.NOW_DATE) ;
        View view = generateDatePick3(curDate) ;
        dialog = new Dialog(activity, R.style.Dialog);
        dialog.setContentView(view);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);

        lp.y = 300; // 新位置Y坐标
        lp.width = ScreenUtil.dip2px(activity,350); // 宽度
        lp.height = ScreenUtil.dip2px(activity,300); // 高度

        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
        // dialog.onWindowAttributesChanged(lp);
        dialogWindow.setAttributes(lp);
        dialog.show();
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

        BitmapUtils bitmapUtils = new BitmapUtils(this) ;
        bitmapUtils.display(scdj_img,cptu);

        String curTime = TimeUtil.getDateToString(new Date(),TimeUtil.FORMART1) ;
        sc_scrq.setText(curTime);
        sc_scrq.setOnClickListener(this);

        btn_eoms_query.setOnClickListener(this);
        btn_camera.setOnClickListener(this);
        btn_camera_gzd.setOnClickListener(this);
        btn_camera_jjd.setOnClickListener(this);
        etGzd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mHandler.removeCallbacks(mRunnable);
                //800毫秒没有输入认为输入完毕
                mHandler.postDelayed(mRunnable, 1500);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.w("afterTextChanged", "-----------------------------------");
                Log.e("afterTextChanged@ ", etGzd.getText().toString());
            }
        });

        scdj_wcsl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    gzdh = etGzd.getText().toString();
                    jjdh = etJjd.getText().toString() ;
                    if(TextUtils.isEmpty(gzdh) ) return ;
                    sendByGet(Constant.BUSINESS_JOB_PROCESS +"?gzdh="+ gzdh+ "&djbh=" + djbhStr+
                            "&gxbh=" + gxbh + "&jjdh=" + jjdh
                            + "&cpbh="+cpbhStr + "&ddbh=" + ddbhStr + "&rybh=" + SPUtils.get(ScdjActivityF.this,"yhbh","")+
                            "&bmbh=" +SPUtils.get(ScdjActivityF.this,"bmbh",""));

                } else {
//                    ToastUtil.show(ScdjActivityF.this,"out 了",Toast.LENGTH_LONG);
//                    Log.i(TAG, "失去焦点了");
                }
            }
        });



//        spin_gzd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int indGz = spin_gzd.getSelectedItemPosition() ;
//                GzdItemEntity gzdItemEntity =  gzdList.get(indGz) ;
//
//                int indJj = spin_gzd.getSelectedItemPosition() ;
//
//                GzdItemEntity jjdItemEntity =  gzdList.get(indJj) ;
//                gzdh = jjdItemEntity.getGzdh() ;
//                sendByGet(Constant.BUSINESS_GZD_SL +"?cpbh="+ cpbhStr+"&ddbh=" + ddbhStr + "&gxbh=" + gxbh + "&gzdh=" + gzdItemEntity.getGzdh() + "&jjdh=" + jjdItemEntity.getGzdh());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        }) ;
//        spin_jjd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int indGz = spin_gzd.getSelectedItemPosition() ;
//                GzdItemEntity gzdItemEntity =  gzdList.get(indGz) ;
//
//                int indJj = spin_gzd.getSelectedItemPosition() ;
//                JjdItemEntity jjdItemEntity =  jjdList.get(indJj) ;
//                jjdh = jjdItemEntity.getJjdh() ;
//                sendByGet(Constant.BUSINESS_GZD_SL +"?cpbh="+ cpbhStr+"&ddbh=" + "&gxbh=" + gxbh + "&gzdh" + gzdItemEntity.getGzdh() + "&jjdh=" + jjdItemEntity.getJjdh());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        }) ;

        spin_gx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gxbh = list.get(position).getGxbh() ;
                gxlb = list.get(position).getGxlb() ;
                etGzd.setText("");
                fpsl_input.setText("");
                cpsl_input.setText("");



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        scdj_cpyy_btn.setOnClickListener(this);
        scdj_fpyy_btn.setOnClickListener(this);
        scdj_sub_btn.setOnClickListener(this);
        scdj_can_btn.setOnClickListener(this);

        showProcess();
        Map<String,String> map = new HashMap();
        String rybh = SPUtils.get(activity,"yhbh","").toString() ;
        String bmbh = SPUtils.get(activity,"bmbh","").toString() ;

        sendByGet(Constant.BUSINESS_SCDJ +"?ddbh="+ ddbhStr+"&bmbh="+bmbh +"&rybh=" + rybhStr);
        sendByGet(Constant.BUSINESS_GZDH +"?cpbh="+ cpbhStr+"&ddbh=" + ddbhStr);
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 11;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

int camerType ;
    private void requestCamera(int arg) {
        camerType = arg ;
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
            intent.putExtra("camer_type" ,arg) ;
            startActivityForResult(intent, TAKE_PHOTO);
            //调用会返回结果的开启方式，返回成功的话，则把它显示出来
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        switch (requestCode) {
            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                {
//                    requestCamera();
//                } else {
//                    Toast.makeText(this, "You denied the peimission",
//                            Toast.LENGTH_SHORT).show();
//                }
//                break;
            default:
        }
    }

    //处理返回结果的函数，下面是隐示Intent的返回结果的处理方式，具体见以前我所发的intent讲解
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        String camer_type = data.getStringExtra("camer_type") ;
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 12:
                    String total = data.getStringExtra("total");
                    fpsl_input.setText(total);
                    break;
                case 11:
                    String total1 = data.getStringExtra("total");
                    cpsl_input.setText(total1);
                    break;
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId() ;
        gzdh =  etGzd.getText().toString();
        jjdh =  etJjd.getText().toString();
        switch (id){
            case R.id.btn_eoms_query:
                  String ddbhStrT = etScan.getText().toString() ;
                if(TextUtils.isEmpty(ddbhStrT)){
                    ToastUtil.show(this,"请输入订单号",Toast.LENGTH_SHORT);
                    return ;
                }
                ddbhStr = ddbhStrT ;
                sendByGet(Constant.BUSINESS_SCDJ +"?ddbh="+ ddbhStr+"&bmbh="+SPUtils.get(this,"bmbh","").toString() +"&rybh=" + rybhStr);
                break;
            case R.id.btn_camera:
                requestCamera(TAKE_PHOTO);
                camerType = TAKE_PHOTO ;
                break;
            case R.id.btn_camera_gzd:
                requestCamera(TAKE_PHOTO_GZD);
                break;
            case R.id.btn_camera_jjd:
                requestCamera(TAKE_PHOTO_JJD);
                break;
            case R.id.sc_scrq:
                startTime = sc_scrq.getText().toString() ;
                Date dateS = null;
                try {
                    dateS = TimeUtil.stringToDate(TimeUtil.FORMART1, startTime);
                    showTimeDialog(dateS);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.scdj_cpyy_btn:
                Intent intent = new Intent(activity,ScdjFcpActivity.class) ;
                intent.putExtra("djbh" ,djbhStr) ;
                intent.putExtra("fclb" ,"1") ;
                intent.putExtra("gxbh" ,gxbh) ;

                intent.putExtra("gzdh" ,gzdh) ;
                intent.putExtra("jjdh" ,jjdh) ;
                intent.putExtra("rybh" ,rybhStr) ;
                intent.putExtra("ddbh" ,ddbhStr) ;

                startActivityForResult(intent,11);
                break;
            case R.id.scdj_fpyy_btn:

                Intent intent1 = new Intent(activity,ScdjFcpActivity.class) ;
                intent1.putExtra("djbh" ,djbhStr) ;
                intent1.putExtra("fclb" ,"2") ;
                intent1.putExtra("gxbh" ,gxbh) ;

                intent1.putExtra("gzdh" ,gzdh) ;
                intent1.putExtra("jjdh" ,jjdh) ;
                intent1.putExtra("rybh" ,rybhStr) ;
                intent1.putExtra("ddbh" ,ddbhStr) ;
                startActivityForResult(intent1,12);
                break;
            case R.id.scdj_sub_btn:
                String fpsl = fpsl_input.getText().toString() ;
                String cpsl = cpsl_input.getText().toString() ;
                String wcsl = scdj_wcsl.getText().toString()  ;
                String zpsl = "";
                if(!TextUtils.isEmpty(wcsl) && !TextUtils.isEmpty(fpsl) && !TextUtils.isEmpty(cpsl)){
                    zpsl = String.valueOf(Integer.parseInt(wcsl) - Integer.parseInt(fpsl) - Integer.parseInt(cpsl));
                    if(!TextUtils.isEmpty(zpsl) && Integer.parseInt(zpsl) > 0){
                        sendByGet(Constant.BUSINESS_UPDATEPRODUCTION +"?gzdh="+ gzdh+ "&djbh=" + djbhStr+ "&fpsl=" + fpsl + "&cpsl=" +cpsl+  "&wcsl=" +wcsl+
                                "&gxbh=" + gxbh + "&jjdh=" + jjdh
                                + "&cpbh="+cpbhStr + "&ddbh=" + ddbhStr + "&rybh=" + SPUtils.get(this,"yhbh","")+ "&bmbh=" +SPUtils.get(this,"bmbh","")

                        );
                    }
                }


//                new HttpUtils().send(HttpRequest.HttpMethod.GET, Constant.BUSINESS_UPDATEPRODUCTION +"?cpbh="+ ddbhStr+"&ddbh=", new RequestCallBack<String>(){
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                        onHttpStart();
//                    }
//                    @Override
//                    public void onFailure(HttpException arg0, String arg1) {
//                        progressDia.dismiss();
//                    }
//
//                    @Override
//                    public void onSuccess(ResponseInfo<String> arg0) {
//                        progressDia.dismiss();
//                        //确定成功
//                        ToastUtil.show(ScdjActivityF.this,"提交成功", Toast.LENGTH_LONG);
//                    }}) ;
                break;
            case R.id.scdj_can_btn:
                sendByGet(Constant.BUSINESS_QXDJ +"?djbh="+ djbhStr+ "&ddbh="+ddbhStr+"&gxbh=" + gxbh );
                break;
        }
    }

    @Override
    public void onRetSuccess(String result) {
        dismissProcess();

        if(result.contains("gwgx")){
            String jsonArray = String.valueOf(JSON.parse(result));
            GwgxRetEneity entity = JSON.parseObject(jsonArray, GwgxRetEneity.class);
    //        dismissProcess();
            if(entity != null) {
                String gzdhFlag = entity.getData().getGzdhflag();
                if(gzdhFlag!=null && gzdhFlag.equals("1")){
                    etGzd.setText(ddbhStr);

                }
                ddbh.setText("订单编号:" + entity.getData().getDdbh());
                cpmc.setText("产品名称:" + entity.getData().getCpmc());
                bbth.setText("版本图号:" + entity.getData().getCpth());
                cz.setText("材质:" + entity.getData().getCpcz());
                scrq.setText("生产日期:" + entity.getData().getJhrq() + " 排产数量:" + entity.getData().getPcsl());

                sc_scbm.setText("生产部门:" + entity.getData().getBmmc());
                sc_scgr.setText("生产工人:" + entity.getData().getRyxm());

                ddbhStr = entity.getData().getDdbh() ;
//                djbhStr = entity.getData().getDjbh() ;
                cpbhStr = entity.getData().getCpbh() ;

                LogUtils.d("---" + entity.getData().getCpcz());

                list = entity.getData().getGwgx();

                if (list != null) {
                    List<String> strList = new ArrayList<>() ;
                    for (GwgxItemEntity item: list) {
                        strList.add(item.getGxmc()) ;
                    }
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item, strList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gx.setAdapter(adapter);
                }
            }

        }else if(result.contains("jjdhList")){
            String jsonArray = String.valueOf(JSON.parse(result));
            JjdGzdRetEntity entity = JSON.parseObject(jsonArray, JjdGzdRetEntity.class);
            if(entity != null) {
                jjdList = entity.getData().getJjdhList() ;
                List<String> strList = new ArrayList<>() ;
                for (JjdItemEntity item: jjdList) {
                        strList.add(item.getJjdh()) ;
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item, strList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spin_jjd.setAdapter(adapter);

               gzdList = entity.getData().getGzdhList() ;
                List<String> strList1 = new ArrayList<>() ;

                for (GzdItemEntity item: gzdList) {
                        strList1.add(item.getGzdh()) ;
                }
                ArrayAdapter<String> adapter1=new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item, strList1);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spin_gzd.setAdapter(adapter1);
            }
        }else if(result.contains("gzdwcsl")){
//            String jsonArray = String.valueOf(JSON.parse(result));
//            JddGzdCountRetEntity entity = JSON.parseObject(jsonArray, JddGzdCountRetEntity.class);
//            if(entity !=null){
////                jjdsl.setText("数量:" + entity.getData().getJjdwcsl());
////                gzdsl.setText("数量:" + entity.getData().getGzdwcsl());
//            }
        }else if(result.contains("取消")){
            ToastUtil.show(this,"取消登记成功",Toast.LENGTH_LONG);
        }else if(result.contains("updateScjldjb") ){
            String jsonEntity = String.valueOf(JSON.parse(result));
            ScdjMsgEntity entity = JSON.parseObject(jsonEntity, ScdjMsgEntity.class);
            String msg = entity.getData().getMsg();
            ToastUtil.show(this,msg,Toast.LENGTH_LONG);
            if(msg.equals("登记成功")){
                finish();
            }

        }else if(result.contains("zpsl") &&result.contains("cpsl")){
            String jsonArray = String.valueOf(JSON.parse(result));
            JobProcList entity = JSON.parseObject(jsonArray, JobProcList.class);
            if(entity!= null && entity.getData() != null){
                JobProcListItem jobProcListItem = entity.getData().get(0) ;
//                scdj_wcsl.setText(Integer.parseInt(jobProcListItem.getZpsl())+Integer.parseInt(jobProcListItem.getFpsl())+Integer.parseInt(jobProcListItem.getCpsl()));
//                fpsl_input.setText(jobProcListItem.getFpsl());
//                cpsl_input.setText(jobProcListItem.getCpsl());
                sc_scrq.setText(jobProcListItem.getDjrq());

                sc_jjs.setText("交接数:" + jobProcListItem.getJjsl());
                sc_wcs.setText("完成数:" + jobProcListItem.getWcsl());

                djbhStr = jobProcListItem.getDjbh() ;
            }
        }else if(result.contains("登记数量") ){
            String jsonArray = String.valueOf(JSON.parse(result));
            ScdjMsgEntity scdjErrorEntity = JSON.parseObject(jsonArray, ScdjMsgEntity.class);
            if (scdjErrorEntity != null){
                ToastUtil.show(this,scdjErrorEntity.getData().getMsg(),Toast.LENGTH_LONG);
            }
        }else if(result.contains("verifyGzdhMsg")){
            String jsonArray = String.valueOf(JSON.parse(result));
            VerifyGzdhList entity = JSON.parseObject(jsonArray, VerifyGzdhList.class);
            if(entity!= null && entity.getData() != null){
                VerifyGzdhListItem verifyGzdhListItem = entity.getData().get(0) ;
                String verifyGzdhMsg = verifyGzdhListItem.getVerifyGzdhMsg();
                if(!verifyGzdhMsg.contains("成功")){
                    scdj_wcsl.setText("0");
                    fpsl_input.setText("0");
                    cpsl_input.setText("0");
                    etGzd.setText("0");
                    ToastUtil.show(this,verifyGzdhMsg,Toast.LENGTH_LONG);
                }else{
                    scdj_wcsl.setText(verifyGzdhListItem.getWcsl());
                    fpsl_input.setText(verifyGzdhListItem.getFpsl());
                    cpsl_input.setText(verifyGzdhListItem.getCpsl());
                }
            }
        }
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (EDIT_OK == msg.what) {
                Log.d(TAG, "handleMessage() returned:输入完成 " );
                String verifyGzdh = etGzd.getText().toString();
                if(!verifyGzdh.isEmpty()){
                    sendByGet(Constant.BUSINESS_VERIFY_GZDH +"?ddbh="+ddbhStr+"&gxbh="+gxbh+"&gzdh="+verifyGzdh);
                }

            }

        }
    };

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            mHandler.sendEmptyMessage(EDIT_OK);
        }
    };

    @Override
    public void onRetFailure(String msg) {

    }

    @Override
    public void onRetSuccessFile(String msg) {
        switch (camerType){
            case TAKE_PHOTO:
                etScan.setText(msg);
                break;
            case TAKE_PHOTO_GZD:
                etGzd.setText(msg);
                if(!msg.isEmpty()){
                    sendByGet(Constant.BUSINESS_VERIFY_GZDH +"?ddbh="+ddbhStr+"&gxbh="+gxbh+"&gzdh="+msg);
                }
                break;
            case TAKE_PHOTO_JJD:
                etJjd.setText(msg);
                break;
        }
//        etScan.setText(msg);
        dismissProcess();
    }

    @Override
    public void onRetFailureFile(String msg) {
        dismissProcess();
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

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
