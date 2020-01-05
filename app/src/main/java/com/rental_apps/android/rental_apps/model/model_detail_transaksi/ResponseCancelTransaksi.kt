package com.rental_apps.android.rental_apps.model.model_detail_transaksi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Muhajir on 3/11/2018.
 */
class ResponseCancelTransaksi {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null
    @SerializedName("message")
    @Expose
    var message: String? = null

}