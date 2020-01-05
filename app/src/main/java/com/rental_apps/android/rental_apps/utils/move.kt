package com.rental_apps.android.rental_apps.utils

import android.content.Context
import android.content.Intent

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
object move : Intent() {
    @JvmStatic
    fun moveActivity(mContext: Context, activity: Class<*>?) {
        val i = Intent(mContext, activity)
        mContext.startActivity(i)
    }
}