package com.mapping.filemapping.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/*
 * メディアアクセス権限管理
 */
public class PermissonManager {

    /*
     * 端末のメディア権限アクセス許諾状況の確認
     */
    static public boolean checkPermissonStatus(Context context){

        // メディアアクセス権限の確認
        String permissionStr = getRequestPermisson();
        int permission = ContextCompat.checkSelfPermission(context, permissionStr);

        return (permission == PackageManager.PERMISSION_GRANTED);
    }

    /*
     * 端末のメディア権限アクセス許諾状況の確認
     */
    static private String getRequestPermisson(){

        // API29
        if( Build.VERSION.SDK_INT == Build.VERSION_CODES.Q ){
            //※API29は、WRITEを要求しないとimageにアクセスできない
            return Manifest.permission.WRITE_EXTERNAL_STORAGE;
        }

        // API33以上
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ){
            return Manifest.permission.READ_MEDIA_IMAGES;
        }

        // それ以外
        return Manifest.permission.READ_EXTERNAL_STORAGE;
    }



    /*
     * メディアストレージアクセス権限付与
     */
    static public void permissionsStorage(Activity requestActivity, int requestCode) {

        //---------------------------------
        // API23未満なら、許可ダイアログは不要
        //---------------------------------
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        //----------------
        // API23以降
        //----------------
        //要求パーミッション
        String[] PERMISSIONS_STORAGE = new String[1];
        PERMISSIONS_STORAGE[0] = getRequestPermisson();

//        //要求パーミッション
//        //許可ダイアログは必須
//        String[] PERMISSIONS_STORAGE = {
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//        };
//        //API29のみ、WRITEを要求しないとimageにアクセスできないため、API29ならパーミッション上書き
//        if( Build.VERSION.SDK_INT == Build.VERSION_CODES.Q ){
//            PERMISSIONS_STORAGE[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
//        }
        //許可されていなければ、許可を要求
        ActivityCompat.requestPermissions(
                requestActivity,
                PERMISSIONS_STORAGE,
                requestCode
        );
    }

}
