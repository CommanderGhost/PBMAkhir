package com.rental_apps.android.rental_apps.model.model_detail_history

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by USER on 28/01/2018.
 */
class ResponseDetailHistory {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var data: List<DataDetailHistory>? = null

}