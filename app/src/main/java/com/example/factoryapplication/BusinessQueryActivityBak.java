package com.example.factoryapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.fragment.app.FragmentTabHost;

import com.example.factoryapplication.common.base.activity.BaseActivity;
import com.example.factoryapplication.fragment.CustomQueryFragment;


/**订单选择
 * Created by dyc on 2016/10/9.
 */
public class BusinessQueryActivityBak extends BaseActivity implements TabHost.OnTabChangeListener {

    private static final String TAG = BusinessQueryActivityBak.class.getName();

    private FragmentTabHost mTabHost;

    private LayoutInflater layoutInflater;

    private Class<?> fragmentArray[] = { CustomQueryFragment.class,
            CustomQueryFragment.class,CustomQueryFragment.class,
            CustomQueryFragment.class,CustomQueryFragment.class};

    private String mTextviewArray[];

    private TextView tview[];

    public static final int TAB_CUSTOM_QUERY = 1 ;
    public static final int TAB_GCLINE_QUERY = 2 ;
    public static int curSelTab = TAB_CUSTOM_QUERY;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_query);
        initView();
    }


    private void initView() {
        mTextviewArray = getResources().getStringArray(R.array.business_arr_str) ;
        setTitle(mTextviewArray[0]);
        layoutInflater = LayoutInflater.from(this);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.flid_tab_content);
        mTabHost.setOnTabChangedListener(this);

        int count = fragmentArray.length;
        tview = new TextView[5];

        for (int i = 0; i < count; i++) {

            View view = layoutInflater.inflate(
                    R.layout.layout_tab_top, null);

            tview[i] = (TextView) view.findViewById(R.id.tvid_tab_item);
            tview[i].setText(mTextviewArray[i]);

            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
                    .setIndicator(view);
            mTabHost.addTab(tabSpec, fragmentArray[i], null);

//            mTabHost.getTabWidget().getChildAt(i)
//                    .setBackgroundResource(R.drawable.selector_tab_background);
        }

        tview[0].setTextColor(getResources().getColor(R.color.blue_font));
//        if(tview[1] != null)
        tview[1].setTextColor(getResources().getColor(R.color.gray_font));

    }

    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(
                R.layout.layout_tab_top, null);

        TextView textView = (TextView) view.findViewById(R.id.tvid_tab_item);
        textView.setText(mTextviewArray[index]);

        return view;
    }

    @Override
    public void onTabChanged(String tabId) {

        if (tabId.equals(mTextviewArray[0])) {
            curSelTab = TAB_CUSTOM_QUERY;
            setTitle(mTextviewArray[0]);
            tview[0].setTextColor(getResources().getColor(R.color.blue_font));
            if(tview[1] != null)
            tview[1].setTextColor(getResources().getColor(R.color.gray_font));
        } else {
            curSelTab = TAB_GCLINE_QUERY;
            setTitle(mTextviewArray[1]);
            tview[0].setTextColor(getResources().getColor(R.color.gray_font));
            tview[1].setTextColor(getResources().getColor(R.color.blue_font));
        }

        final int size = mTabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);
            } else {
                v.setSelected(false);
            }
        }
        supportInvalidateOptionsMenu();
    }
}
