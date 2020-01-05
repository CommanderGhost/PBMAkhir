package com.rental_apps.android.rental_apps.model.model_detail_transaksi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class ResponseDetailTransaksi {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var data: List<DataDetailTransaksi>? = null

}