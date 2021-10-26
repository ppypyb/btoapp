package com.example.factoryapplication;


import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class TestMyActivityGroup extends AbstractMyActivityGroup { //加载的Activity的名字，LocalActivityManager就是通过这些名字来查找对应的Activity的。
    private static final String CONTENT_ACTIVITY_NAME_0 = "contentActivity0";
    private static final String CONTENT_ACTIVITY_NAME_1 = "contentActivity1";
    private static final String CONTENT_ACTIVITY_NAME_2 = "contentActivity2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main1);
        super.onCreate(savedInstanceState);

        ((RadioButton) findViewById(R.id.radio_button0)).setChecked(true);
    }

    /**
     * 找到自定义id的加载Activity的View
     */
    @Override
    protected ViewGroup getContainer() {
        return (ViewGroup) findViewById(R.id.container);
    }

    /**
     * 初始化按钮
     */
    @Override
    protected void initRadioBtns() {
        initRadioBtn(R.id.radio_button0);
        initRadioBtn(R.id.radio_button1);
        initRadioBtn(R.id.radio_button2);

    }

    /**
     * 导航按钮被点击时，具体发生的变化
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.radio_button0:
                    setContainerView(CONTENT_ACTIVITY_NAME_0, DdjdActivity.class);
                    break;
                case R.id.radio_button1:
                    setContainerView(CONTENT_ACTIVITY_NAME_1, JlcxActivity.class);
                    break;
                case R.id.radio_button2:
                    setContainerView(CONTENT_ACTIVITY_NAME_2, DdjdActivity.class);
                    break;

                default:
                    break;
            }
        }
    }

}