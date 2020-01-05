package com.rental_apps.android.rental_apps.admin

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.Gson
import com.mikepenz.itemanimators.SlideLeftAlphaAnimator
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.SPreferenced.SPref
import com.rental_apps.android.rental_apps.adapter.Carts
import com.rental_apps.android.rental_apps.adapter.DetailTransaksiAdapter
import com.rental_apps.android.rental_apps.api.client.api
import com.rental_apps.android.rental_apps.model.model_detail_transaksi.DataDetailTransaksi
import com.rental_apps.android.rental_apps.model.model_detail_transaksi.ResponseDetailTransaksi
import com.rental_apps.android.rental_apps.model.model_transaksi.DataTransaksi
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import customfonts.MyTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class ActivityDetailListTransaksi : AppCompatActivity(), InitComponent, View.OnClickListener {
    private var confirm: MyTextView? = null
    private var mContext: Context? = null
    private var recyclerCart: RecyclerView? = null
    //Declare Adapter
    private var mAdapter: DetailTransaksiAdapter? = null
    private val pDialog: SweetAlertDialog? = null
    //Declare Object Users
    var dataTransaksi: ResponseDetailTransaksi? = null
    var listDetailTransaksi: MutableList<DataDetailTransaksi> = ArrayList()
    var transaksi: DataTransaksi? = null
    override fun onCreate(SavedInstance: Bundle?) {
        super.onCreate(SavedInstance)
        setContentView(R.layout.activity_list_cart)
        val gson = Gson()
        transaksi = gson.fromJson(intent.getStringExtra("transaksi"), DataTransaksi::class.java)
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
        supportActionBar!!.title = transaksi!!.kODETRANSAKSI
    }

    override fun initUI() {
        recyclerCart = findViewById<View>(R.id.rCartList) as RecyclerView
        confirm = findViewById<View>(R.id.checkout) as MyTextView
    }

    override fun initValue() {
        prepareCart()
        getTransaksi()
        mAdapter!!.notifyDataSetChanged()
        //        setCheckout();
    }

    fun setCheckout() {
        confirm!!.text = "(Rp. " + String.format("%,.2f", Carts.totalAmount(SPref.CARTS).toString().toDouble()) + ") Checkout"
    }

    override fun initEvent() {
        confirm!!.setOnClickListener(this)
    }

    private fun prepareCart() {
        mAdapter = DetailTransaksiAdapter(mContext, listDetailTransaksi)
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

    fun getTransaksi() {
        val users = api.dataDetailTransaksi(transaksi!!.kODETRANSAKSI)
        users!!.enqueue(object : Callback<ResponseDetailTransaksi?> {
            override fun onResponse(call: Call<ResponseDetailTransaksi?>, response: Response<ResponseDetailTransaksi?>) {
                dataTransaksi = response.body()
                if (response.isSuccessful) {
                    if (dataTransaksi!!.status!!) {
                        listDetailTransaksi.clear()
                        listDetailTransaksi.addAll(dataTransaksi!!.data!!)
                        mAdapter!!.notifyDataSetChanged()
                    } else {
                        Toast.makeText(mContext, "Tidak Ada Data Ditemukan", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(mContext, "Tidak Ada Data Ditemukan", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseDetailTransaksi?>, t: Throwable) {
                Toast.makeText(mContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.checkout -> Toast.makeText(mContext, "Proses", Toast.LENGTH_LONG).show()
        }
    }
}