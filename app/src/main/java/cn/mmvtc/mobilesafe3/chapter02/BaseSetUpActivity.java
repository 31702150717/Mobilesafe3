package cn.mmvtc.mobilesafe3.chapter02;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Toast;

import cn.mmvtc.mobilesafe3.R;

/**
 * Created by Administrator on 2019/4/3.
 */

public abstract class BaseSetUpActivity extends Activity {
    private GestureDetector gestureDetector;//手势识别器
    public SharedPreferences sp;//用于存储，存储SIM卡序列号（串号）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //作为手势滑动的父类，控制页面是未知的。
        sp = getSharedPreferences("config",MODE_PRIVATE);
        //1.手势识别器的初始化
        gestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (Math.abs(velocityX)<200){
                    Toast.makeText(getApplicationContext(), "无效动作，滑动太慢", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if ((e2.getRawX()-e1.getRawX())>200){
                    showPre();//从左向右滑动，显示上一个界面。
                    overridePendingTransition(R.anim.pre_in,R.anim.pre_out);
                    return true;
                }

                if ((e1.getRawX()-e2.getRawX())>200){
                    showNext();//从左向右滑动，显示下一个界面。
                    overridePendingTransition(R.anim.next_in,R.anim.next_out);
                    return true;
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }
    //滑动相关的方法
    public abstract void showNext();
    public abstract void showPre();

    //2.用手势识别器去识别事件，触发事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);//触摸屏幕，调用手势识别器。
        return super.onTouchEvent(event);
    }

    //3.开启新的Activity,关闭自己的页面
    public void startActivityAndFinishSelf(Class<?>cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
        finish();
    }
}
