package com.rental_apps.android.rental_apps.adapter

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.SPreferenced.SPref
import com.rental_apps.android.rental_apps.helper.DateDifference.betweenDates
import com.rental_apps.android.rental_apps.model.model_detail_transaksi.DataDetailTransaksi
import com.rental_apps.android.rental_apps.user.ActivityListTransaksi
import customfonts.MyTextView
import es.dmoral.toasty.Toasty

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class DetailTransaksiAdapter(var mContext: Context, private val cartList: List<DataDetailTransaksi>) : RecyclerView.Adapter<DetailTransaksiAdapter.MyViewHolder>() {
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
            cancel.setOnClickListener {
                val builder = AlertDialog.Builder(mContext)
                builder.setTitle("Confirm")
                builder.setMessage("Are you sure?")
                builder.setPositiveButton("YES") { dialog, which ->
                    Carts.cancel(SPref.CARTS, adapterPosition)
                    cartList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    (mContext as ActivityListTransaksi).setCheckout()
                    // Do nothing, but close the dialog
                    dialog.dismiss()
                    Toasty.success(mContext, "berhasil dihapus", Toast.LENGTH_LONG).show()
                }
                // AlertDialog alert = builder.create();
                builder.show()
                //                    new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
//                            .setTitleText("Are you sure?")
//                            .setContentText("Won't be able to recover this file!")
//                            .setConfirmText("Yes,delete it!")
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sDialog) {
//                                    Carts.cancel(SPref.getCARTS(),getAdapterPosition());
//                                    cartList.remove(getAdapterPosition());
//                                    notifyItemRemoved(getAdapterPosition());
//                                    ((ActivityListTransaksi)mContext).setCheckout();
//
//                                    sDialog
//                                            .setTitleText("Deleted!")
//                                            .setContentText("Your data has been deleted!")
//                                            .setConfirmText("OK")
//                                            .setConfirmClickListener(null)
//                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//                                }
//                            })
//                            .show();
            }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.design_cart, parent, false)
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