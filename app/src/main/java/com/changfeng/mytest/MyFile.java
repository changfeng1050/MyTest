package com.changfeng.mytest;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by changfeng on 2015/4/2.
 */
public class MyFile {

    private static final String TAG = "MyFile";

    public static String readFileSdcardFile(String fileName) throws IOException {
        String res="";
        try{
            FileInputStream fin = new FileInputStream(fileName);

            int length = fin.available();

            byte [] buffer = new byte[length];
            fin.read(buffer);

            res = EncodingUtils.getString(buffer, "UTF-8");

            fin.close();
        }

        catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    //写文件
    public static void writeSDFile(String fileName, String write_str) throws IOException {

        File file = new File(fileName);

        FileOutputStream fos = new FileOutputStream(file);

        byte [] bytes = write_str.getBytes();

        fos.write(bytes);

        fos.close();
    }

    //写数据到SD中的文件
    public static void writeFileSdcardFile(String fileName,String write_str) throws IOException {
        try{

            FileOutputStream fout = new FileOutputStream(fileName);
            byte [] bytes = write_str.getBytes();

            fout.write(bytes);
            fout.close();
        }

        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void appendToFile(String filename, String context) throws IOException {
        try {
            File file = new File(filename);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file.getAbsolutePath(), true);
            fileWriter.write(context);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
