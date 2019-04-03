package cn.mmvtc.mobilesafe3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import cn.mmvtc.mobilesafe3.chapter01.adapter.HomeAdapter;
import cn.mmvtc.mobilesafe3.chapter02.LostFindActivity;
import cn.mmvtc.mobilesafe3.chapter02.SetUp1Activity;
import cn.mmvtc.mobilesafe3.chapter02.dialog.InterPasswordDialog;
import cn.mmvtc.mobilesafe3.chapter02.dialog.SetUpPasswordDialog;
import cn.mmvtc.mobilesafe3.chapter02.utils.MD5Utils;

public class HomeActivity extends Activity {
    private GridView gv_home;
    private long mExitTime;
    private SharedPreferences msharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        msharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        gv_home = (GridView) findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter(HomeActivity.this));
        //每个条目的点击事件
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0://手机防盗
                        //判断是否设置过防盗密码
                        if (isSetUpPassword()){
                            showInterPswdDialog();
                        }else {
                            showSetUpPswdDialog();
                        }
                        break;
                    case 1://通讯卫士

                        break;
                    case 2://软件管家

                        break;
                    case 3://手机杀毒

                        break;
                    case 4://缓存清理

                        break;
                    case 5://进程管理

                        break;
                    case 6://流量管理

                        break;
                    case 7://高级工具

                        break;
                    case 8://设置中心

                        break;
                }
            }
        });
    }

    /**
     * 弹出设置密码对话框
     * */
    private void showSetUpPswdDialog(){
        final SetUpPasswordDialog setUpPasswordDialog = new SetUpPasswordDialog(HomeActivity.this);
        setUpPasswordDialog.setCallBack(new SetUpPasswordDialog.MyCallBack() {
            @Override
            public void ok() {
                //获得首次输入密码和再次输入密码的值
                String firstPwsd = setUpPasswordDialog.mFirstPWDET.getText().toString().trim();
                String affirmPwsd = setUpPasswordDialog.mAffirmET.getText().toString().trim();

                if (!TextUtils.isEmpty(firstPwsd) && !TextUtils.isEmpty(affirmPwsd)){
                    if (firstPwsd.equals(affirmPwsd)){
                        //两次密码一致，存储密码
                        savePswd(affirmPwsd);
                        //确认后，对话框消失
                        setUpPasswordDialog.dismiss();
                        //显示输入密码的对话框
                        showInterPswdDialog();
                    }else {
                        Toast.makeText(HomeActivity.this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(HomeActivity.this, "密码不能够为空！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void cancle() {
                setUpPasswordDialog.dismiss();
            }
        });
        setUpPasswordDialog.setCancelable(true);
        setUpPasswordDialog.show();
    }

    /**
     * 弹出输入密码对话框
     * */
    private void showInterPswdDialog(){
        final String password = getPassword();
        final InterPasswordDialog interPasswordDialog = new InterPasswordDialog(HomeActivity.this);
        interPasswordDialog.setCallBack(new InterPasswordDialog.MyCallBack() {
            @Override
            public void comfirm() {
                if (TextUtils.isEmpty(interPasswordDialog.getPassword())){
                    Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else if (password.equals(MD5Utils.encode(interPasswordDialog.getPassword()))){
                    //进入防盗页面
                    interPasswordDialog.dismiss();
                   // startActivity(new Intent(HomeActivity.this,LostFindActivity.class));
                    startActivity(SetUp1Activity.class);
                }else {
                    //对话框消失
                    interPasswordDialog.dismiss();
                    Toast.makeText(HomeActivity.this, "密码有误，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void dismiss() {
                interPasswordDialog.dismiss();
            }
        });
        interPasswordDialog.setCancelable(true);
        interPasswordDialog.show();
    }

    /**
     * 保存密码
     * */
    private void savePswd(String affirmPwsd){
        //手机防盗密码的保存
        SharedPreferences.Editor edit =  msharedPreferences.edit();
        edit.putString("PhoneAntiTheftPWD",MD5Utils.encode(affirmPwsd));
        edit.commit();
    }

    /**
     * 读取密码
     * */
    private String getPassword(){
        String password = msharedPreferences.getString("PhoneAntiTheftPWD",null);
        if (TextUtils.isEmpty(password)){
            return "";
        }

        return password;
    }

    /**
     * 判断用户是否设置过手机防盗密码
     * */
    private boolean isSetUpPassword() {
        String password = msharedPreferences.getString("PhoneAntiTheftPWD",null);
        if (TextUtils.isEmpty(password)){
            return false;
        }
        return true;
    }

    /**
     * 开启新的Activity不关闭自己
     * */
    public void startActivity(Class<?> cls){
        Intent intent = new Intent(HomeActivity.this,cls);
        startActivity(intent);
    }

    /**
    * 连续按两次退出程序
    * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if ((System.currentTimeMillis()-mExitTime)<2000){
                System.exit(0);
            }else {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
