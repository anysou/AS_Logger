package com.anysou.aslogger;

/**
 * 作者：AnySou_HongDao
 * 时间：2020/1/9
 * 邮箱：anysou@126.com
  * 步骤：2
 * 默认配置
 */

public class ASLogIConfig {

    public static final String fileName = "ASLog.log"; //log日志的文件名称
    public static final String sqlName = "ASLog.db";  //log日志的sSQLite数据库名称

    private boolean T_About = true;      //吐司提示已调用本库
    private boolean SHOW_LOG  = false;   //是否显示日志
    private boolean WRITE_LOG = false;   //是否写日志
    private boolean SAVE_SQLite = false; //记录方式默认为非SQLite数据库
    private boolean AUTO_UPDATE = false; //容量满更新方式：false=删除更新；true=逐步替换更新（file文本存储记录方式才有效）
    private long fileSize = 104857;      //日志文件的大小，默认0.1M=1024*1024*0.1，以bytes为单位
    private long sqllen = 100;           //SQLite数据库记录条数，默认100

    private String TAG = "ASLOG";        //Logcat中显示的tag

    private static ASLogIConfig instance;

    //获取 实例
    public static ASLogIConfig getInstance() {
        if (instance == null) {
            synchronized (ASLogIConfig.class) {
                if (instance == null) {
                    instance = new ASLogIConfig();
                }
            }
        }
        return instance;
    }

    //设置是否本软件信息吐司
    public ASLogIConfig setToastAbout(boolean isToast){
        T_About = isToast;
        return this;
    }
    //获取是否本软件信息吐司
    public boolean getToastAbout() {
        return T_About;
    }

    //设置是否采用数据库记录方式
    public ASLogIConfig setSaveSQL(boolean isSaveSQL){
        SAVE_SQLite = isSaveSQL;
        return this;
    }
    //获取当前记录方式是否为数据库
    public boolean getSaveSQL() {
        return SAVE_SQLite;
    }

    //设置是否自动更新记录方式
    public ASLogIConfig setAutoUpdate(boolean isAutoUpdate){
        AUTO_UPDATE = isAutoUpdate;
        return this;
    }
    //获取当前记录方式是否为自动更新
    public boolean getAutoUpdate() {
        return AUTO_UPDATE;
    }

    //设置是否显示Log
    public ASLogIConfig setShowLog(boolean isShowLog){
        SHOW_LOG = isShowLog;
        return this;
    }
    //获取是否显示Log
    public boolean getIsShowLog() {
        return SHOW_LOG;
    }

    //设置是否写Log
    public ASLogIConfig setWriteLog(boolean isWriteLog){
        WRITE_LOG = isWriteLog;
        return this;
    }
    //获取是否写Log
    public boolean getIsWriteLog(){
        return WRITE_LOG;
    }

    //设置Log文件大小，默认0.1M,以bytes为单位
    public ASLogIConfig setFileSize(long size){
        this.fileSize = size;
        return this;
    }
    //查看设置Log文件大小
    public long getFileSize(){
        return this.fileSize;
    }

    //设置SQLite数据库记录条数
    public ASLogIConfig setSQLLen(long size){
        this.sqllen = size;
        return this;
    }
    //查看设置SQLite数据库记录条数
    public long getSQLLen(){
        return this.sqllen;
    }

    //设置标签
    public ASLogIConfig setTag(String tag){
        TAG = tag;
        return this;
    }
    //获取标签
    public String getTag(){
        return TAG;
    }

}
