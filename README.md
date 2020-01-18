# AS_Logger
Android的多功能日志工具，支持Logcat输出显示和文件记录。
## 功能简介
* 控制台日志显示，支持配置TAG过滤，可自定义修改TAG。（默认：不显示打印日志）
* 日志信息方式分三种：i信息info、w警告warn、e错误error
* 日志文件可以保存在APP工作目录下。（默认：不保存）
* 日志内容保存方式可选：file文件（默认：0.1M） 或 SQLite数据库（默认：100条）。（默认：file文本）
## 版本说明
* v1.0.0 项目初始提交
## 使用介绍
1. 在项目的 根**build.gradle** -> allprojects(所有项目)->repositories(仓库) 中添加 GitHub的仓库地址 https://jitpack.io：<br>
    ```
    allprojects {
    	repositories {
    		...
    		maven { url 'https://jitpack.io' }
        }
    }
    ```
2. 在要引用项目中 的**build.gradle** -> dependencies(依赖关系) 中添加：<br>
    ```
    dependencies {
        implementation 'com.github.anysou:AS_Logger:1.1.0'
    }

    ```
3. 项目中调用ASLog有三种方法（建议使用B.B）：
    A) 如果你的项目中没有更改 Application，在**AndroidManifest.xml**中配置如下：<br>
    ```
    <manifest>
        <application
            android:name="com.anysou.as_logger.ASLogApplication"
            ...
        </application>
    </manifest>
    ```
    
   B) 如果你在项目中有自己的 Application文件，如：<br>
    ```
    <manifest>
           <application
               android:name=".MyApplication"
               ...
           </application>
    </manifest>
    ```
    B.A) 那么，你可以让自己的 MyApplication 继承 ASLogApplication：<br>
    ```
    public class MyApplication extends ASLogApplication {
        @Override
        public void onCreate() {
            super.onCreate();
            ...
        }
    }
    ```
    B.B) 或者你可以在onCreate()中调用ASLogApplication.init(this);<br>
    ```
    public class MyApplication extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            ASLogApplication.init(this);
            ...
        }
    }
    ```
 4. 相关配置设置：<br>
    ```
    public class MyApplication extends Application {
        @Override
        public void onCreate() {
            super.onCreate();

        ASLogApplication.init(this);
        ASLogIConfig.getInstance()
                .setShowLog(true)     //是否在logcat中显示log,默认不显示。
                .setWriteLog(true)    //是否在文件中记录，默认不记录。
                .setFileSize(100000)  //日志文件的大小，默认0.1M,以bytes为单位。
                .setSQLLen(100)       //设置SQLite数据库的数据最大条数。默认100条。
                .setSaveSQL(true)     //设置为file文本存储记录方式，true=SQLite数据库。
                .setAutoUpdate(true)  //设置为逐步替换更新模式。（file文本存储记录方式才有效）
                .setTag("myTag");     //logcat日志过滤tag。
        }
    }
    ```
 5. 显示/记录日志
    * 带标题<br>
        ```
        ASLogger.i(title, log);
        ASLogger.w(title, log);
        ASLogger.e(title, log);
        ```
        ![带标题](https://github.com/anysou/AS_Log/blob/master/pictures/pic1.png)
    * 无标题<br>
        ```
        ASLogger.i(log);
        ASLogger.w(log);
        ASLogger.e(log);
        ```
        ![无标题](https://github.com/anysou/AS_Log/blob/master/pictures/pic2.png)
 6. 查看本地日志
    * 字符串方式<br>
    ```
    String log = ASLogFileUtils.readLog();
    ```
    ![本地日志](https://github.com/anysou/AS_Log/blob/master/pictures/pic3.png)
    * 字符串数组方式<br>
    ```
    String[] loglist = ASLogFileUtils.readLogList();
    ```
    ![本地日志](https://github.com/anysou/AS_Log/blob/master/pictures/pic4.png)

## 使用样板 ASLoggerSample
 在样例程序中，采用了B.B调用方式。<br>
 样例中有三个按键：1）记录日志、2）TextViem显示日志、3）ListViem显示日志、4）调用实时logcat<br>
 ![Sample展示](https://github.com/anysou/AS_Log/blob/master/pictures/pic0.png)

### 引用项目
| ||
|-|-|
|实时logcat | [Lynx](https://github.com/pedrovgs/Lynx) |
|实时logcat使用方法 | [Lynx](https://github.com/anysou/Lynx) |