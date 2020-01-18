package com.anysou.aslogger;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 作者：AnySou_HongDao
 * 时间：2020/1/9
 * 邮箱：anysou@126.com
 * 原创：WangJintao
 * 步骤：4
 * 记录读写操作： file 文件 \ SQLite 数据库
 * 参考： 在android中的文件放在不同位置，它们的读取方式也有一些不同。
 * https://www.cnblogs.com/bastard/archive/2013/03/04/2943302.html
 */

/**
 * 中文字符所占的字节数取决于字符的编码方式，
 * 一般情况下，采用ISO8859-1编码方式时，一个中文字符与一个英文字符一样只占1个字节；
 * 采用GB2312或GBK编码方式时，一个中文字符占2个字节；
 * 而如果采用UTF-8编码方式时，一个中文字符会占3个字节。"测试".getBytes("UTF-8").length
 *  */

public class ASLogFileUtils {

    private static Object obj = new Object();
    private static int fileMaxSize =  (int) ASLogIConfig.getInstance().getFileSize();
    private static int sqlMaxSize =  (int) ASLogIConfig.getInstance().getSQLLen();
    private static String TAG = "test";


    public static void writeLog(String msg) {
        if(msg==null || msg=="") return;
        if(ASLogIConfig.getInstance().getSaveSQL()){
            writeLogSQL(msg);
        } else{
            writeLogFile(msg);
        }
    }

    public static String readLog(){
        if(ASLogIConfig.getInstance().getSaveSQL()){
            return readLogSQL();
        } else{
            return readLogText();
        }
    }

    public static String[] readLogList(){
        return readLog().split("\\n|\\r"); //按换行符号分割为数组
    }

    //写日志文件
    private static void writeLogFile(String msg) {
        synchronized (obj) { //synchronized 代表这个方法加锁
            try {
                //创建文件               文件位置                                  文件名
                File file = new File(ASLogApplication.getAPP().getFilesDir(), ASLogIConfig.fileName);
                FileWriter fw = null;
                if (file.exists()) { //文件存在
                    if (file.length() > fileMaxSize) {
                        //Log.i(TAG, "文件长的大于设定值" + fileMaxSize);
                        if (ASLogIConfig.getInstance().getAutoUpdate()) { //自动更新模式

                            String allmsg = readLogText();  //读取 原有的文件
                            String[] arrayList = allmsg.split("\\n|\\r"); //按换行符号分割为数组
                            int addlen = msg.getBytes("UTF-8").length; //要添加文字长度
                            if (addlen < fileMaxSize / 2) {
                                addlen = (int) fileMaxSize / 2;
                            }
                            int j = 0;
                            fw = new FileWriter(file, false); //重新写
                            for (int i = 0; i < arrayList.length; i++) {
                                j = arrayList[i].getBytes("UTF-8").length;
                                addlen -= j;
                                //Log.i(TAG, arrayList[i]);
                                arrayList[i] = "";
                                if (addlen <= 0) {
                                    fw.write(arrayList[i]);
                                    fw.write(13);
                                    fw.write(10);
                                }
                            }
                        } else {
                            fw = new FileWriter(file, false); //重新写
                        }
                    }
                    else
                        fw = new FileWriter(file, true); //在原文件基础上写
                } else
                    fw = new FileWriter(file, false);

                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("MM-dd HH:mm:ss");
                String dateStr = s.format(d);

                fw.write(String.format("[%s] %s", dateStr, msg));
                fw.write(13);  //下面两句实现换行
                fw.write(10);
                fw.flush();  //刷新此输出流，并强制将所有已缓冲的输出字节写入该流中
                fw.close();
            } catch (Throwable ex) {
                ex.printStackTrace();
                Log.e("ASLOG","发生错误"+ex.toString());
            }
        }
    }

    //读文本日志
    private static String readLogText() {
        FileReader fr = null;
        try {
            File file = new File(ASLogApplication.getAPP().getFilesDir(), ASLogIConfig.fileName);
            if (!file.exists()) {
                return "";
            }
            long n = ASLogIConfig.getInstance().getFileSize();  //文件最大值
            long len = file.length();  //文件大小
            long skip = len - n;
            fr = new FileReader(file);
            fr.skip(Math.max(0, skip));  //从数据流中跳过n个字节
            char[] cs = new char[(int) Math.min(len, n)];
            fr.read(cs);
            return new String(cs).trim();
        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fr != null)
                    fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    //写日志文件 SQLite
    private static void writeLogSQL(String msg){
        synchronized (obj) { //synchronized 代表这个方法加锁
            try {
                ASLogSQLite mylogsql = new ASLogSQLite(ASLogApplication.getAPP());
                //无库则建立库；有库则连接
                SQLiteDatabase db = mylogsql.getWritableDatabase();
                Cursor cursor = db.rawQuery("select count(*) from AS_Log",null);
                cursor.moveToFirst(); //游标移到第一条记录准备获取数据
                Long count = cursor.getLong(0); // 获取数据中的LONG类型数据
                cursor.close();
//                Log.e(TAG, "数据共："+count);
                int id = 0;
                if(count>=sqlMaxSize){
                    cursor = db.query("AS_Log",new String[]{"_id,msg"},null,null,null,null,"_id asc","0,1");
                    while (cursor.moveToNext()) { //当游标正常下移（默认BeforeFirst）
                        id=cursor.getInt(0);  //获取最早的ID号
//                        String oldmsg=cursor.getString(1);
//                        Log.i("test",oldmsg);
                    }
                    cursor.close();
                    db.delete("AS_Log","_id=?",new String[]{""+id}); //删除
                }

                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("MM-dd HH:mm:ss");
                String dateStr = s.format(d);
                ContentValues values=new ContentValues();
                values.put("msg",String.format("[%s] %s", dateStr, msg));
                db.insert("AS_Log",null,values);  //插入数据
                db.close();
            } catch (Throwable ex) {
                ex.printStackTrace();
                Log.e("ASLOG","写SQL发生错误"+ex.toString());
            } finally {

            }
        }
    }

    //读 SQLite
    private static String readLogSQL(){
        try{
            ASLogSQLite mylogsql = new ASLogSQLite(ASLogApplication.getAPP());
            //无库则建立库；有库则连接
            SQLiteDatabase db = mylogsql.getWritableDatabase();
            Cursor cursor = db.query("AS_Log",new String[]{"_id,msg"},null,null,null,null,"_id desc",null);
            String msg = "";
            while (cursor.moveToNext()) { //当游标正常下移（默认BeforeFirst）
                //Log.i("test",cursor.getString(1));
                msg += cursor.getString(1)+"\n";
            }
            cursor.close();
            db.close();
            return msg;
        } catch (Throwable ex){
            ex.printStackTrace();
            Log.e("ASLOG","读SQL发生错误"+ex.toString());
            return "";
        }finally {

        }
    }


}
