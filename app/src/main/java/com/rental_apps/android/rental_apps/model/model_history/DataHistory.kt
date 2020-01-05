package com.rental_apps.android.rental_apps.model.model_history

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by USER on 28/01/2018.
 */
class DataHistory {
    @SerializedName("KODE_TRANSAKSI")
    @Expose
    var kODETRANSAKSI: String? = null
    @SerializedName("ID_USER")
    @Expose
    var iDUSER: String? = null
    @SerializedName("TGL_ORDER")
    @Expose
    var tGLORDER: String? = null
    @SerializedName("TGL_SEWA")
    @Expose
    var tGLSEWA: String? = null
    @SerializedName("TGL_PENGEMBALIAN")
    @Expose
    var tGLPENGEMBALIAN: String? = null
    @SerializedName("TOTAL_PEMBAYARAN")
    @Expose
    var tOTALPEMBAYARAN: String? = null
    @SerializedName("TGL_PEMBAYARAN")
    @Expose
    var tGLPEMBAYARAN: String? = null
    @SerializedName("BUKTI_PEMBAYARAN")
    @Expose
    var bUKTIPEMBAYARAN: String? = null
    @SerializedName("STATUS_PEMBAYARAN")
    @Expose
    var sTATUSPEMBAYARAN: String? = null
    @SerializedName("STATUS_TRANSAKSI")
    @Expose
    var sTATUSTRANSAKSI: String? = null
    @SerializedName("NAME")
    @Expose
    var nAME: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param bUKTIPEMBAYARAN
     * @param tGLORDER
     * @param tGLSEWA
     * @param tGLPENGEMBALIAN
     * @param kODETRANSAKSI
     * @param sTATUSTRANSAKSI
     * @param tGLPEMBAYARAN
     * @param iDUSER
     * @param sTATUSPEMBAYARAN
     * @param tOTALPEMBAYARAN
     */
    constructor(kODETRANSAKSI: String?, iDUSER: String?, tGLORDER: String?, tGLSEWA: String?, tGLPENGEMBALIAN: String?, tOTALPEMBAYARAN: String?, tGLPEMBAYARAN: String?, bUKTIPEMBAYARAN: String?, sTATUSPEMBAYARAN: String?, sTATUSTRANSAKSI: String?, nAME: String?) : super() {
        this.kODETRANSAKSI = kODETRANSAKSI
        this.iDUSER = iDUSER
        this.tGLORDER = tGLORDER
        this.tGLSEWA = tGLSEWA
        this.tGLORDER = tGLPENGEMBALIAN
        this.tOTALPEMBAYARAN = tOTALPEMBAYARAN
        this.tGLPEMBAYARAN = tGLPEMBAYARAN
        this.bUKTIPEMBAYARAN = bUKTIPEMBAYARAN
        this.sTATUSPEMBAYARAN = sTATUSPEMBAYARAN
        this.sTATUSTRANSAKSI = sTATUSTRANSAKSI
        this.nAME = nAME
    }

}