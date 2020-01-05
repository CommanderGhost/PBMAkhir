package com.rental_apps.android.rental_apps.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.api.client.api
import com.rental_apps.android.rental_apps.helper.DateDifference.betweenDates
import com.rental_apps.android.rental_apps.model.model_detail_transaksi.DataDetailTransaksi
import com.rental_apps.android.rental_apps.model.model_detail_transaksi.ResponseCancelTransaksi
import com.rental_apps.android.rental_apps.user.ActivityDetailListHistory
import customfonts.MyTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by USER on 28/01/2018.
 */
class DetailHistoryAdapter(var mContext: Context, private val cartList: List<DataDetailTransaksi>) : RecyclerView.Adapter<DetailHistoryAdapter.MyViewHolder>() {
    private var lastPosition = -1
    private var mView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nama_mobil: MyTextView
        private val merk_mobil: MyTextView
        private val tanggal: MyTextView
        private val plat: MyTextView
        private val total: TextView
        private val cancel: TextView
        private val view: View
        fun bindItem(cart: DataDetailTransaksi) {
            val jumlah_hari = betweenDates(cart.tGLSEWA, cart.tGLAKHIRPENYEWAAN) + 1
            nama_mobil.text = cart.nAMAMOBIL
            merk_mobil.text = cart.mERKMOBIL
            tanggal.text = cart.tGLSEWA + " s/d " + cart.tGLAKHIRPENYEWAAN + " (" + jumlah_hari + " Hari)"
            total.text = "Rp. " + String.format("%,.2f", cart.tOTAL!!.toDouble())
            plat.text = cart.pLATNOMOBIL
            cancel.setOnClickListener { cancelTransaksi(cart.iDDETAILTRANSAKSI!!.toInt()) }
        }

        init {
            mView = view
            nama_mobil = view.findViewById<View>(R.id.nama_mobil) as MyTextView
            merk_mobil = view.findViewById<View>(R.id.merk_mobil) as MyTextView
            tanggal = view.findViewById<View>(R.id.tanggal) as MyTextView
            plat = view.findViewById<View>(R.id.plat) as MyTextView
            total = view.findViewById<View>(R.id.total) as TextView
            cancel = view.findViewById<View>(R.id.cancel) as TextView
            this.view = view
        }
    }

    fun cancelTransaksi(id: Int?) {
        val users = api.cancelTransaksi(id)
        users!!.enqueue(object : Callback<ResponseCancelTransaksi> {
            override fun onResponse(call: Call<ResponseCancelTransaksi>, response: Response<ResponseCancelTransaksi>) {
                if (response.isSuccessful) {
                    if (response.body().status!!) {
                        Toast.makeText(mContext, "Sukses Cancel", Toast.LENGTH_LONG).show()
                        (mContext as ActivityDetailListHistory).getTransaksi()
                    } else {
                        Toast.makeText(mContext, "Gagal Cancel", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(mContext, "Gagal Cancel", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseCancelTransaksi>, t: Throwable) {
                Toast.makeText(mContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.design_detail_history, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(cartList[position])
        setAnimation(mView, position)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    private fun setAnimation(viewToAnimate: View?, position: Int) {
        if (position > lastPosition) {
            lastPosition = position
            val animation = AnimationUtils.loadAnimation(mView!!.context, R.anim.slide_left_to_right)
            viewToAnimate!!.startAnimation(animation)
        }
    }

}