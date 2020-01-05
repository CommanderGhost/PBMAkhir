package com.rental_apps.android.rental_apps.model.model_mobil

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class ResponseRegisterCars {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var data: DataCars? = null

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
    constructor(status: Boolean?, message: String?, data: DataCars?) : super() {
        this.status = status
        this.message = message
        this.data = data
    }

}