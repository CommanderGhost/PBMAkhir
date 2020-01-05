package com.rental_apps.android.rental_apps.admin

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import cn.pedant.SweetAlert.SweetAlertDialog
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.api.client.api
import com.rental_apps.android.rental_apps.model.model_user.DataUser
import com.rental_apps.android.rental_apps.model.model_user.ResponseRegister
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import com.rental_apps.android.rental_apps.utils.validate.cek
import com.rental_apps.android.rental_apps.utils.validate.cekPassword
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class ActivityCreateAdmin : AppCompatActivity(), InitComponent, View.OnClickListener {
    //declare component
    private var etNama: EditText? = null
    private var etNik: EditText? = null
    private var etUsername: EditText? = null
    private var etNumber: EditText? = null
    private var etAlamat: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var etConfirmPassword: EditText? = null
    private var JK: Char? = null
    private var rbl: RadioButton? = null
    private var rbp: RadioButton? = null
    private var btnRegister: Button? = null
    private val coordinatorLayout: CoordinatorLayout? = null
    //declare context
    private var mContext: Context? = null
    //declare variable
    private val userData: DataUser? = null
    //declare sweet alert
    private var pDialog: SweetAlertDialog? = null

    override fun onCreate(SavedInstance: Bundle?) {
        super.onCreate(SavedInstance)
        setContentView(R.layout.activity_add_admin)
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
        supportActionBar!!.title = "Register Admin"
    }

    override fun initUI() {
        etNama = findViewById<View>(R.id.et_nama) as EditText
        etNik = findViewById<View>(R.id.et_nik) as EditText
        etEmail = findViewById<View>(R.id.et_email) as EditText
        etNumber = findViewById<View>(R.id.et_no_telp) as EditText
        etAlamat = findViewById<View>(R.id.et_alamat) as EditText
        etUsername = findViewById<View>(R.id.et_username) as EditText
        etPassword = findViewById<View>(R.id.et_password) as EditText
        etConfirmPassword = findViewById<View>(R.id.et_confirm_password) as EditText
        rbl = findViewById<View>(R.id.jkl) as RadioButton
        rbp = findViewById<View>(R.id.jkp) as RadioButton
        btnRegister = findViewById<View>(R.id.btn_register) as Button
    }

    override fun initValue() {
        btnRegister!!.setOnClickListener(this)
        rbl!!.setOnClickListener(this)
        rbp!!.setOnClickListener(this)
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

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_register -> if (validasi()) register()
            R.id.jkl -> {
                JK = 'L'
                rbp!!.isChecked = false
            }
            R.id.jkp -> {
                JK = 'P'
                rbl!!.isChecked = false
            }
        }
    }

    private fun register() {
        pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pDialog!!.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog!!.titleText = "Loading"
        pDialog!!.setCancelable(false)
        pDialog!!.show()
        val register: Call<ResponseRegister?>?
        register = api.userRegister(etNama!!.text.toString(),
                etNik!!.text.toString(),
                etUsername!!.text.toString(),
                etEmail!!.text.toString(),
                etNumber!!.text.toString(),
                JK,
                etAlamat!!.text.toString(),
                etPassword!!.text.toString(),
                1, 1)
        register!!.enqueue(object : Callback<ResponseRegister> {
            override fun onResponse(call: Call<ResponseRegister>, response: Response<ResponseRegister>) {
                pDialog!!.hide()
                if (response.isSuccessful) {
                    if (response.body().status!!) {
                        SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Info")
                                .setContentText("Akun Berhasil Di Buat!")
                                .show()
                    } else {
                        SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Info")
                                .setContentText("Akun Gagal Di Buat!")
                                .show()
                    }
                } else {
                    SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Info")
                            .setContentText("Akun Gagal Di Buat!")
                            .show()
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                pDialog!!.hide()
                SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Koneksi bermasalah!")
                        .show()
            }
        })
    }

    private fun validasi(): Boolean {
        return if (!cek(etNama!!)
                && !cek(etNik!!)
                && !cek(etUsername!!)
                && !cek(etEmail!!)
                && !cek(etNumber!!)
                && !cek(etAlamat!!)
                && !cek(etPassword!!)
                && !cek(etConfirmPassword!!)) {
            if (cekPassword(etConfirmPassword!!, etPassword!!.text.toString(), etConfirmPassword!!.text.toString())) {
                false
            } else {
                true
            }
        } else {
            false
        }
    }
}

private fun <T> Call<T>.enqueue(callback: Callback<ResponseRegister>) {

}
