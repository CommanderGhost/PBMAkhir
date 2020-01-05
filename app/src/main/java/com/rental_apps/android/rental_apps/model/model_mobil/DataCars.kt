package com.rental_apps.android.rental_apps.model.model_mobil

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class DataCars {
    @SerializedName("ID_MOBIL")
    @Expose
    var iDMOBIL: String? = null
    @SerializedName("NAMA_MOBIL")
    @Expose
    var nAMAMOBIL: String? = null
    @SerializedName("MERK_MOBIL")
    @Expose
    var mERKMOBIL: String? = null
    @SerializedName("DESKRIPSI_MOBIL")
    @Expose
    var dESKRIPSIMOBIL: String? = null
    @SerializedName("TAHUN_MOBIL")
    @Expose
    var tAHUNMOBIL: String? = null
    @SerializedName("KAPASITAS_MOBIL")
    @Expose
    var kAPASITASMOBIL: String? = null
    @SerializedName("HARGA_MOBIL")
    @Expose
    var hARGAMOBIL: String? = null
    @SerializedName("WARNA_MOBIL")
    @Expose
    var wARNAMOBIL: String? = null
    @SerializedName("BENSIN_MOBIL")
    @Expose
    var bENSINMOBIL: String? = null
    @SerializedName("PLAT_NO_MOBIL")
    @Expose
    var pLATNOMOBIL: String? = null
    @SerializedName("STATUS_SEWA")
    @Expose
    var sTATUSSEWA: String? = null
    @SerializedName("STATUS_MOBIL")
    @Expose
    var sTATUSMOBIL: String? = null
    @SerializedName("CREATED_MOBIL")
    @Expose
    var cREATEDMOBIL: String? = null
    @SerializedName("IMAGE")
    @Expose
    var iMAGE: List<String>? = null

}