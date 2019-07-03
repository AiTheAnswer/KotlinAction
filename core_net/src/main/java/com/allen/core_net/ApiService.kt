package com.allen.core_net

import android.database.Observable
import com.allen.core_net.model.HomeBean
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Allen
 * on 2019/3/23
 */
interface ApiService {
    @GET("v2/feed?")
    fun getFristHomeData(@Query("num") num:Int):Observable<HomeBean>
}