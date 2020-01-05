package com.rental_apps.android.rental_apps.model.model_history

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by USER on 28/01/2018.
 */
class ResponseRegisterHistory {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var data: DataHistory? = null

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
    constructor(status: Boolean?, message: String?, data: DataHistory?) : super() {
        this.status = status
        this.message = message
        this.data = data
    }

}