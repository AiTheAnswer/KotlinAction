package com.allen.core_basic.mvp

/**
 * Created by Allen
 * on 2018/9/19
 */
interface IBasicPresenter<in T : IBasicView> {
    fun setView(basicView: T)

    fun subscribe()

    fun unSubscribe()
}