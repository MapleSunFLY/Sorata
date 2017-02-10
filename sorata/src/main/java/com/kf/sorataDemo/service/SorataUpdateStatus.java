package com.kf.sorataDemo.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.kf.sorataDemo.common.MyConstants;
import com.kf.sorataDemo.loader.SorataLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by "sinlov" on 2017/2/8.
 */
public class SorataUpdateStatus {
    private static final String TAG = "FLY.Sorata.UpdateStatus";

    public void strongUpdate() throws IOException {
        String filePath = MyConstants.SDCard_FILEPATH;
        deletefile(filePath);

    }

    public void heatUpdate(Context context,boolean rebootApplication,String pathURL) {

        SorataGetHotPatchs sorataGetHotPatchs = new SorataGetHotPatchs();


        SharedPreferences sharedPreferences = context.getSharedPreferences(MyConstants.PF_INFORMATION_NAME, Context.MODE_PRIVATE);//获取SharedPreferences实例


        String versionMD5 = sharedPreferences.getString(MyConstants.PF_NEW_MD5, "");
        if (versionMD5 == null ||"".equals(versionMD5) ){
            Log.d(TAG, "versionMD5 is null");
            return;
        }

//        String filePath = sorataGetHotPatchs.downloadHotPatchs(context, pathURL, versionMD5);
        String filePath = sorataGetHotPatchs.fileDex(context,versionMD5);
        if (filePath==null||"".equals(filePath)){
            Log.d(TAG, "filePath is null");
            return;
        }

        try {
            new SorataLoader().tryLoadPatchFilesInternal(context, rebootApplication, MyConstants.SORATS_HEAT, filePath);
            Log.d(TAG, "SorataLoader ok");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Log.d(TAG, "SorataLoader not ");
        }


        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString(MyConstants.PF_OLD_MD5,versionMD5);//键值对
        editor.commit();//内容提交
    }

    public void notUpdate(Context context) {
        try {
            new SorataLoader().tryLoadPatchFilesInternal(context, false, MyConstants.SORATS_NOT, null);
            Log.d(TAG, "notUpdate SorataLoader ok");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Log.d(TAG, "notUpdate SorataLoader not ");
        }
    }


    /**
     * 删除某个文件夹下的所有文件夹和文件
     *
     * @param delpath String
     * @return boolean
     * @throws FileNotFoundException
     * @throws IOException
     */

    public boolean deletefile(String delpath) throws FileNotFoundException,

            IOException {

        try {

            File file = new File(delpath);

            if (!file.isDirectory()) {

                System.out.println("1");

                file.delete();

            } else if (file.isDirectory()) {

                System.out.println("2");

                String[] filelist = file.list();

                for (int i = 0; i < filelist.length; i++) {

                    File delfile = new File(delpath + "\\" + filelist[i]);

                    if (!delfile.isDirectory()) {

                        System.out.println("path=" + delfile.getPath());

                        System.out.println("absolutepath=" + delfile.getAbsolutePath());

                        System.out.println("name=" + delfile.getName());

                        delfile.delete();

                        System.out.println("删除文件成功");

                    } else if (delfile.isDirectory()) {

                        deletefile(delpath + "\\" + filelist[i]);

                    }

                }

                file.delete();

            }

        } catch (FileNotFoundException e) {

            System.out.println("deletefile() Exception:" + e.getMessage());

        }

        return true;

    }
}
