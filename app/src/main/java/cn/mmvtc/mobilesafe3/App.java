package cn.mmvtc.mobilesafe3;

import android.app.Application;
import android.content.SharedPreferences;
import android.telecom.TelecomManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * 功能：检测SIM卡是否发生变化
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        correctSIM();
    }

    public void correctSIM() {
        //检查SIM卡是否发生变化
        SharedPreferences sp = getSharedPreferences("config",MODE_PRIVATE);
        //获取防盗保护的状态
        boolean protecting = sp.getBoolean("protecting",true);
        if (protecting){
            //得到绑定SIM卡串号
            String bindSIM = sp.getString("sim","");
            //得到手机现在的SIM卡串号
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            //
            String realsim = tm.getSimSerialNumber();
            if (bindSIM.equals(realsim)) {
                Log.i("","sim卡未发生变化，还是你的手机");
            }else {
                Log.i("","SIM卡变化了");
                String safenumber = sp.getString("safenumber","");
                if (!TextUtils.isEmpty(safenumber)){
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(safenumber,null,
                            "你的亲友的SIM卡已经被更换！",null,null);
                }
            }
        }
    }
}
