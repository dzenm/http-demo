package com.din.myhjc.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by dinzhenyan on 2018/4/21.
 */

public class PermissionUtil {

    //  9组24个危险权限
    private static final String[] PERMISSIONS_DANGEROUS = new String[]{
            "android.permission.READ_CALENDAR",
            "android.permission.WRITE_CALENDAR",                //日历权限  1
            "android.permission.CAMERA",                        //相机权限  2
            "android.permission.READ_CONTACTS",
            "android.permission.WRITE_CONTACTS",
            "android.permission.GET_ACCOUNTS",                  //联系人权限  5
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",        //定位权限  7
            "android.permission.RECORD_AUDIO",                  //麦克风权限  8
            "android.permission.READ_PHONE_STATE",
            "android.permission.CALL_PHONE",
            "android.permission.READ_CALL_LOG",
            "android.permission.WRITE_CALL_LOG",
            "android.permission.ADD_VOICAMAIL",
            "android.permission.USE_SIP",
            "android.permission.PROCESS_OUTGOING_CALLS",        //电话权限  15
            "android.permission.BODY_SENSORS",                  //传感器权限   16
            "android.permission.SEND_SMS",
            "android.permission.RECEIVE_SMS",
            "android.permission.READ_SMS",
            "android.permission.RECEIVE_WAP_PUSH",
            "android.permission.RECEIVE_MMS",                   //短信权限  21
            "android,permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"         //存储权限  23
    };

    public static void verifyPermissions(Activity activity, int[] requestCode) {
        for (int i = 0; i < requestCode.length; i++) {
            try {
                //  检测是否有指定的权限
                int permission = ActivityCompat.checkSelfPermission(
                        activity, PERMISSIONS_DANGEROUS[requestCode[i]]);
                //  如果包管理没有该权限,动态的申请权限
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, PERMISSIONS_DANGEROUS, requestCode[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void verifyPermissions(Activity activity, int requestCode) {
        try {
            //  检测是否有指定的权限
            int permission = ActivityCompat.checkSelfPermission(
                    activity, PERMISSIONS_DANGEROUS[requestCode]);
            //  如果包管理没有该权限,动态的申请权限
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_DANGEROUS, requestCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}