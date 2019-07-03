package com.allen.business_home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.allen.business_home.contract.HomePresenter
import com.allen.common.base.BaseActivity
import com.allen.common.base.BasePresenter
import com.allen.common.config.ARouterConfig
import com.allen.common.util.Den
import com.allen.core_basic.mvp.IBasicView

@Route(path = ARouterConfig.HomeARouter.HOME_INIT)
class HomeActivity : BaseActivity(), View.OnClickListener {
    private var mLayoutContent:FrameLayout? = null
    //每日精选
    private var mTxtTitleDailySelect: TextView? = null

    //发现
    private var mTxtTitleFound: TextView? = null
    //热门
    private var mTxtTitleHot: TextView? = null
    //我的
    private var mTxtTitleMine: TextView? = null
    //图标的宽高
    private var mIconWidth: Int = 0
    private var mIconHeight: Int = 0

    /**
     * 主页Tab页的枚举
     */
    enum class TabMenu {

        DAILY_SELECT,//每日精选
        FOUND,//发现
        HOT,//热门
        MINE//我的
    }
    //当前选择的Tab页，默认是每日精选
    private var mSelectedTab: TabMenu = TabMenu.DAILY_SELECT

    //Tab标题选中的颜色
    private var mTabTitleUnSelectColor: Int = -1

    //Tab标题未选中的颜色
    private var mTabTitleSelectColor: Int = -1
    private var mIconSelectedDaily: Drawable? = null
    private var mIconNormalDaily: Drawable? = null
    private var mIconSelectedFound: Drawable? = null
    private var mIconNormalFound: Drawable? = null
    private var mIconSelectedHot: Drawable? = null
    private var mIconNormalHot: Drawable? = null
    private var mIconSelectedMine: Drawable? = null
    private var mIconNormalMine: Drawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initListener()
        initData()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun setPresenter(): BasePresenter<IBasicView> {
        return HomePresenter()
    }

    private fun initData() {
        mTabTitleSelectColor = resources.getColor(R.color.black, theme)
        mTabTitleUnSelectColor = resources.getColor(R.color.gray, theme)
        mIconSelectedDaily = resources.getDrawable(R.drawable.ic_home_selected, theme)
        mIconNormalDaily = resources.getDrawable(R.drawable.ic_home_normal, theme)
        mIconSelectedFound = resources.getDrawable(R.drawable.ic_discovery_selected, theme)
        mIconNormalFound = resources.getDrawable(R.drawable.ic_discovery_normal, theme)
        mIconSelectedHot = resources.getDrawable(R.drawable.ic_hot_selected, theme)
        mIconNormalHot = resources.getDrawable(R.drawable.ic_hot_normal, theme)
        mIconSelectedMine = resources.getDrawable(R.drawable.ic_mine_selected, theme)
        mIconNormalMine = resources.getDrawable(R.drawable.ic_mine_normal, theme)
        mIconWidth = Den.dip2px(this, 20)
        mIconHeight = Den.dip2px(this, 20)
        updateTab(TabMenu.DAILY_SELECT)
    }

    private fun initListener() {
        mTxtTitleDailySelect?.setOnClickListener(this)
        mTxtTitleFound?.setOnClickListener(this)
        mTxtTitleHot?.setOnClickListener(this)
        mTxtTitleMine?.setOnClickListener(this)
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.main_txt_title_daily_select -> switchTab(TabMenu.DAILY_SELECT)
            R.id.main_txt_title_found -> switchTab(TabMenu.FOUND)
            R.id.main_txt_title_hot -> switchTab(TabMenu.HOT)
            R.id.main_txt_title_mine -> switchTab(TabMenu.MINE)
        }
    }

    private fun switchTab(tabMenu: TabMenu) {
        if (tabMenu != mSelectedTab) {
            updateTab(tabMenu)
            mSelectedTab = tabMenu
        }
    }

    private fun updateTab(tabMenu: TabMenu) {
        mTxtTitleDailySelect?.setTextColor(
                if (tabMenu === TabMenu.DAILY_SELECT) mTabTitleSelectColor else mTabTitleUnSelectColor)
        mTxtTitleFound?.setTextColor(
                if (tabMenu === TabMenu.FOUND) mTabTitleSelectColor else mTabTitleUnSelectColor)
        mTxtTitleHot?.setTextColor(
                if (tabMenu === TabMenu.HOT) mTabTitleSelectColor else mTabTitleUnSelectColor)
        mTxtTitleMine?.setTextColor(
                if (tabMenu === TabMenu.MINE) mTabTitleSelectColor else mTabTitleUnSelectColor)
        val dailyIcon = if (tabMenu === TabMenu.DAILY_SELECT) mIconSelectedDaily else mIconNormalDaily
        dailyIcon?.setBounds(0, 0, mIconWidth, mIconHeight)
        mTxtTitleDailySelect?.setCompoundDrawables(null, dailyIcon, null, null)
        val foundIcon = if (tabMenu === TabMenu.FOUND) mIconSelectedFound else mIconNormalFound
        foundIcon?.setBounds(0, 0, mIconWidth, mIconHeight)
        mTxtTitleFound?.setCompoundDrawables(null, foundIcon, null, null)
        val hotIcon = if (tabMenu === TabMenu.HOT) mIconSelectedHot else mIconNormalHot
        hotIcon?.setBounds(0, 0, mIconWidth, mIconHeight)
        mTxtTitleHot?.setCompoundDrawables(null, hotIcon, null, null)
        val mineIcon = if (tabMenu === TabMenu.MINE) mIconSelectedMine else mIconNormalMine
        mineIcon?.setBounds(0, 0, mIconWidth, mIconHeight)
        mTxtTitleMine?.setCompoundDrawables(null, mineIcon, null, null)

    }

    private fun initView() {
        mTxtTitleDailySelect = findViewById(R.id.main_txt_title_daily_select)
        mTxtTitleFound = findViewById(R.id.main_txt_title_found)
        mTxtTitleHot = findViewById(R.id.main_txt_title_hot)
        mTxtTitleMine = findViewById(R.id.main_txt_title_mine)
    }
}