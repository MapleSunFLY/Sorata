package com.kf.sorataDemo.loader;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.kf.sorataDemo.common.MyConstants;
import com.kf.sorataDemo.loader.share.SharePatchFileUtil;
import com.kf.sorataDemo.service.SorataGetHotPatchs;

import java.io.File;

/**
 * Created by "sinlov" on 2017/2/4.
 */
public class SorataLoader {
    private static final String TAG = "FLY.Sorata.SorataLoader";

    public void tryLoadPatchFilesInternal(Context context,boolean rebootApplication,int sorataFlag,String SDFilePath) throws Throwable {


        File patchDirectoryFile = SharePatchFileUtil.getPatchDirectory(context);

        if (patchDirectoryFile == null) {
            Log.d(TAG, "tryLoadPatchFiles:getPatchDirectory == null");
            return;
        }
        String patchDirectoryPath = patchDirectoryFile.getAbsolutePath();
        if (!patchDirectoryFile.exists()) {
            Log.d(TAG, "tryLoadPatchFiles:patch dir not exist:" + patchDirectoryPath);
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyConstants.PF_INFORMATION_NAME, Context.MODE_PRIVATE);//获取SharedPreferences实例
        String version = sharedPreferences.getString(MyConstants.PF_NEW_MD5, "数据为null");
        if (sorataFlag == MyConstants.SORATS_NOT){
            version = sharedPreferences.getString(MyConstants.PF_OLD_MD5, "数据为null");
        }
        String patchName = SharePatchFileUtil.getPatchVersionDirectory(version);
        if (patchName == null) {
            Log.w(TAG, "tryLoadPatchFiles:patchName is null");
            //we may delete patch info file

            return;
        }
        //tinker/patch/patch-641e634c
        String patchVersionDirectory = patchDirectoryPath + "/" + patchName;
        File patchVersionDirectoryFile = new File(patchVersionDirectory);
        patchVersionDirectoryFile.mkdir();
        if (!patchVersionDirectoryFile.exists()) {
            Log.w(TAG, "tryLoadPatchFiles:onPatchVersionDirectoryNotFound");
            //we may delete patch info file
            return;
        }
        if (sorataFlag == MyConstants.SORATS_HEAT){
            boolean is = new SorataGetHotPatchs().CopyOfMyzipDecompressing(SDFilePath,patchVersionDirectory);
            if (!is){
                Log.w(TAG, "CopyOfMyzipDecompressing is no");
                return;
            }
        }
        SorataDexLoader.loadFixeDex(context,patchVersionDirectoryFile);
    }
}
