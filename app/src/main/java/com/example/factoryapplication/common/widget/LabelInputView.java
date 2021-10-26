package com.example.factoryapplication.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.factoryapplication.R;


/**
 * Created by dyc on 2016/10/11.
 */
public class LabelInputView extends LinearLayout{

    private TextView lable ;
    private EditText edit ;

    String lableStr , inputStr ;

    public LabelInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view =  View.inflate(context, R.layout.label_input,this) ;

        lable = (TextView) view.findViewById(R.id.tv_label);
        edit = (EditText) view.findViewById(R.id.tv_input);

        lableStr = attrs.getAttributeValue(null, "label");
        inputStr = attrs.getAttributeValue(null, "input");
        if (lableStr != null) {
            lable.setText(lableStr);
        }
        if (inputStr != null) {
            edit.setHint(inputStr);
        }
    }

    public void setLable(String val){
        lable.setText(val);
    }
    public void setValue(String val){
        edit.setText(val);
    }

    public String getValue(){
        if(edit != null)
            return edit.getText().toString() ;
        return "" ;
    }
}
