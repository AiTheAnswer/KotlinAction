package com.allen.common.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import kotlin.properties.Delegates

/**
 * Created by Allen
 * on 2018/10/18
 */
class BaseApplication : Application() {
    //通过伴生对象实现静态
    companion object {
        var mApplication: BaseApplication by Delegates.notNull()
        private set//这行代码是将属性的set方法私有化
    }

    override fun onCreate() {
        super.onCreate()
        mApplication = this
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        ARouter.openLog()    // 打印日志
        ARouter.openDebug()  // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this)
    }
}