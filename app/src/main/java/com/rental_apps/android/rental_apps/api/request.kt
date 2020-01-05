package com.rental_apps.android.rental_apps.api

import com.rental_apps.android.rental_apps.model.model_dashboard.ResponseInfoDashboard
import com.rental_apps.android.rental_apps.model.model_detail_transaksi.ResponseCancelTransaksi
import com.rental_apps.android.rental_apps.model.model_detail_transaksi.ResponseDetailTransaksi
import com.rental_apps.android.rental_apps.model.model_history.ResponseHistory
import com.rental_apps.android.rental_apps.model.model_mobil.ResponseCars
import com.rental_apps.android.rental_apps.model.model_mobil.ResponseRegisterCars
import com.rental_apps.android.rental_apps.model.model_transaksi.ResponseRegisterTransaksi
import com.rental_apps.android.rental_apps.model.model_transaksi.ResponseTransaksi
import com.rental_apps.android.rental_apps.model.model_user.ResponseLogin
import com.rental_apps.android.rental_apps.model.model_user.ResponseRegister
import com.rental_apps.android.rental_apps.model.model_user.ResponseUser
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
interface request {
    @FormUrlEncoded
    @POST("Api/auth")
    fun auth(@Field("USERNAME") username: String?,
             @Field("PASSWORD") password: String?): Call<ResponseLogin?>?

    @FormUrlEncoded
    @POST("Api/user")
    fun userRegister(@Field("NAME") name: String?,
                     @Field("NIK") nik: String?,
                     @Field("USERNAME") username: String?,
                     @Field("EMAIL") email: String?,
                     @Field("NO_TELP") no_telp: String?,
                     @Field("JENIS_KELAMIN") jenis_kelamin: Char?,
                     @Field("ALAMAT") alamat: String?,
                     @Field("PASSWORD") password: String?,
                     @Field("ACTIVATED") activated: Int?,
                     @Field("GROUP_USER") group_user: Int?): Call<ResponseRegister?>?

    @FormUrlEncoded
    @PUT("Api/user")
    fun userUpdate(@Field("ID_USER") ID_USER: String?,
                   @Field("NAME") name: String?,
                   @Field("NIK") nik: String?,
                   @Field("USERNAME") username: String?,
                   @Field("EMAIL") email: String?,
                   @Field("NO_TELP") no_telp: String?,
                   @Field("JENIS_KELAMIN") jenis_kelamin: String?,
                   @Field("ALAMAT") alamat: String?,
                   @Field("PASSWORD") password: String?,
                   @Field("ACTIVATED") activated: Int?,
                   @Field("GROUP_USER") group_user: Int?,
                   @Field("PHOTO") photo: String?): Call<ResponseRegister?>?

    @GET("Api/mobil")
    fun dataMobil(): Call<ResponseCars?>?

    @FormUrlEncoded
    @POST("Api/mobil")
    fun mobilRegister(@Field("NAMA_MOBIL") NAMA_MOBIL: String?,
                      @Field("MERK_MOBIL") MERK_MOBIL: String?,
                      @Field("DESKRIPSI_MOBIL") DESKRIPSI_MOBIL: String?,
                      @Field("TAHUN_MOBIL") TAHUN_MOBIL: String?,
                      @Field("KAPASITAS_MOBIL") KAPASITAS_MOBIL: String?,
                      @Field("HARGA_MOBIL") HARGA_MOBIL: String?,
                      @Field("WARNA_MOBIL") WARNA_MOBIL: String?,
                      @Field("BENSIN_MOBIL") BENSIN_MOBIL: Int?,
                      @Field("PLAT_NO_MOBIL") PLAT_NO_MOBIL: String?,
                      @Field("STATUS_MOBIL") STATUS_MOBIL: String?,
                      @Field("PHOTO") PHOTO: String?
    ): Call<ResponseRegisterCars?>?

    @FormUrlEncoded
    @PUT("Api/mobil")
    fun mobilUpdate(@Field("ID_MOBIL") ID_MOBIL: String?,
                    @Field("NAMA_MOBIL") NAMA_MOBIL: String?,
                    @Field("MERK_MOBIL") MERK_MOBIL: String?,
                    @Field("DESKRIPSI_MOBIL") DESKRIPSI_MOBIL: String?,
                    @Field("TAHUN_MOBIL") TAHUN_MOBIL: String?,
                    @Field("KAPASITAS_MOBIL") KAPASITAS_MOBIL: String?,
                    @Field("HARGA_MOBIL") HARGA_MOBIL: String?,
                    @Field("WARNA_MOBIL") WARNA_MOBIL: String?,
                    @Field("BENSIN_MOBIL") BENSIN_MOBIL: Int?,
                    @Field("PLAT_NO_MOBIL") PLAT_NO_MOBIL: String?,
                    @Field("STATUS_MOBIL") STATUS_MOBIL: String?,
                    @Field("PHOTO") PHOTO: String?
    ): Call<ResponseRegisterCars?>?

    @GET("Api/user/{GROUP_USER}/{ID_USER}")
    fun dataUser(
            @Path("GROUP_USER") GROUP_USER: Int?,
            @Path("ID_USER") ID_USER: Int?
    ): Call<ResponseUser?>?

    @GET("Api/pesanan")
    fun dataTransaksi(): Call<ResponseTransaksi?>?

    @GET("Api/dashboard")
    fun dataInfoDashboard(): Call<ResponseInfoDashboard?>?

    @FormUrlEncoded
    @POST("Api/pesanan")
    fun checkout(@Field("ID_USER") ID_USER: String?,
                 @Field("TOTAL_PEMBAYARAN") TOTAL_PEMBAYARAN: String?,
                 @Field("LIST_CART") LIST_CART: String?
    ): Call<ResponseRegisterTransaksi?>?

    @GET("Api/pesanan/{KODE_TRANSAKSI}")
    fun dataDetailTransaksi(
            @Path("KODE_TRANSAKSI") KODE_TRANSAKSI: String?
    ): Call<ResponseDetailTransaksi?>?

    @GET("api/pesanan/history/{ID_USER}")
    fun dataHistory(
            @Path("ID_USER") ID_USER: Int?
    ): Call<ResponseHistory?>?

    @GET("Api/fasilitas")
    fun dataFasilitas(): Call<ResponseCars?>?

    @DELETE("api/transaksi/{ID_DETAIL_TRANSAKSI}")
    fun cancelTransaksi(
            @Path("ID_DETAIL_TRANSAKSI") ID_DETAIL_TRANSAKSI: Int?
    ): Call<ResponseCancelTransaksi?>? //    @FormUrlEncoded
//    @POST("Api/pesanan")
//    Call<ResponseRegisterTransaksi> checkout(@Body DataCarts data);
}