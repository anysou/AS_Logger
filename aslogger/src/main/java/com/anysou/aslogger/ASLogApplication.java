package com.anysou.aslogger;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * 创建库的步骤：
 * 1、New –> Module -> Android Library -> Next -> 库名 （ASLogger）
 * 2、建立对外调用的类名，继承 Application。按本文件 基本为标准模板。实现使用时两种调用模式。
 *
 *使用方法：
 *
 * 一、将整个库原码导入到新项目中(不建议)：
 *   1、把整个module文件夹复制到 工程的根目录下。
 *   2、在工程的settings.gradle中增加对module的引用。 
 * 　　  由include ‘:app’变成include ‘:app:aslogger’
 *   3、在工程的主 module (Module: app) 中增加对库 module 的引用:
 * 　　  File -> Project Structure(项目结构) -> Dependencies(依赖关系) -> Modules 选中app
 *       ->Declare Dependencies(宣布依赖) -> + -> 3 Module Dependency -> 勾选 aslogger库 -> OK
 *   * 实际完成：在 app的 build.gradle 里 的 dependencies(依赖关系) 里添加了：
 *       implementation project(path: ':app:aslogger')
 *       或 compile project(path: ':app:aslog')  [老版本]
 *   4、在项目的 MainActivity.java 的 onCreate 输入： AS_LogApplication.About(this);  即可测试关于。
 *
 * 二、将库编译好的库文件 aar 引入（自用建议）：
 *   1、通过上例，可以在 as_log 库目录的 build/outputs/aar 下 有 aslogger_debug.aar 文件.
 *   2、复制 aar 文件到 要引用的 libs 目录下。（切换为 Project 模式下，复制到 libs 目录下 ）
 *   3、在工程的主 module (Module: app) 中增加对库 module 的引用:
 *      File -> Project Structure(项目结构) -> Dependencies(依赖关系) -> Modules 选中app
 *         ->Declare Dependencies(宣布依赖) -> + -> 2 Jar Dependency -> ok
 *   *  实际完成 在 app的 build.gradle 里 的 dependencies(依赖关系) 里添加了：
 *       implementation fileTree(dir: 'libs', include: ['*.aar', '*.jar'], exclude: [])
 *
 * 三、发布到 github上 使用 (分享建议)：
 *   1、设置github: file -> seting -> Version Control -> GitHub -> 
 *   2、 VCS -> Import into Version Control(导入版本控制) -> Share Project on GitHub (分享项目到GitHub)
 *   3、打开刚发布的项目 -> release -> 点按键release -> 发布版本号 1.0.0
 *   4、返回项目首页 -> Clone or download -> 复制项目下载地址
 *   5、打开https://jitpack.io -> 把复制的项目地址粘贴到输入框 -> Look up
 *
 *   其他项目调用 github 的使用方法：
 *   1、打开 根项目（注意：根项目） 的 build.gradle ，在 allprojects（所有项目） 的 repositories(仓库) 里添加：
 *      maven { url 'https://jitpack.io' }  //表示我们要用到的远程仓库的地址
 *   2、再 打开要调用远程库的 模块 的 的 build.gradle ，在 dependencies(依赖关系) 内添加：
 *      implementation 'com.github.anysou:AS_Logger:1.0.0'
 *   3、然后 File -> Sync Project with Gradle Filse (根据Gradle文件同步项目) 或点击 工具栏 对应图标。
 *   4、 此时，就可以使用该库了。 本库正确使用方法，请看readme.md .
 */

/**
 * 本库目的：
 *   用来方便显示打印、并记录日志。记录日志有两种方式：file文本、SQLite数据库。
 * 功能实现：
 *   1、可控制是否显示打印、或记录日志。（默认为否）
 *   2、可确定日志的记录方式：file文本、或 SQLite数据库。（默认为file文本）
 *   3、可设置日志的标签Tag。
 *   4、记录信息方式分：i信息info、w警告warn、e错误error
 *   5、记录日志容量：
 *      1） file文本方式：可设置记录文件的大小。（默认为：0.1M）
 *      2） SQLite数据库：可设置记录数据库的条数。（默认为：100条）
 *   6、超过规定容量，记录数据更新方式：逐步替换更新 或 删除更新。(默认为：删除更新)
* */

/**
 * 作者：AnySou_HongDao
 * 时间：2020/1/9
 * 邮箱：anysou@126.com
 * 步骤：1
 * Application
 */

public class ASLogApplication extends Application {

    private static Context mContenx;

    public static Context getAPP(){
        return mContenx;
    }

    // 对外、静态、方法。 可让调用在 继承 Application 时的 onCreate() 中 调用本方式 初始化
    public static void init(Context context){
            mContenx = context;
    }

    @Override  //可让需要调用的Application通过继承 AS_TLogApplication 方式，创建初始化
    public void onCreate() {
        super.onCreate();
        mContenx = this;
    }

    public static void About(Context context){
        Toast.makeText(context,"AS_Log 是一个日志工具包。可控制显示和存储、查看！!",Toast.LENGTH_LONG).show();
    }

}
