package com.rental_apps.android.rental_apps.model.fasilitas

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Ujang Wahyu on 02/02/2018.
 */
class ResponseFasilitas {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var data: List<DataFasilitas>? = null

}