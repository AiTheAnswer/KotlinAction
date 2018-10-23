package com.allen.kotlinaction

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

/**
 * Created by Allen
 * on 2018/9/13
 */
class RadioItemView(context: Context, attr: AttributeSet) : FrameLayout(context, attr) {


    private var mImgIcon: ImageView
    private var mTxtTitle: TextView
    private val mRootView: View = LayoutInflater.from(context).inflate(R.layout.layout_radio_item, null)
    private val mLayoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(200,
           200)

    init {
        mImgIcon = mRootView.findViewById(R.id.radio_img_icon)
        mTxtTitle = mRootView.findViewById(R.id.radio_item_txt_title)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        removeAllViews()
        mRootView.layoutParams = mLayoutParams
        addView(mRootView,0)
    }

    fun setSelectFlag(selectFlag: Boolean) {
        mImgIcon.isSelected = selectFlag
    }

    fun setIcon(icon: Drawable) {
        mImgIcon.setImageDrawable(icon)
    }

    fun setTitle(title: String) {
        mTxtTitle.text = title
    }
}