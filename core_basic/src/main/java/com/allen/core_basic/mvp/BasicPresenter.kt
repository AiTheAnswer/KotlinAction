package com.allen.core_basic.mvp

/**
 * Created by Allen
 * on 2018/9/22
 */

abstract class BasicPresenter<in T : IBasicView> : IBasicPresenter<T> {
    var basicView: IBasicView? = null

    override fun setView(basicView: T) {
        this.basicView = basicView
    }

    override fun unSubscribe() {
        basicView = null
    }
}