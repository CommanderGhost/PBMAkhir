package com.rental_apps.android.rental_apps.user

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.mikepenz.itemanimators.SlideLeftAlphaAnimator
import com.pixplicity.easyprefs.library.Prefs
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.SPreferenced.SPref
import com.rental_apps.android.rental_apps.adapter.CartAdapter
import com.rental_apps.android.rental_apps.adapter.Carts
import com.rental_apps.android.rental_apps.api.client
import com.rental_apps.android.rental_apps.model.model_transaksi.ResponseRegisterTransaksi
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import com.rental_apps.android.rental_apps.utils.move.moveActivity
import customfonts.MyTextView
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class ActivityListTransaksi : AppCompatActivity(), InitComponent, View.OnClickListener {
    private var checkout: MyTextView? = null
    private var mContext: Context? = null
    private var recyclerCart: RecyclerView? = null
    //Declare Adapter
    private var mAdapter: CartAdapter? = null
    private var pDialog: ProgressDialog? = null
    override fun onCreate(SavedInstance: Bundle?) {
        super.onCreate(SavedInstance)
        setContentView(R.layout.activity_list_cart)
        mContext = this
        startInit()
    }

    override fun startInit() {
        initToolbar()
        initUI()
        initValue()
        initEvent()
    }

    override fun initToolbar() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun initUI() {
        recyclerCart = findViewById<View>(R.id.rCartList) as RecyclerView
        checkout = findViewById<View>(R.id.checkout) as MyTextView
    }

    override fun initValue() {
        prepareCart()
        mAdapter!!.notifyDataSetChanged()
        setCheckout()
    }

    fun setCheckout() {
        checkout!!.text = "(Rp. " + String.format("%,.2f", Carts.totalAmount(SPref.CARTS).toString().toDouble()) + ") Checkout"
    }

    override fun initEvent() {
        checkout!!.setOnClickListener(this)
    }

    private fun prepareCart() {
        mAdapter = CartAdapter(mContext, Carts.getOrder(SPref.CARTS))
        recyclerCart!!.setHasFixedSize(true)
        recyclerCart!!.layoutManager = LinearLayoutManager(mContext)
        recyclerCart!!.itemAnimator = DefaultItemAnimator()
        recyclerCart!!.adapter = mAdapter
        recyclerCart!!.itemAnimator = SlideLeftAlphaAnimator()
        recyclerCart!!.itemAnimator!!.addDuration = 500
        recyclerCart!!.itemAnimator!!.removeDuration = 500
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkout() { //        pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        pDialog.setTitleText("Loading");
//        pDialog.setCancelable(false);
//        pDialog.show();
        pDialog = ProgressDialog(this)
        //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog!!.setMessage("Loading")
        pDialog!!.setCancelable(false)
        // pDialog.setIndeterminate(false);
        pDialog!!.show()
        val checkout = client.api.checkout("" + Prefs.getInt(SPref.idUser, 0), "" + Carts.totalAmount(SPref.CARTS), Carts.getAllOrder(SPref.CARTS))
        checkout.enqueue(object : Callback<ResponseRegisterTransaksi> {
            override fun onResponse(call: Call<ResponseRegisterTransaksi>, response: Response<ResponseRegisterTransaksi>) {
                pDialog!!.hide()
                if (response.isSuccessful) {
                    if (response.body().status) {
                        pDialog = ProgressDialog(this@ActivityListTransaksi)
                        //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog!!.setMessage("Berhasil")
                        pDialog!!.setCancelable(false)
                        // pDialog.setIndeterminate(false);
                        Carts.reset(SPref.CARTS)
                        moveActivity(mContext!!, UserMain::class.java)
                        finish()
                        pDialog!!.show()
                        Toasty.success(mContext!!, "Pesanan berhasil", Toast.LENGTH_LONG).show()
                        //                        new ProgressDialog(mContext)
//                                .setTitle("Info?")
//                                .setTitle("Data berhasil disimpan!")
//                                .setConfirmText("Yes!")
//                                .setConfirmClickListener(new ProgressDialog().OnSweetClickListener() {
//                                    @Override
//                                    public void onClick(SweetAlertDialog sDialog) {
//                                        Carts.reset(SPref.getCARTS());
//                                        move.moveActivity(mContext, UserMain.class);
//                                        finish();
//                                    }
//                                })
//                                .show();
                    }
                }
            }

            override fun onFailure(call: Call<ResponseRegisterTransaksi>, t: Throwable) {
                pDialog!!.hide()
                Toasty.error(mContext!!, t.message.toString() + Carts.getAllOrder(SPref.CARTS), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.checkout -> if (Carts.getSize(SPref.CARTS) > 0) checkout() else Toasty.error(mContext!!, "Tidak ada pesanan yang diproses", Toast.LENGTH_SHORT).show()
        }
    }
}