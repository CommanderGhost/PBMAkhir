package com.rental_apps.android.rental_apps.user

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
import com.rental_apps.android.rental_apps.adapter.DetailHistoryAdapter
import com.rental_apps.android.rental_apps.api.client
import com.rental_apps.android.rental_apps.model.model_detail_transaksi.DataDetailTransaksi
import com.rental_apps.android.rental_apps.model.model_detail_transaksi.ResponseDetailTransaksi
import com.rental_apps.android.rental_apps.model.model_transaksi.DataTransaksi
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import customfonts.MyTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ActivityDetailListHistory : AppCompatActivity(), InitComponent, View.OnClickListener {
    private val confirm: MyTextView? = null
    private var mContext: Context? = null
    private var recyclerCart: RecyclerView? = null
    //Declare Adapter
    private var mAdapter: DetailHistoryAdapter? = null
    private val pDialog: SweetAlertDialog? = null
    //Declare Object Users
    var dataTransaksi: ResponseDetailTransaksi? = null
    var listDetailTransaksi: MutableList<DataDetailTransaksi> = ArrayList()
    var transaksi: DataTransaksi? = null
    override fun onCreate(SavedInstance: Bundle?) {
        super.onCreate(SavedInstance)
        setContentView(R.layout.activity_detail_list_history)
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
        supportActionBar!!.title = transaksi!!.kodetransaksi
    }

    override fun initUI() {
        recyclerCart = findViewById<View>(R.id.rCartList) as RecyclerView
    }

    override fun initValue() {
        prepareCart()
        getTransaksi()
        mAdapter!!.notifyDataSetChanged()
        //        setCheckout();
    }

    override fun initEvent() {}
    private fun prepareCart() {
        mAdapter = DetailHistoryAdapter(mContext, listDetailTransaksi)
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
        val users = client.api.dataDetailTransaksi(transaksi!!.kodetransaksi)
        users.enqueue(object : Callback<ResponseDetailTransaksi?> {
            override fun onResponse(call: Call<ResponseDetailTransaksi?>, response: Response<ResponseDetailTransaksi?>) {
                dataTransaksi = response.body()
                if (response.isSuccessful) {
                    if (dataTransaksi!!.status) {
                        listDetailTransaksi.clear()
                        listDetailTransaksi.addAll(dataTransaksi!!.data)
                        mAdapter!!.notifyDataSetChanged()
                    } else {
                        listDetailTransaksi.clear()
                        mAdapter!!.notifyDataSetChanged()
                        Toast.makeText(mContext, "Tidak Ada Data Ditemukan", Toast.LENGTH_LONG).show()
                    }
                } else {
                    listDetailTransaksi.clear()
                    mAdapter!!.notifyDataSetChanged()
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
        }
    }
}