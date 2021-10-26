package com.example.factoryapplication;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.example.factoryapplication.adapter.DdxzListAdapter;
import com.example.factoryapplication.common.utils.DateUtil;
import com.example.factoryapplication.common.utils.SPUtils;
import com.example.factoryapplication.common.utils.ScreenUtil;
import com.example.factoryapplication.common.utils.TimeUtil;
import com.example.factoryapplication.entity.CustomerEntity;
import com.example.factoryapplication.entity.CustomerEntityItem;
import com.example.factoryapplication.entity.DdxzEntity;
import com.example.factoryapplication.entity.DdxzListItemEntity;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.ultrapower.tomas.ui.wheelview.adapters.ArrayWheelAdapter;
import com.ultrapower.tomas.ui.wheelview.views.WheelView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;


public class WeiBoActivityF extends BaseFragment implements  AdapterView.OnItemClickListener ,OnClickListener {

    private ViewPager viewPager;//页卡内容
    private ImageView imageView;// 动画图片
    private TextView textView1,textView2,textView3,textView4,textView5;
    private List<View> views;// Tab页面列表
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private View view1,view2,view3,view4,view5;//各个页卡

    private Activity activity ;
    private View view ;

    private ListView ddxz_listview ,ddxz_listviewLq,ddxz_listviewYq,ddxz_listviewWc;
    String status = "1" ;

    int clickCur = 0 ;
    EditText etKh ,etHt ,etCpmc;
    private TextView etBegin ;
    private TextView etEnd ;
    private Button btnQuery ;
    ListView ddsx_listview ;

    Spinner spinnerKh;

    int selSx = 0 ;

    List<CustomerEntityItem> dataCust ;
    String khbh ;

    String  khh ,hth ,cpmc,begin,end;

    String cmdStr = "";

//    ddsx_khh


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_weibo, container, false);
        InitImageView();
        InitTextView();
        InitViewPager();
        initSx() ;
        initView();
        return  view ;
    }


    private void initView() {
        ddxz_listview = (ListView) view1.findViewById(R.id.ddxz_listview);
        ddxz_listview.setOnItemClickListener(this) ;

        ddxz_listviewLq = (ListView) view2.findViewById(R.id.ddxz_listview);
        ddxz_listviewLq.setOnItemClickListener(this) ;
        ddxz_listviewYq = (ListView) view3.findViewById(R.id.ddxz_listview);
        ddxz_listviewYq.setOnItemClickListener(this) ;
        ddxz_listviewWc = (ListView) view4.findViewById(R.id.ddxz_listview);
        ddxz_listviewWc.setOnItemClickListener(this) ;

        getData();
    }

    private void initSx() {
        etBegin = (TextView) view5.findViewById(R.id.ddsx_khh_begion);
        etEnd =(TextView) view5.findViewById(R.id.ddsx_khh_end);

        String lastWeekTime = TimeUtil.getLastWeekByStrToYMD1();
        String curTime = TimeUtil.getDateToString(new Date(),TimeUtil.FORMART1) ;
        etBegin.setText(lastWeekTime);
        etEnd.setText(curTime);

        spinnerKh =  view5.findViewById(R.id.spinnerKh);
        spinnerKh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                khbh = dataCust.get(position).getKhbh() ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etKh = (EditText) view5.findViewById(R.id.ddsx_khh);
        etHt = (EditText) view5.findViewById(R.id.ddsx_hth);
        etCpmc = (EditText) view5.findViewById(R.id.ddsx_cpmc);
        btnQuery = (Button) view5.findViewById(R.id.ddsx_cx);
        ddsx_listview = (ListView) view5.findViewById(R.id.ddsx_listview);
        ddsx_listview.setOnItemClickListener(this);

        etBegin.setOnClickListener(this);
        etEnd.setOnClickListener(this);

        btnQuery.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                khh = etKh.getText().toString() ;
                hth = etHt.getText().toString() ;
                cpmc = etCpmc.getText().toString() ;

                begin = etBegin.getText().toString() ;
                end = etEnd.getText().toString() ;

                String url = Constant.BUSINESS_DDXZ + "?status="+status+"&rybh=" + SPUtils.get(activity,"yhbh","") ;
                if(!isEmpty(khh)) url = url + "&khbh=" + (khbh ==null?"":khbh);
                if(!isEmpty(hth)) url = url + "&htbh=" + hth ;
                if(!isEmpty(cpmc)) url = url + "&cpmc=" + cpmc ;
                if(!isEmpty(begin)) url = url + "&startDate=" + begin ;
                if(!isEmpty(end)) url = url + "&endDate=" + end ;

                viewPager.setCurrentItem(0);
//                selSx = 1 ;
                sendByGet(url);
            }
        });

