package com.rental_apps.android.rental_apps.model.model_transaksi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class ResponseRegisterTransaksi {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var data: DataTransaksi? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param message
     * @param status
     * @param data
     */
    constructor(status: Boolean?, message: String?, data: DataTransaksi?) : super() {
        this.status = status
        this.message = message
        this.data = data
    }

}