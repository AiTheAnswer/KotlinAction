package com.allen.business_launch

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.allen.business_launch.contract.LaunchPresenter
import com.allen.business_launch.contract.LauncherContract
import com.allen.common.base.BaseActivity
import com.allen.common.config.ARouterConfig
import com.allen.core_basic.mvp.BasicPresenter
import com.allen.core_basic.mvp.IBasicView
import java.util.*

class LaunchActivity : BaseActivity(), LauncherContract.View {
    override fun  setPresenter(): BasicPresenter<IBasicView> {
        return LaunchPresenter()
    }


    override fun toHome() {
        ARouter.getInstance().build(ARouterConfig.HomeARouter.HOME_INIT).navigation()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_launch
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()

    }

    override fun getIsFullScreen(): Boolean {
        return true
    }

    private fun initData() {
        val timer = Timer()
        timer.schedule(object :TimerTask(){
            override fun run() {
                runOnUiThread {
                    toHome()
                    finish()
                }
            }
        },3000)

    }
}
