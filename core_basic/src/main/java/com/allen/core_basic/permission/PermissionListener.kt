package com.allen.core_basic.permission

/**
 * Created by Allen
 * on 2018/9/20
 */
interface PermissionListener {
    fun onGranted(permissions: Array<out String>)

    fun onDenied(permissions: Array<out String>)
}