//        sendByGet(Constant.BUSINESS_KHLB + "?rybh=" + SPUtils.get(getActivity(),"yhbh",""));
        httpUtils.send(HttpMethod.GET, Constant.BUSINESS_KHLB + "?rybh=" + SPUtils.get(getActivity(),"yhbh",""), new RequestCallBack<String>(){
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
                String result = arg0.result ;
                String jsonArray = String.valueOf(JSON.parse(result));
                CustomerEntity entity = JSON.parseObject(jsonArray, CustomerEntity.class);

                dataCust = entity.getData() ;
                List<String> strList = new ArrayList<>() ;
//                int i =0;
                for (CustomerEntityItem item: dataCust) {
//                    if(i<5)
                        strList.add(item.getKhmc()) ;
//                    i++ ;
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item, strList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerKh.setAdapter(adapter);
            }}) ;
        getData();
    }

    boolean isEmpty(String str){
        if(str == null || str == "" ) return true ;
        return false ;
    }

    private void getData() {
//        sendByGet(Constant.BUSINESS_DDXZ   );
        String url = Constant.BUSINESS_DDXZ + "?status="+status+"&rybh=" + SPUtils.get(activity,"yhbh","");
        if(!isEmpty(khh)) url = url + "&khbh=" + (khbh ==null?"":khbh);
        if(!isEmpty(hth)) url = url + "&ddbh=" + hth ;
        if(!isEmpty(cpmc)) url = url + "&cpmc=" + cpmc ;
        if(!isEmpty(begin)) url = url + "&startDate=" + begin ;
        if(!isEmpty(end)) url = url + "&endDate=" + end ;
        sendByGet(url );

        cmdStr = "list" ;
    }

    @Override
    public void onRetSuccess(String result) {
//        if(result.contains("htbh")) {
                if(cmdStr.equals("list")) {
                    if(result.contains("htbh")) {
                        String jsonArray = String.valueOf(JSON.parse(result));
                        DdxzEntity entity = JSON.parseObject(jsonArray, DdxzEntity.class);
//        dismissProcess();
                        if (entity != null) {

                            if (selSx != 1) {
                                DdxzListAdapter adapter = new DdxzListAdapter(activity,
                                        entity.getData().getOrderList(), R.layout.item_ddxz);
                                switch (selPage) {
                                    case 0:
//                                        ddxz_listview.setAdapter(null);
                                        ddxz_listview.setAdapter(adapter);
                                        break;
                                    case 1:
//                                        ddxz_listview.setAdapter(null);
                                        ddxz_listviewLq.setAdapter(adapter);
                                        break;
                                    case 2:
//                                        ddxz_listview.setAdapter(null);
                                        ddxz_listviewYq.setAdapter(adapter);
                                        break;
                                    case 3:
//                                        ddxz_listview.setAdapter(null);
                                        ddxz_listviewWc.setAdapter(adapter);
                                        break;
                                }

                            } else {
                                DdxzListAdapter adapter = new DdxzListAdapter(activity,
                                        entity.getData().getOrderList(), R.layout.item_ddxz);
                                ddsx_listview.setAdapter(adapter);
                            }
                        }
                    }else{
                        switch (selPage) {
                            case 0:
                                ddxz_listview.setAdapter(null);

                                break;
                            case 1:
                                ddxz_listview.setAdapter(null);

                                break;
                            case 2:
                                ddxz_listview.setAdapter(null);

                                break;
                            case 3:
                                ddxz_listview.setAdapter(null);

                                break;
                        }
                    }
        }else if(result.contains("khbh") &&result.contains("khmc")){
//            String jsonArray = String.valueOf(JSON.parse(result));
//            CustomerEntity entity = JSON.parseObject(jsonArray, CustomerEntity.class);
//
//            dataCust = entity.getData() ;
//            List<String> strList = new ArrayList<>() ;
//            int i =0;
//            for (CustomerEntityItem item: dataCust) {
//                if(i<5)
//                    strList.add(item.getKhmc()) ;
//                i++ ;
//            }
//            ArrayAdapter<String> adapter=new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item, strList);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinnerKh.setAdapter(adapter);
        }
    }

    @Override
    public void onRetFailure(String msg) {

    }

    private void InitViewPager() {
        viewPager=(ViewPager) view.findViewById(R.id.vPager);
        views=new ArrayList<View>();
        LayoutInflater inflater=getLayoutInflater();
        view1=inflater.inflate(R.layout.fragment_custom_query, null);
        view2=inflater.inflate(R.layout.fragment_custom_query, null);
        view3=inflater.inflate(R.layout.fragment_custom_query, null);
        view4=inflater.inflate(R.layout.fragment_custom_query, null);
        view5=inflater.inflate(R.layout.layout3, null);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);
        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }
    /**
     *  初始化头标
     */

    private void InitTextView() {
        textView1 = (TextView)  view.findViewById(R.id.text1);
        textView2 = (TextView)  view.findViewById(R.id.text2);
        textView3 = (TextView)  view.findViewById(R.id.text3);
        textView4 = (TextView)  view.findViewById(R.id.text4);
        textView5 = (TextView)  view.findViewById(R.id.text5);


        textView1.setOnClickListener(new MyOnClickListener(0));
        textView2.setOnClickListener(new MyOnClickListener(1));
        textView3.setOnClickListener(new MyOnClickListener(2));
        textView4.setOnClickListener(new MyOnClickListener(3));
        textView5.setOnClickListener(new MyOnClickListener(4));
    }

    /**
     2      * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
     3 */

    private void InitImageView() {
        imageView= (ImageView)  view.findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.aa).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 5 - bmpW) / 4;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);// 设置动画初始位置
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DdxzListItemEntity ddxzListItemEntity = (DdxzListItemEntity) parent.getAdapter().getItem(position);
        Intent intent = new Intent(activity,DdjdActivity.class) ;
        intent.putExtra("ddbh" ,ddxzListItemEntity.getDdbh()) ;
        startActivity(intent);

