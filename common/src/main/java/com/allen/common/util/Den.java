package com.allen.common.util;

import android.content.Context;

/**
 * Created by Allen
 * on 2018/9/19
 */
public class Den {
    public static int dip2px(Context context,int dpValue){
        return (int) (context.getResources().getDisplayMetrics().density * dpValue + 0.5f);
    }
}
