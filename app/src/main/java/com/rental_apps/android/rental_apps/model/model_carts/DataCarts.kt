package com.rental_apps.android.rental_apps.model.model_carts

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class DataCarts(@field:Expose @field:SerializedName("ID_MOBIL") var iD_MOBIL: String, @field:Expose @field:SerializedName("NAMA_MOBIL") var nAMA_MOBIL: String, @field:Expose @field:SerializedName("MERK_MOBIL") var mERK_MOBIL: String, @field:Expose @field:SerializedName("PLAT_NO_MOBIL") var pLAT_NO_MOBIL: String, @field:Expose @field:SerializedName("TGL_SEWA") var tGL_SEWA: String, @field:Expose @field:SerializedName("TGL_AKHIR_PENYEWAAN") var tGL_AKHIR_PENYEWAAN: String, @field:Expose @field:SerializedName("HARGA_MOBIL") var hARGA_MOBIL: String, @field:Expose @field:SerializedName("TOTAL") var tOTAL: String)