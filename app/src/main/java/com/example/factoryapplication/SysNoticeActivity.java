//package com.example.factoryapplication;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTabHost;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TabHost;
//import android.widget.TextView;
//
//import com.lidroid.xutils.util.LogUtils;
//import com.lidroid.xutils.view.annotation.ViewInject;
//import com.ultrapower.tomas.gsinfowinene.base.AbstractSelTimeActivity;
//import com.ultrapower.tomas.gsinfowinene.common.utils.TimeUtil;
//import com.ultrapower.tomas.gsinfowinene.common.utils.ToastUtil;
//import com.ultrapower.tomas.gsinfowinene.common.widget.TabBottomView;
//
//import java.text.ParseException;
//import java.util.Date;
//
///**系统通知
// * Created by dyc on 2016/10/12.
// */
//public class SysNoticeActivity extends AbstractSelTimeActivity implements TabHost.OnTabChangeListener{
//
//    private static final String TAG = SysNoticeActivity.class.getName();
//
//    private FragmentTabHost mTabHost;
//
//    private LayoutInflater layoutInflater;
//
//    @ViewInject(R.id.ll_eoms_query_top)
//    LinearLayout llTop ;
//
//    private Class<?> fragmentArray[] = { NoticeQueryFragment.class,
//            NoticeQueryFragment.class };
//
//    private String mTextviewArray[];
//
//    public static final int TAB_HANDING = 1 ;
//    public static final int TAB_HAND_OVER = 2 ;
//    public static int curSelTab = TAB_HANDING;
//
//    int clickCur = 0 ;
//    public String startTime ,endTime ;
//
//    @ViewInject(R.id.et_eoms_begion)
//    private TextView etBegin ;
//    @ViewInject(R.id.et_eoms_end)
//    private TextView etEnd ;
//
//    @ViewInject(R.id.btn_eoms_query)
//    private Button btnQuery ;
//
//    @ViewInject(R.id.eoms_bottom)
//    private TabBottomView bottomView ;
//
//    private TextView tview[];
//
//    NoticeQueryFragment noRead ,haveRead ;
//
//    boolean queryFalgNRead = true ;
//
//
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_notice_query);
//
//        setTitle("通知信息列表");
//
//        initView() ;
//        bottomView.setSelIndex(4);
//    }
//
//    @Override
//    public void onClick(View v) {
//        super.onClick(v);
//        int id = v.getId() ;
//        switch (id){
//            case R.id.btn_eoms_query:
//                startTime = etBegin.getText().toString()  + "00分00" ;
//                endTime = etEnd.getText().toString()  + "00分00";
//                try {
//                    Date dateS = TimeUtil.stringToDate(TimeUtil.FORMART51,startTime) ;
//                    startTime = TimeUtil.dateToString(TimeUtil.FORMART6,dateS) ;
//                    Date dateE = TimeUtil.stringToDate(TimeUtil.FORMART51,endTime) ;
//                    endTime = TimeUtil.dateToString(TimeUtil.FORMART6,dateE) ;
//                    long diff = TimeUtil.dateDiff(dateS ,dateE) ;
//                    if(diff >7){
//                        ToastUtil.showLong(this,"请选择一周以内的时间查询.");
//                        return ;
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                if (queryFalgNRead){
//                    noRead.searchData(startTime,endTime,false);
//                }else{
//                    haveRead.searchData(startTime,endTime,true);
//                }
//                if(noRead != null)
//                    noRead.setTime(startTime,endTime) ;
//                if(haveRead != null)
//                    haveRead.setTime(startTime,endTime) ;
//                break;
//            case R.id.et_eoms_begion:
//                startTime = etBegin.getText().toString()  + "00分00" ;
//                Date dateS = null;
//                try {
//                    dateS = TimeUtil.stringToDate(TimeUtil.FORMART51, startTime);
//                    clickCur = 0 ;
//                    showTimeDialog(dateS);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case R.id.et_eoms_end:
//                clickCur = 1 ;
//                endTime = etEnd.getText().toString()  + "00分00";
//                Date dateE = null;
//                try {
//                    dateE = TimeUtil.stringToDate(TimeUtil.FORMART51, endTime);
//                    showTimeDialog(dateE);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                break;
//        }
//    }
//
//    private void initQueryTime() {
//        startTime = etBegin.getText().toString() ;
//        endTime = etEnd.getText().toString() ;
//
//        try {
//            Date dateStart = TimeUtil.stringToDate(TimeUtil.FORMART41, startTime) ;
//            Date dateEnd = TimeUtil.stringToDate(TimeUtil.FORMART41,endTime) ;
//
//            startTime = TimeUtil.dateToString(TimeUtil.FORMART6,dateStart) ;
//            endTime = TimeUtil.dateToString(TimeUtil.FORMART6,dateEnd) ;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 选择日期后
//     * @param dateSel
//     */
//    public void onSelDate(String dateSel) {
//        if (clickCur == 0 ){
//            etBegin.setText(dateSel);
//        }else if(clickCur ==1){
//            etEnd.setText(dateSel);
//        }
//        onCancelSel();
//    }
//
//    /**
//     * 取消选择
//     */
//    public void onCancelSel() {
//        if (dialog != null){
//            dialog.dismiss();
//        }
//    }
//
//    private void initView() {
//        etBegin.setOnClickListener(this);
//        etEnd.setOnClickListener(this);
//        btnQuery.setOnClickListener(this);
//
//        llTop.setVisibility(View.GONE);
//
//        mTextviewArray = getResources().getStringArray(R.array.notice_status_arr) ;
//
//        layoutInflater = LayoutInflater.from(this);
//
//        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
//
//        mTabHost.setup(this, getSupportFragmentManager(), R.id.flid_tab_eoms);
//        mTabHost.setOnTabChangedListener(this);
//        int count = fragmentArray.length;
//        tview = new TextView[2];
//
//        for (int i = 0; i < count; i++) {
//
//            View view = layoutInflater.inflate(
//                    R.layout.layout_tab_top, null);
//
//            tview[i] = (TextView) view.findViewById(R.id.tvid_tab_item);
//            tview[i].setText(mTextviewArray[i]);
//
//            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
//                    .setIndicator(view);
//            mTabHost.addTab(tabSpec, fragmentArray[i], null);
//
//            mTabHost.getTabWidget().getChildAt(i)
//                    .setBackgroundResource(R.drawable.selector_tab_background);
//        }
//        tview[0].setTextColor(getResources().getColor(R.color.blue_font));
//        tview[1].setTextColor(getResources().getColor(R.color.gray_font));
//
//        String lastWeekTime = TimeUtil.getLastWeekByStrToYMD1();
//        String curTime = TimeUtil.getDateToString(new Date(),TimeUtil.FORMART41) ;
//        etBegin.setText(lastWeekTime);
//        etEnd.setText(curTime);
//
//        initQueryTime();
//    }
//
//    @Override
//    public void onAttachFragment(Fragment fragment) {
//        super.onAttachFragment(fragment);
//        String curTag = mTabHost.getCurrentTabTag() ;
//        LogUtils.w("curTag" + curTag);
//
//        if (curTag.equals("未读")){
//            noRead = (NoticeQueryFragment) getSupportFragmentManager().findFragmentByTag(mTabHost.getCurrentTabTag()) ;
//            noRead.setIsRead(false);
//            noRead.searchData(startTime, endTime,false);
//        } else {
//            haveRead = (NoticeQueryFragment) getSupportFragmentManager().findFragmentByTag(mTabHost.getCurrentTabTag());
//            haveRead.setIsRead(true);
//            haveRead.searchData(startTime, endTime,true);
//        }
//    }
//
//    @Override
//    public void onTabChanged(String tabId) {
//        if (tabId.equals(mTextviewArray[0])) {
//            curSelTab = TAB_HANDING;
//            queryFalgNRead = true ;
//            tview[0].setTextColor(getResources().getColor(R.color.blue_font));
//            if(tview[1] != null)
//                tview[1].setTextColor(getResources().getColor(R.color.gray_font));
//            if(noRead != null)
//                 noRead.searchData(startTime,endTime,false);
//        } else {
//            curSelTab = TAB_HAND_OVER;
//            queryFalgNRead = false ;
//            tview[0].setTextColor(getResources().getColor(R.color.gray_font));
//            tview[1].setTextColor(getResources().getColor(R.color.blue_font));
//            if(haveRead != null)
//            haveRead.searchData(startTime,endTime,true);
//        }
//    }
//
//    private View getTabItemView(int index) {
//        View view = layoutInflater.inflate(
//                R.layout.layout_tab_top, null);
//
//        TextView textView = (TextView) view.findViewById(R.id.tvid_tab_item);
//        textView.setText(mTextviewArray[index]);
//
//        return view;
//    }
//
//    @Override
//    public void onRetSuccess(String result) {
//
//    }
//
//    @Override
//    public void onRetFailure(String msg) {
//
//    }
//}
