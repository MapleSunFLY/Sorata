package com.kf.sorataDemo.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.kf.sorataDemo.common.MyConstants;

import org.json.JSONObject;


/**
 * Created by "sinlov" on 2017/2/4.
 */
public class SorataInformationParse {
    private static final String TAG = "FLY.Sorata.SorataInformationParse";

    public static String MD5PARSE = "";

    public void infirmationParse(final Context context, JSONObject jsonObject) {
        int apkVersion = Integer.parseInt("1");
//        SharedPreferences sharedPreferences = context.getSharedPreferences(MyConstants.PF_INFORMATION_NAME, Context.MODE_PRIVATE);//获取SharedPreferences实例
//        String name = sharedPreferences.getString("name", "数据为null");
//        String age = sharedPreferences.getString("age", "数据为null");
//
//
//
//
//
//
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
//        editor.putString("name",name);//键值对
//        editor.putString("age",age);
//        editor.commit();//内容提交

        Log.e("1111", "111111111");
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyConstants.PF_INFORMATION_NAME, Context.MODE_PRIVATE);//获取SharedPreferences实例
        final SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString(MyConstants.PF_NEW_MD5, "7CB69C6A070355C577316EDFA911914D");//键值对
        editor.commit();//内容提交
        String versionMD5 = sharedPreferences.getString("name", "1");
        final SorataUpdateStatus sorataUpdateStatus = new SorataUpdateStatus();
        if (versionMD5.equals("1")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sorataUpdateStatus.heatUpdate(context, true, "");
                    editor.putString("name", "0");
                    editor.commit();//内容提交
                }
            }).start();
        } else {
            sorataUpdateStatus.notUpdate(context);
        }
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            Log.d("FLY", "versionCode " + pi.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }


}
