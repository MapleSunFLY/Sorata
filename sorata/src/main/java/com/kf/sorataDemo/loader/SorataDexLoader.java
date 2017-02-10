package com.kf.sorataDemo.loader;

import android.content.Context;
import android.util.Log;

import com.kf.sorataDemo.common.MyConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.PathClassLoader;

/**
 * Created by "sinlov" on 2017/2/4.
 */
public class SorataDexLoader {
    private static final String TAG = "FLY.Sorata.SorataDexLoader";
    private static ArrayList<File> loaderDex = new ArrayList<File>();

    static {
        loaderDex.clear();
    }

    public static void loadFixeDex(Context context, File fileDir) throws Throwable {
        if (context == null) {
            Log.d("FLY", "loadFixeDex Context is null");
            return;
        }
        //Dex文件目录
        File[] listFiles = fileDir.listFiles();
        for (File file : listFiles) {

            //判断后缀名
            if (file.getName().endsWith(MyConstants.DEX_SUFFIX)) {
                loaderDex.add(file);
            }

        }



        doDexInject(context, fileDir, loaderDex);
    }

    private static void doDexInject(Context context, File fileDir, List<File> loaderDex) throws Throwable {
        //系统ClassLoader
        PathClassLoader loader = (PathClassLoader) context.getClassLoader();
        //临时目录
        String optimizedDir = fileDir.getAbsolutePath() + File.separator + MyConstants.OPT_DEX;
        File optFile = new File(optimizedDir);
        if (!optFile.exists()) {
            optFile.mkdirs();
        }

        SystemClassLoaderAdder.installDexes(loader, optFile, loaderDex);
    }



}
