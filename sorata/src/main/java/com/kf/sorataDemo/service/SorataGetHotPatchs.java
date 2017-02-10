package com.kf.sorataDemo.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kf.sorataDemo.common.MyConstants;
import com.kf.sorataDemo.loader.share.SharePatchFileUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by "sinlov" on 2017/2/4.
 */
public class SorataGetHotPatchs {
    private static final String TAG = "FLY.Sorata.GetHotPatchs";


    public String downloadHotPatchs(Context context,String path, String checkMd5) throws IOException {
        URL url = new URL(path);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();//得到基于HTTP协议的链接对象
        httpURLConnection.setConnectTimeout(5000);//请求超时时间
        httpURLConnection.setRequestMethod("GET");//请求方式

        if (httpURLConnection.getResponseCode() == 200) {

            InputStream is = httpURLConnection.getInputStream();

            String filea = MyConstants.SDCard_FILEPATH + File.separator + context.getPackageName() ;
            File files = new File(filea);
            files.mkdirs();
            if (files.exists()) {
                Log.d("FLY", files.getAbsolutePath());
            }
            String filePath = files.getAbsolutePath()+ File.separator + MyConstants.PATCH_DIRECTORY_NAME+MyConstants.PATCH_SUFFIX;
            File file = new File(filePath);
            if (file.exists()) {
                Log.d("FLY", "sourceDex exists "+file.getAbsolutePath());
                //删除
                file.delete();
            }
            FileOutputStream os = new FileOutputStream(filePath);
            int len = 0;
            byte[] buffer = new byte[1024];

            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            File file1 = new File(filePath);
            if (file1.exists()&& SharePatchFileUtil.verifyDexFileMd5(file, checkMd5)) {
                Log.d(TAG, "download Patchs " + httpURLConnection.getResponseCode());
                return filePath;
            }
        } else {
            Log.d(TAG, "请求失败" + httpURLConnection.getResponseCode());
        }
        return null;

    }


    public boolean CopyOfMyzipDecompressing(String zipPath, String filePath) throws IOException {
        long startTime = System.currentTimeMillis();
        boolean is = false;
        ZipInputStream Zin = new ZipInputStream(new FileInputStream(zipPath));//输入源zip路径
        BufferedInputStream Bin = new BufferedInputStream(Zin);//输出路径（文件夹目录）
        File Fout = null;
        ZipEntry entry;
        while ((entry = Zin.getNextEntry()) != null && !entry.isDirectory()) {
            Fout = new File(filePath, entry.getName());
            if (!Fout.exists()) {
                (new File(Fout.getParent())).mkdirs();
            }
            FileOutputStream out = new FileOutputStream(Fout);
            BufferedOutputStream Bout = new BufferedOutputStream(out);
            int b;
            while ((b = Bin.read()) != -1) {
                Bout.write(b);
            }
            Bout.close();
            out.close();
            Log.d(TAG, Fout + "解压成功");
            is = true;
        }
        Bin.close();
        Zin.close();
        long endTime = System.currentTimeMillis();
        Log.d(TAG, "耗费时间： " + (endTime - startTime) + " ms");
        return is;
    }

    //TEXT
    public static InputStream pull(Context context) throws IOException {

        InputStream pull = context.getResources().getAssets().open("classes.zip");// 获取assets下XM文件输出流
        return pull;
    }

    public String fileDex(Context context, String checkMd5) {

        String filea = MyConstants.SDCard_FILEPATH + File.separator + context.getPackageName() ;
        File files = new File(filea);
        if (!files.exists()) {
            files.mkdirs();
            if (files.exists()){
                Log.d(TAG, files.getAbsolutePath());
            }
        }else {
            Log.d(TAG, files.getAbsolutePath());
        }
        String filePath = files.getAbsolutePath()+ File.separator + MyConstants.PATCH_DIRECTORY_NAME+MyConstants.PATCH_SUFFIX;
        File file = new File(filePath);
        if (file.exists()) {
            Log.d(TAG, "sourceDex exists not " + file.getAbsolutePath());
            file.delete();
        }

        InputStream is = null;
        FileOutputStream os = null;

        try {
            is = SorataGetHotPatchs.pull(context);
            os = new FileOutputStream(filePath);

            int len = 0;
            byte[] buffer = new byte[1024];

            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            File file1 = new File(filePath);
            if (file1.exists()){
                Log.d(TAG, "pull and override is complete");
                return filePath;
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("FLY", "aaaaa");
        }finally {

            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
