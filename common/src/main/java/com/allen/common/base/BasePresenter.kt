package com.allen.common.base

import com.allen.core_basic.mvp.BasicPresenter
import com.allen.core_basic.mvp.IBasicView
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Allen
 * on 2018/10/21
 */
abstract class BasePresenter<in T:IBasicView> : BasicPresenter<T>() {
    private var mCompositeDisposable: CompositeDisposable? = null
    override fun subscribe() {
        mCompositeDisposable = CompositeDisposable()
    }

    override fun unSubscribe() {
        mCompositeDisposable?.clear()
        super.unSubscribe()
    }
}