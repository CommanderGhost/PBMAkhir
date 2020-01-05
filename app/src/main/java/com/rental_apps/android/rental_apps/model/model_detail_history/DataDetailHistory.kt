package com.rental_apps.android.rental_apps.model.model_detail_history

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by USER on 28/01/2018.
 */
class DataDetailHistory {
    @SerializedName("ID_DETAIL_TRANSAKSI")
    @Expose
    var iDDETAILTRANSAKSI: String? = null
    @SerializedName("KODE_TRANSAKSI")
    @Expose
    var kODETRANSAKSI: String? = null
    @SerializedName("ID_MOBIL")
    @Expose
    var iDMOBIL: String? = null
    @SerializedName("TGL_SEWA")
    @Expose
    var tGLSEWA: String? = null
    @SerializedName("TGL_AKHIR_PENYEWAAN")
    @Expose
    var tGLAKHIRPENYEWAAN: String? = null
    @SerializedName("TGL_PENGEMBALIAN")
    @Expose
    var tGLPENGEMBALIAN: Any? = null
    @SerializedName("HARGA_MOBIL")
    @Expose
    var hARGAMOBIL: String? = null
    @SerializedName("TOTAL")
    @Expose
    var tOTAL: String? = null
    @SerializedName("STATUS_MOBIL")
    @Expose
    var sTATUSMOBIL: String? = null
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
    @SerializedName("CREATED_MOBIL")
    @Expose
    var cREATEDMOBIL: String? = null

}