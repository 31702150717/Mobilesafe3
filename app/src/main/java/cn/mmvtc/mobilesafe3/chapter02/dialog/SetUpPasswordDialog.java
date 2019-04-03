package cn.mmvtc.mobilesafe3.chapter02.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import cn.mmvtc.mobilesafe3.R;

/**
 * 自定义对话框：设置密码对话框的逻辑代码
 * */

public class SetUpPasswordDialog extends Dialog implements View.OnClickListener {
    private TextView mTitleTV;//标题
    public EditText mFirstPWDET;//首次输入密码文本框
    public EditText mAffirmET;//再次输入确认密码文本框
    private MyCallBack myCallBack;//回调接口
    public SetUpPasswordDialog(Context context){
        super(context, R.style.dialog_custom);//引入自定义对话框样式
    }

    public void setCallBack(MyCallBack myCallBack){
        this.myCallBack = myCallBack;
    }

    //设置对话框标题
    public void setmTitle(String text){
        if (!TextUtils.isEmpty(text)){
            mTitleTV.setText(text);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_password_dialog);
        initView();
    }
    private void initView(){
        mTitleTV = (TextView) findViewById(R.id.tv_setuppwd_title);
        mFirstPWDET = (EditText) findViewById(R.id.et_firstpwd);
        mAffirmET = (EditText) findViewById(R.id.et_affirm_password);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_cancle).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                myCallBack.ok();
                break;
            case R.id.btn_cancle:
                myCallBack.cancle();
                break;
        }
    }

    public interface MyCallBack{
        void ok();
        void cancle();
    }
}
