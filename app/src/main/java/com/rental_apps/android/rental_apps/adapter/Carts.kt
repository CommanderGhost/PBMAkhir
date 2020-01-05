package com.rental_apps.android.rental_apps.adapter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pixplicity.easyprefs.library.Prefs
import com.rental_apps.android.rental_apps.model.model_carts.DataCarts
import java.util.*

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
object Carts {
    fun order(dataCarts: DataCarts, key: String?) {
        var ket = true
        val dCarts: ArrayList<DataCarts>
        dCarts = if (!Prefs.contains(key)) {
            ArrayList()
        } else {
            getOrder(key)
        }
        for (i in dCarts.indices) {
            if (dCarts[i].iD_MOBIL == dataCarts.iD_MOBIL) {
                dCarts[i].hARGA_MOBIL = dataCarts.hARGA_MOBIL
                dCarts[i].iD_MOBIL = dataCarts.iD_MOBIL
                dCarts[i].tGL_AKHIR_PENYEWAAN = dataCarts.tGL_AKHIR_PENYEWAAN
                dCarts[i].tGL_SEWA = dataCarts.tGL_SEWA
                dCarts[i].tOTAL = dataCarts.tOTAL
                ket = false
            }
        }
        if (ket) dCarts.add(dataCarts)
        saveOrder(dCarts, key)
    }

    fun saveOrder(list: ArrayList<DataCarts>?, key: String?) {
        val gson = Gson()
        val listCart = gson.toJson(list)
        Prefs.putString(key, listCart)
    }

    fun getOrder(key: String?): ArrayList<DataCarts> {
        val gson = Gson()
        val json = Prefs.getString(key, null)
        val type = object : TypeToken<ArrayList<DataCarts?>?>() {}.type
        return gson.fromJson(json, type)
    }

    fun getAllOrder(key: String?): String {
        val gson = Gson()
        return gson.toJson(getOrder(key))
    }

    fun getSize(key: String?): Int {
        return if (!Prefs.contains(key)) {
            0
        } else {
            val data = getOrder(key)
            data.size
        }
    }

    fun cancel(key: String?, position: Int) {
        val dCarts: ArrayList<DataCarts>
        val tempCarts = ArrayList<DataCarts>()
        dCarts = if (!Prefs.contains(key)) {
            ArrayList()
        } else {
            getOrder(key)
        }
        for (i in dCarts.indices) {
            if (i != position) {
                tempCarts.add(dCarts[i])
            }
        }
        saveOrder(tempCarts, key)
    }

    fun totalAmount(key: String?): Int {
        val dCarts: ArrayList<DataCarts>
        var total = 0
        dCarts = if (!Prefs.contains(key)) {
            ArrayList()
        } else {
            getOrder(key)
        }
        for (i in dCarts.indices) {
            total += dCarts[i].tOTAL.toInt()
        }
        return total
    }

    fun reset(key: String?) {
        saveOrder(ArrayList(), key)
    }
}