package com.rental_apps.android.rental_apps.admin

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.google.gson.Gson
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.api.client
import com.rental_apps.android.rental_apps.model.model_user.DataUser
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import com.squareup.picasso.Picasso
import customfonts.MyTextView
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class ActivityDetailUsers : AppCompatActivity(), InitComponent {
    private var name: MyTextView? = null
    private var email: MyTextView? = null
    private var noTelp: MyTextView? = null
    private var address: MyTextView? = null
    private var jenis_kelamin: MyTextView? = null
    private var status: MyTextView? = null
    private var userPhoto: CircleImageView? = null
    var mContext: Context? = null
    var toolbar: Toolbar? = null
    var user: DataUser? = null
    override fun onCreate(SavedInstance: Bundle?) {
        super.onCreate(SavedInstance)
        setContentView(R.layout.activity_detail_user)
        val gson = Gson()
        user = gson.fromJson(intent.getStringExtra("user"), DataUser::class.java)
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
        toolbar = findViewById<View>(R.id.maintoolbar) as Toolbar
        setSupportActionBar(toolbar)
        val upArrow = resources.getDrawable(R.drawable.ic_action_back)
        upArrow.setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = ""
    }

    override fun initUI() {
        name = findViewById<View>(R.id.name) as MyTextView
        email = findViewById<View>(R.id.email) as MyTextView
        noTelp = findViewById<View>(R.id.notelp) as MyTextView
        address = findViewById<View>(R.id.address) as MyTextView
        jenis_kelamin = findViewById<View>(R.id.jenis_kelamin) as MyTextView
        status = findViewById<View>(R.id.status) as MyTextView
        userPhoto = findViewById<View>(R.id.userPhoto) as CircleImageView
    }

    override fun initValue() {
        name!!.text = user!!.name
        email!!.text = user!!.email
        noTelp!!.text = user!!.no_telp
        address!!.text = user!!.alamat
        if (user!!.jenis_kelamin == 'L') {
            jenis_kelamin!!.text = "Laki-laki"
        } else {
            jenis_kelamin!!.text = "Perempuan"
        }
        if (user!!.activated == 1) status!!.text = "Aktif" else status!!.text = "Tidak Aktif"
        if (!user!!.photo!!.isEmpty()) Picasso.with(mContext).load(client.baseUrlImage.toString() + user!!.photo).into(userPhoto)
    }

    override fun initEvent() {}
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