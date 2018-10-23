package com.allen.core_basic.mvp

import com.allen.core_basic.permission.PermissionListener

/**
 * Created by Allen
 * on 2018/9/19
 */
interface IBasicView {
    fun toast(msg: String)

    fun requestPermission(pemissions: Array<String>, permissionListener: PermissionListener)
}