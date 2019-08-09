package com.ssky.example.hairdesign.util;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

public class ToolUtil {
    /**
     * 4.4及以上系统处理图片的方法
     * */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String handleImgeOnKitKat(Intent data, Context context) {
        String imagePath = null;
        Uri imageUri = data.getData();
        System.out.println("文件路径："+imageUri.toString());
        if (DocumentsContract.isDocumentUri(context,imageUri)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(imageUri);
            if ("com.android.providers.media.documents".equals(imageUri.getAuthority())) {
                //解析出数字格式的id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection,context);
            }else if ("com.android.providers.downloads.documents".equals(imageUri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null, context);
            }else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
                //如果是content类型的uri，则使用普通方式处理
                imagePath = getImagePath(imageUri,null, context);
            }else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
                //如果是file类型的uri，直接获取图片路径即可
                imagePath = imageUri.getPath();
            }
        }
        return imagePath;
    }
    /**
     * 通过uri和selection来获取真实的图片路径
     * */
    private String getImagePath(Uri uri, String selection,Context context) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
