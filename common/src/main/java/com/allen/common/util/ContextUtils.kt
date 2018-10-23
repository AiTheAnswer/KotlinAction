package com.allen.common.util

import android.content.Context

/**
 * Created by Allen
 * on 2018/9/19
 */
fun Context.dip2px(dpValue: Int): Int {
    return (this.resources.displayMetrics.density * dpValue + 0.5f).toInt()
}