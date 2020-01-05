package com.rental_apps.android.rental_apps.utils

import android.text.TextUtils
import android.view.View
import android.widget.EditText
import com.rental_apps.android.rental_apps.helper.DatePickerView

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
object validate {
    fun cek(et: EditText): Boolean {
        var focusView: View? = null
        var cancel = false
        if (TextUtils.isEmpty(et.text.toString().trim { it <= ' ' })) {
            et.error = "Inputan Harus Di Isi"
            focusView = et
            cancel = true
        }
        if (cancel) {
            focusView!!.requestFocus()
        }
        return cancel
    }

    @JvmStatic
    fun cek(et: DatePickerView): Boolean {
        var focusView: View? = null
        var cancel = false
        if (TextUtils.isEmpty(et.text.toString().trim { it <= ' ' })) {
            et.error = "Inputan Harus Di Isi"
            focusView = et
            cancel = true
        }
        if (cancel) {
            focusView!!.requestFocus()
        }
        return cancel
    }

    @JvmStatic
    fun cekPassword(et: EditText, Password: String, ConfirmPassword: String): Boolean {
        var focusView: View? = null
        var cancel = false
        if (Password != ConfirmPassword) {
            et.error = "Password tidak sama"
            focusView = et
            cancel = true
        }
        if (cancel) {
            focusView!!.requestFocus()
        }
        return cancel
    }
}