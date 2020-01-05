package com.rental_apps.android.rental_apps.model.model_user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class DataUser {
    @SerializedName("ID_USER")
    @Expose
    var id_user: Int? = null
    @SerializedName("NIK")
    @Expose
    var nik: String? = null
    @SerializedName("USERNAME")
    @Expose
    var username: String? = null
    @SerializedName("NAME")
    @Expose
    var name: String? = null
    @SerializedName("EMAIL")
    @Expose
    var email: String? = null
    @SerializedName("NO_TELP")
    @Expose
    var no_telp: String? = null
    @SerializedName("JENIS_KELAMIN")
    @Expose
    var jenis_kelamin: Char? = null
    @SerializedName("ALAMAT")
    @Expose
    var alamat: String? = null
    @SerializedName("PASSWORD")
    @Expose
    var password: String? = null
    @SerializedName("PHOTO")
    @Expose
    var photo: String? = null
    @SerializedName("ACTIVATED")
    @Expose
    var activated: Int? = null
    @SerializedName("CREATED")
    @Expose
    var created: String? = null
    @SerializedName("GROUP_USER")
    @Expose
    var group_user: Int? = null
    @SerializedName("LAST_LOGIN")
    @Expose
    var last_logn: String? = null
    @SerializedName("LAST_UPDATE")
    @Expose
    var last_update: String? = null

}