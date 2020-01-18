package com.anysou.aslogger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * 作者：AnySou_HongDao
 * 时间：2020/1/9
 * 邮箱：anysou@126.com
 * 原创：WangJintao
 * 步骤：3
 * SQLite
 */

public class ASLogSQLite extends SQLiteOpenHelper {

    public static final int VERSION = 1; //注意版本不可以降级

    //没有库则创建；有则连接上
    public ASLogSQLite(@Nullable Context context) {
        //创建SQLite数据库; context=上下文、name=你的数据库名,可以null、factory=游标可以为null、version=定义你的数据库版本号
        super(context, ASLogIConfig.sqlName, null, VERSION);
        //Log.i("test", "数据库创建成功！");
    }

    @Override
    //创建库时触发，一般用来初始化数据表
    public void onCreate(SQLiteDatabase db) {
        //创建表
        db.execSQL("create table AS_Log(_id integer primary key autoincrement, msg text)");
        //System.out.println("onCreate");
        //Log.i("test", "表创建成功！");
    }

    @Override
    //数据库版本更新时触发，可根据版本号来做相应的数据库操作
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(VERSION==1){
        }else if (VERSION==2){
            //添加字段列
            db.execSQL("alter table AS_Log add date text");
        }
    }
}
