package com.rental_apps.android.rental_apps.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.admin.ActivityDetailListTransaksi
import com.rental_apps.android.rental_apps.model.model_transaksi.DataTransaksi

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class TransaksiAdapter(private val transaksiList: List<DataTransaksi>) : RecyclerView.Adapter<TransaksiAdapter.MyViewHolder>() {
    private var mContext: Context? = null
    private var lastPosition = 0
    private var mView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val view: View
        private val kode_transaksi: TextView
        private val tanggal_pesanan: TextView
        private val nama_pemesan: TextView
        private val total_transaksi: TextView
        private val bg_transaksi: LinearLayout
        fun bindItem(transaksi: DataTransaksi) {
            kode_transaksi.text = transaksi.kODETRANSAKSI
            tanggal_pesanan.text = transaksi.tGLORDER
            nama_pemesan.text = transaksi.nAME
            total_transaksi.text = "Rp. " + String.format("%,.2f", transaksi.tOTALPEMBAYARAN.toString().toDouble())
            if (transaksi.sTATUSPEMBAYARAN == "0") {
                bg_transaksi.setBackgroundColor(Color.parseColor("#da4749"))
            }
        }

        init {
            mView = view
            kode_transaksi = view.findViewById<View>(R.id.kode_transaksi) as TextView
            tanggal_pesanan = view.findViewById<View>(R.id.tanggal_pesanan) as TextView
            nama_pemesan = view.findViewById<View>(R.id.nama_pemesan) as TextView
            total_transaksi = view.findViewById<View>(R.id.total_transaksi) as TextView
            bg_transaksi = view.findViewById<View>(R.id.bg_transaksi) as LinearLayout
            this.view = view
            mContext = view.context
            view.setOnClickListener {
                val gson = Gson()
                val transaksi = gson.toJson(transaksiList[adapterPosition])
                val i = Intent(mContext, ActivityDetailListTransaksi::class.java)
                i.putExtra("transaksi", transaksi)
                mContext.startActivity(i)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.design_transaksi, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(transaksiList[position])
        setAnimation(mView, position)
    }

    override fun getItemCount(): Int {
        return transaksiList.size
    }

    private fun setAnimation(viewToAnimate: View?, position: Int) {
        if (position > lastPosition) {
            lastPosition = position
            val animation = AnimationUtils.loadAnimation(mView!!.context, R.anim.slide_left_to_right)
            viewToAnimate!!.startAnimation(animation)
        }
    }

}