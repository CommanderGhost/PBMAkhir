package com.rental_apps.android.rental_apps.model.fasilitas

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Ujang Wahyu on 02/02/2018.
 */
class DataFasilitas
/**
 * No args constructor for use in serialization
 *
 */
{
    @SerializedName("ID_FASILITAS")
    @Expose
    private var iDFASILITAS: String? = null
    @SerializedName("FASILITAS")
    @Expose
    private var fASILITAS: String? = null
    @SerializedName("KETFASILITAS")
    @Expose
    private var kETFASILITAS: String? = null
    @SerializedName("BIAYA")
    @Expose
    private var bIAYA: String? = null

    //get
    fun getiDFASILITAS(): String? {
        return iDFASILITAS
    }

    fun getfASILITAS(): String? {
        return fASILITAS
    }

    fun getkETFASILITAS(): String? {
        return kETFASILITAS
    }

    fun getbIAYA(): String {
        return getbIAYA()
    }

    //set
    fun setiDFASILITAS(iDFASILITAS: String?) {
        this.iDFASILITAS = iDFASILITAS
    }

    fun setfASILITAS(fASILITAS: String?) {
        this.fASILITAS = fASILITAS
    }

    fun setkETFASILITAS(kETFASILITAS: String?) {
        this.kETFASILITAS = kETFASILITAS
    }

    fun setbIAYA(kETFASILITAS: String?) {
        bIAYA = bIAYA
    }
}