//        JlcxActivityF
    }

    public String startTime ,endTime ;

    @Override
    public void onClick(View v) {

        int id = v.getId() ;
        switch (id) {
            case R.id.ddsx_khh_begion:
                startTime = etBegin.getText().toString() ;
                Date dateS = null;
                try {
                    dateS = TimeUtil.stringToDate(TimeUtil.FORMART1, startTime);
                    clickCur = 0;
                    showTimeDialog(dateS);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ddsx_khh_end:
                clickCur = 1 ;
                endTime = etEnd.getText().toString() ;
                Date dateE = null;
                try {
                    dateE = TimeUtil.stringToDate(TimeUtil.FORMART1, endTime);
                    clickCur = 1 ;
                    showTimeDialog(dateE);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private class MyOnClickListener implements OnClickListener{
        private int index=0;
        public MyOnClickListener(int i){
            index=i;
        }
        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }

    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) 	{
            container.removeView(mListViews.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return  mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;
        }
    }
int selPage = 0 ;
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 4 + bmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量
        public void onPageScrollStateChanged(int arg0) {


        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {


        }

        public void onPageSelected(int arg0) {
            selPage = arg0 ;
            Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);//显然这个比较简洁，只有一行代码。
            currIndex = arg0;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            imageView.startAnimation(animation);
//            Toast.makeText(activity, "您选择了"+ viewPager.getCurrentItem()+"页卡", Toast.LENGTH_SHORT).show();

            switch (arg0){
                case 0:
                    status = "1" ;
                    getData();
                    break;
                case 1:
                    status = "2" ;
                    getData();
                    break;
                case 2:
                    status = "3" ;
                    getData();
                    break;
                case 3:
                    status = "4" ;
                    getData();
                    break;
//                case 4:
//                    status = "" ;
//                    break;
            }

        }

    }

    public Dialog dialog ;

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

//        final String []strArrHour = new String[24];
//        for (int i = 0; i < strArrHour.length; i++) {
//
////            Calendar cal1 = Calendar.getInstance() ;
//
//            cal.setTime(new Date());
//            int week=cal.get(Calendar.HOUR_OF_DAY);
//
//            strArrHour[i] = 1 + i + "时" ;
//            if (week == (1+ i)) {
//                index = i ;
//            }
//        }
//        ArrayWheelAdapter<String> strArrHourAdapt = new ArrayWheelAdapter<String>(activity, strArrHour) ;
//        wheelHour.setViewAdapter(strArrHourAdapt) ;
//        wheelHour.setCurrentItem(index);

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
            etBegin.setText(dateSel);
        }else if(clickCur ==1){
            etEnd.setText(dateSel);
        }
        onCancelSel();
    }

    /**
     * 取消选择
     */
    public void onCancelSel() {
        if (dialog != null){
            dialog.dismiss();
        }
    }
}