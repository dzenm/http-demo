package com.din.testhttp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    /*
     *  获取外部存储状态
     */
    private static boolean externalStatus() {
        String state = Environment.getExternalStorageState();
        //  如果状态不是mounted，无法读写
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        return true;
    }

    /**
     * 创建文件夹
     *
     * @param folderName
     * @return
     */
    public String getFolder(String folderName) {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/MyHjc/" + folderName);
        //  判断外部存储状态
        if (externalStatus()) {
            if (!file.exists()) {   //  如果该文件夹不存在，则进行创建
                file.mkdirs();      //  创建文件夹
            }
        }
        return file.getPath();
    }

    /**
     * 存储图片
     *
     * @param bitmap
     */
    public void savePhoto(Bitmap bitmap, String photoName) {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), photoName + ".jpg");
        FileOutputStream out = null;
        try {
            if (!file.isDirectory()) {
                file.createNewFile();
            }
            out = new FileOutputStream(file);
            if (out != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            try {
//                // 关闭流
//                out.flush();
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }


    /**
     * 读取图片
     *
     * @param direct
     * @param photoName
     * @return Bitmap
     */
    public Bitmap readPhoto(String direct, String photoName) {
        String path = getFolder(direct) + "/" + photoName + ".jpg";
        if (path != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            return bitmap;
        }
        return null;
    }


    /**
     * 复制文件到SD卡
     *
     * @param DATABASE_NAME 数据库名称
     */
    public void copyDBToSDcrad(String DATABASE_NAME) {

        String oldPath = "data/data/com.dinzhenyan.myhjc/databases/" + DATABASE_NAME + ".db";
        String newPath = Environment.getExternalStorageDirectory() + File.separator + DATABASE_NAME + ".db";
        copyFile(oldPath, newPath);
    }

    /**
     * 复制文件到SD卡
     *
     * @param DATABASE_NAME 数据库名称
     */
    public void copySDcradToDB(String DATABASE_NAME) {

        String oldPath = Environment.getExternalStorageDirectory() + File.separator + DATABASE_NAME + ".db";
        String newPath = "data/data/com.dinzhenyan.myhjc/databases/" + DATABASE_NAME + ".db";
        copyFile(oldPath, newPath);
    }

    /**
     * 复制文件
     *
     * @param oldPath
     * @param newPath
     */
    private static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            File newfile = new File(newPath);
            if (!newfile.exists()) {
                newfile.createNewFile();
            }
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

}