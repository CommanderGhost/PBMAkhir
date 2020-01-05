package com.rental_apps.android.rental_apps.model.model_dashboard

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class DataInfoDashboard {
    @SerializedName("TOTAL")
    @Expose
    private var tOTAL: String? = null
    @SerializedName("MOBIL")
    @Expose
    var mOBIL: String? = null
    @SerializedName("TRANSAKSI")
    @Expose
    var tRANSAKSI: String? = null
    @SerializedName("ADMIN")
    @Expose
    var aDMIN: String? = null
    @SerializedName("USER")
    @Expose
    var uSER: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param mOBIL
     * @param tRANSAKSI
     * @param aDMIN
     * @param uSER
     */
    constructor(tOTAL: String?, mOBIL: String?, tRANSAKSI: String?, aDMIN: String?, uSER: String?) : super() {
        this.tOTAL = tOTAL
        this.mOBIL = mOBIL
        this.tRANSAKSI = tRANSAKSI
        this.aDMIN = aDMIN
        this.uSER = uSER
    }

    fun gettOTAL(): String? {
        return tOTAL
    }

    fun settOTAL(tOTAL: String?) {
        this.tOTAL = tOTAL
    }

}