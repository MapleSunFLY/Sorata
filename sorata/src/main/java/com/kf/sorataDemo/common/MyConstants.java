package com.kf.sorataDemo.common;

import android.os.Environment;

import java.io.File;

/**
 * Created by "sinlov" on 2017/2/3.
 */
public interface MyConstants {

    int BUFFER_SIZE         = 16384;
    int MD5_LENGTH          = 32;
    int MD5_FILE_BUF_LENGTH = 1024 * 100;

    String PF_INFORMATION_NAME = "preferences_information_name";
    String PF_OLD_MD5 = "old_md5";
    String PF_NEW_MD5 = "new_md5";

    String SDCard_FILEPATH = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"Android"+File.separator+"date";
    String SORATA_DEX      = "sorata_dex";
    String OPT_DEX         = "opt_dex";
    String DEX_SUFFIX      = ".dex";

    String DEXMODE_RAW = "raw";
    String DEXMODE_JAR = "jar";
    String DEX_IN_JAR  = "classes.dex";

    String PATCH_BASE_NAME = "patch-";
    String PATCH_SUFFIX    = ".apk";

    String PATCH_DIRECTORY_NAME       = "sorata";
    String PATCH_TEMP_DIRECTORY_NAME  = "sorata_temp";
    String PATCH_TEMP_LAST_CRASH_NAME = "sorata_last_crash";

    String PATCH_INFO_NAME      = "patch";

    String RES_ARSC            = "resources.arsc";

    int  SORATS_STRONG = 0x00;
    int  SORATS_HEAT   = 0x01;
    int  SORATS_NOT    = 0x02;

}
