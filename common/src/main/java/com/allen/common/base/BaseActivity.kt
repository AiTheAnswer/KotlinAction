package com.allen.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.allen.common.R
import com.allen.core_basic.mvp.BasicActivity

/**
 * Created by Allen
 * on 2018/9/22
 */
abstract class BaseActivity : BasicActivity() {

    private var mLayoutTile: View? = null
    private var mImgBack: ImageView? = null
    private var mTxtTile: TextView? = null
    private var mTxtRightTitle: TextView? = null
    private var mLayoutContent: FrameLayout? = null


    protected abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.black)
        if(getIsFullScreen()){
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)// 设置全屏
        }
        setContentView(R.layout.activity_base_main)
         initView()
        val view = LayoutInflater.from(this).inflate(getLayoutId(), null)
        mLayoutContent?.addView(view)
        initListener()
    }

    private fun initListener() {
        mImgBack?.setOnClickListener {
            finish()
        }
    }

    open fun getIsFullScreen():Boolean{
         return false
    }
    /**
     * 设置标题栏是否可见
     */
    protected fun setTitleVisible(visible:Boolean){
        if(visible){
            mLayoutTile?.visibility = View.VISIBLE
        }else{
            mLayoutTile?.visibility = View.GONE
        }

    }

    /**
     * 设置标题栏颜色
     */

     fun setTitleBackgroud(color: Int) {
        mLayoutTile?.setBackgroundColor(getColor(color))
    }

    /**
     * 设置标题
     * @param title 资源Id
     */
     fun setTitleRes(title: Int) {
        setTitle(getString(title))
    }

    /**
     * 设置标题
     * @param title 标题
     */
     fun setTitle(title: String) {
        mTxtTile?.text = title
    }


    private fun initView() {
        mLayoutTile = findViewById(R.id.base_layout_tile)
        mImgBack = findViewById(R.id.base_img_back)
        mTxtTile = findViewById(R.id.base_txt_title)
        mTxtRightTitle = findViewById(R.id.base_txt_right_title)
        mLayoutContent = findViewById(R.id.base_layout_content)
    }

}