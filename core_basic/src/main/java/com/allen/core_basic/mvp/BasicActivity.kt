package com.allen.core_basic.mvp

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.allen.core_basic.permission.PermissionListener
import java.util.*

/**
 * Created by Allen
 * on 2018/9/19
 */
abstract class BasicActivity : AppCompatActivity(), IBasicView {
    private var mPermissionList: ArrayList<String>? = null
    private val PERMISSION_REQUESST_CODE = 0x0001
    private var mPermissionListener: PermissionListener? = null
    abstract fun setPresenter(): BasicPresenter<IBasicView>

    private var mPresenter: BasicPresenter<IBasicView>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = setPresenter()
        mPresenter?.setView(this)
    }

    override fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun requestPermission(pemissions: Array<String>, permissionListener: PermissionListener) {
        mPermissionListener = permissionListener
        if (mPermissionList == null) {
            mPermissionList = ArrayList()
        }
        for (i in 0..pemissions.size) {
            mPermissionList?.clear()
            if (ContextCompat.checkSelfPermission(this, pemissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList?.add(pemissions[i])
            }
        }
        if (mPermissionList?.isEmpty()!!) {
            permissionListener.onGranted(pemissions)
        } else {
            ActivityCompat.requestPermissions(this, mPermissionList?.toArray(pemissions)!!, PERMISSION_REQUESST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUESST_CODE) {
            for (i in 0..grantResults.size) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    val permissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])
                    if (permissionRationale) {
                        mPermissionListener?.onDenied(permissions)
                    }
                } else {
                    mPermissionListener?.onGranted(permissions)
                }
            }

        }
    }
}