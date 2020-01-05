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
import com.rental_apps.android.rental_apps.model.model_history.DataHistory
import com.rental_apps.android.rental_apps.user.ActivityDetailListHistory

/**
 * Created by Ujang Wahyu on 29/01/2018.
 */
/**
 * Created by USER on 28/01/2018.
 */
class HistoryAdapter(private val historyList: List<DataHistory>) : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {
    private var mContext: Context? = null
    private var lastPosition = 0
    private var mView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val view: View
        private val kode_transaksi: TextView
        private val tanggal_ambil: TextView
        private val tanggal_kembali: TextView
        private val status: TextView
        private val total_transaksi: TextView
        private val bg_transaksi: LinearLayout
        fun bindItem(transaksi: DataHistory) {
            kode_transaksi.text = transaksi.kODETRANSAKSI
            tanggal_ambil.text = transaksi.tGLSEWA
            tanggal_kembali.text = transaksi.tGLPENGEMBALIAN
            if (transaksi.sTATUSPEMBAYARAN === "1") status.text = "Lunas" else status.text = "Belum Lunas"
            total_transaksi.text = "Rp. " + String.format("%,.2f", transaksi.tOTALPEMBAYARAN.toString().toDouble())
            if (transaksi.sTATUSPEMBAYARAN == "0") {
                bg_transaksi.setBackgroundColor(Color.parseColor("#da4749"))
                status.text = "Belum Lunas"
            } else {
                bg_transaksi.setBackgroundColor(Color.parseColor("#29A9E1"))
                status.text = "Lunas"
            }
        }

        init {
            mView = view
            kode_transaksi = view.findViewById<View>(R.id.kode_transaksi) as TextView
            tanggal_ambil = view.findViewById<View>(R.id.tanggal_ambil) as TextView
            tanggal_kembali = view.findViewById<View>(R.id.tanggal_kembali) as TextView
            status = view.findViewById<View>(R.id.status) as TextView
            total_transaksi = view.findViewById<View>(R.id.total_transaksi) as TextView
            bg_transaksi = view.findViewById<View>(R.id.bg_transaksi) as LinearLayout
            this.view = view
            mContext = view.context
            view.setOnClickListener {
                val gson = Gson()
                val transaksi = gson.toJson(historyList[adapterPosition])
                val i = Intent(mContext, ActivityDetailListHistory::class.java)
                i.putExtra("transaksi", transaksi)
                mContext.startActivity(i)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.design_history, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(historyList[position])
        setAnimation(mView, position)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    private fun setAnimation(viewToAnimate: View?, position: Int) {
        if (position > lastPosition) {
            lastPosition = position
            val animation = AnimationUtils.loadAnimation(mView!!.context, R.anim.slide_left_to_right)
            viewToAnimate!!.startAnimation(animation)
        }
    }

}