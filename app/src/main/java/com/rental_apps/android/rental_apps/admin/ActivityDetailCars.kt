package com.rental_apps.android.rental_apps.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.admin.ActivityEditMobil
import com.rental_apps.android.rental_apps.api.client
import com.rental_apps.android.rental_apps.helper.DrawableColor.setColor
import com.rental_apps.android.rental_apps.model.model_mobil.DataCars
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import com.squareup.picasso.Picasso
import customfonts.MyTextView

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class ActivityDetailCars : AppCompatActivity(), InitComponent, View.OnClickListener {
    private var mContext: Context? = null
    private var car: DataCars? = null
    private var toolbar: Toolbar? = null
    private var merk: TextView? = null
    private var year: TextView? = null
    private var capacity: TextView? = null
    private var plat: TextView? = null
    private var warna_mobil: TextView? = null
    private var bensin_mobil: TextView? = null
    private var price: TextView? = null
    private var description: MyTextView? = null
    private var nama_mobil: MyTextView? = null
    private var ic_merk: ImageView? = null
    private var ic_year: ImageView? = null
    private var ic_capacity: ImageView? = null
    private var ic_plat: ImageView? = null
    private var ic_warna_mobil: ImageView? = null
    private var ic_bensin_mobil: ImageView? = null
    private var action_mobil: FloatingActionButton? = null
    private var mainbackdrop: ImageView? = null
    override fun onCreate(SavedInstance: Bundle?) {
        super.onCreate(SavedInstance)
        setContentView(R.layout.activity_detail_cars)
        mContext = this
        val gson = Gson()
        car = gson.fromJson(intent.getStringExtra("car"), DataCars::class.java)
        startInit()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.action_mobil -> {
                val gson = Gson()
                val datacar = gson.toJson(car)
                val i = Intent(view.context, ActivityEditMobil::class.java)
                i.putExtra("car", datacar)
                view.context.startActivity(i)
            }
        }
    }

    override fun startInit() {
        initToolbar()
        initUI()
        initValue()
        initEvent()
    }

    override fun initToolbar() {
        toolbar = findViewById<View>(R.id.maintoolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = car!!.nAMAMOBIL
    }

    override fun initUI() {
        merk = findViewById<View>(R.id.merk) as TextView
        year = findViewById<View>(R.id.year) as TextView
        plat = findViewById<View>(R.id.plat) as TextView
        price = findViewById<View>(R.id.price) as TextView
        capacity = findViewById<View>(R.id.capacity) as TextView
        warna_mobil = findViewById<View>(R.id.warna) as TextView
        bensin_mobil = findViewById<View>(R.id.bensin) as TextView
        description = findViewById<View>(R.id.description) as MyTextView
        nama_mobil = findViewById<View>(R.id.nama_mobil) as MyTextView
        ic_merk = findViewById<View>(R.id.ic_merk) as ImageView
        ic_year = findViewById<View>(R.id.ic_year) as ImageView
        ic_plat = findViewById<View>(R.id.ic_plat) as ImageView
        ic_capacity = findViewById<View>(R.id.ic_capacity) as ImageView
        ic_warna_mobil = findViewById<View>(R.id.ic_warna) as ImageView
        ic_bensin_mobil = findViewById<View>(R.id.ic_bensin) as ImageView
        mainbackdrop = findViewById<View>(R.id.mainbackdrop) as ImageView
        action_mobil = findViewById<View>(R.id.action_mobil) as FloatingActionButton
        val yearIcon = ContextCompat.getDrawable(mContext!!, R.drawable.ic_action_go_to_today)
        val capacityIcon = ContextCompat.getDrawable(mContext!!, R.drawable.ic_action_cc_bcc)
        val colorIcon = ContextCompat.getDrawable(mContext!!, R.drawable.ic_action_picture)
        val fuelIcon = ContextCompat.getDrawable(mContext!!, R.drawable.ic_action_fuel)
        val merkIcon = ContextCompat.getDrawable(mContext!!, R.drawable.ic_action_storage)
        val platIcon = ContextCompat.getDrawable(mContext!!, R.drawable.ic_action_screen_locked_to_landscape)
        ic_year!!.setImageDrawable(setColor(yearIcon, R.color.nliveo_orange_colorPrimaryDark))
        ic_capacity!!.setImageDrawable(setColor(capacityIcon, R.color.nliveo_orange_colorPrimaryDark))
        ic_warna_mobil!!.setImageDrawable(setColor(colorIcon, R.color.nliveo_orange_colorPrimaryDark))
        ic_bensin_mobil!!.setImageDrawable(setColor(fuelIcon, R.color.nliveo_orange_colorPrimaryDark))
        ic_merk!!.setImageDrawable(setColor(merkIcon, R.color.nliveo_orange_colorPrimaryDark))
        ic_plat!!.setImageDrawable(setColor(platIcon, R.color.nliveo_orange_colorPrimaryDark))
    }

    override fun initValue() {
        description!!.text = car!!.dESKRIPSIMOBIL
        merk!!.text = car!!.mERKMOBIL
        year!!.text = car!!.tAHUNMOBIL
        capacity!!.text = car!!.kAPASITASMOBIL
        plat!!.text = car!!.pLATNOMOBIL
        warna_mobil!!.text = car!!.wARNAMOBIL
        bensin_mobil!!.text = car!!.bENSINMOBIL
        price!!.text = "Rp. " + String.format("%,.2f", car!!.hARGAMOBIL.toString().toDouble())
        nama_mobil!!.text = car!!.nAMAMOBIL
        if (car!!.iMAGE!!.size > 0) Picasso.with(mContext).load(client.getBaseImg().toString() + "mobil/" + car!!.iMAGE!![0]).into(mainbackdrop)
    }

    override fun initEvent() {
        action_mobil!!.setOnClickListener(this)
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
}