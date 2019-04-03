package cn.mmvtc.mobilesafe3.chapter02.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.mmvtc.mobilesafe3.R;

/**
 * 自定义对话框：输入密码对话框的逻辑代码
 * */

public class InterPasswordDialog extends Dialog implements View.OnClickListener{
    private TextView mTitleTV;//标题
    public EditText mFirstPWDET;//首次输入密码文本框
    private Button mOKBtn;//确认按钮
    private Button mCancleBtn;//取消按钮
    private MyCallBack myCallBack;//回调接口

    public InterPasswordDialog(Context context){
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
        setContentView(R.layout.inter_password_dialog);

        mTitleTV = (TextView) findViewById(R.id.tv_interpwd_title);
        mFirstPWDET = (EditText) findViewById(R.id.et_inter_password);
        mOKBtn = (Button) findViewById(R.id.btn_comfirm);
        mCancleBtn = (Button) findViewById(R.id.btn_dismiss);
        mOKBtn.setOnClickListener(this);
        mCancleBtn.setOnClickListener(this);
    }

    public String getPassword(){
        return mFirstPWDET.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_comfirm:
                myCallBack.comfirm();
                break;
            case R.id.btn_dismiss:
                myCallBack.dismiss();
                break;
        }
    }

    public interface MyCallBack{
        void comfirm();
        void dismiss();
    }
}
