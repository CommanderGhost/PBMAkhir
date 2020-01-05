package com.rental_apps.android.rental_apps

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.pixplicity.easyprefs.library.Prefs
import com.rental_apps.android.rental_apps.SPreferenced.SPref
import com.rental_apps.android.rental_apps.api.client
import com.rental_apps.android.rental_apps.model.model_user.DataUser
import com.rental_apps.android.rental_apps.model.model_user.ResponseLogin
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import com.rental_apps.android.rental_apps.user.SplashActivity
import com.rental_apps.android.rental_apps.user.UserMain
import com.rental_apps.android.rental_apps.utils.move
import com.rental_apps.android.rental_apps.utils.validate
import customfonts.MyEditText
import customfonts.MyTextView
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityLogin : AppCompatActivity(), InitComponent, View.OnClickListener {
    //declare componenr
    private var et_username: MyEditText? = null
    private var et_password: MyEditText? = null
    private var btn_login: MyTextView? = null
    private var txt_register: MyTextView? = null
    private var logofont: TextView? = null
    private val coordinatorlayout: CoordinatorLayout? = null
    //declare context
    private var mContext: Context? = null
    //declate variable
    private var userData: DataUser? = null
    //declare sweet alert
//   private SweetAlertDialog pDialog;
    private var pDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mContext = this
        startInit()
    }

    override fun startInit() {
        if (Prefs.getInt(SPref.groupUser, 0) == 1) {
            mContext?.let { move.moveActivity(it, UserMain::class.java) }
            finish()
        }
        if (Prefs.getInt(SPref.groupUser, 0) == 2) {
            mContext?.let { move.moveActivity(it, UserMain::class.java) }
            finish()
        }
        initToolbar()
        initUI()
        initValue()
        initEvent()
    }

    override fun initToolbar() {
        supportActionBar!!.hide()
    }

    override fun initUI() {
        et_username = findViewById<View>(R.id.et_username) as MyEditText
        et_password = findViewById<View>(R.id.et_password) as MyEditText
        btn_login = findViewById<View>(R.id.btn_login) as MyTextView
        txt_register = findViewById<View>(R.id.txt_register) as MyTextView
        logofont = findViewById<View>(R.id.logofont) as TextView
        val custom_fonts = Typeface.createFromAsset(assets, "fonts/ArgonPERSONAL-Regular.otf")
        logofont!!.typeface = custom_fonts
    }

    override fun initValue() {}
    override fun initEvent() {
        btn_login!!.setOnClickListener(this)
        txt_register!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_login -> if (validate_login()) login()
            R.id.txt_register -> mContext?.let { move.moveActivity(it, ActivityRegister::class.java) }
        }
    }

    fun validate_login(): Boolean {
        return if (!et_username?.let { validate.cek(it) }!! && !et_password?.let { validate.cek(it) }!!) true else false
    }

    fun login() {
        pDialog = ProgressDialog(this)
        //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog!!.setMessage("Loading")
        pDialog!!.setCancelable(false)
        // pDialog.setIndeterminate(false);
        pDialog!!.show()
        val user = client.api.auth(et_username!!.text.toString(), et_password!!.text.toString())
        user.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                pDialog!!.hide()
                if (response.isSuccessful) {
                    if (response.body().status!!) {
                        userData = response.body().data
                        Toasty.success(mContext!!, "login berhasil", Toast.LENGTH_LONG).show()
                        Log.d("data user", userData.toString())
                        setPreference(userData)
                        if (userData?.group_user == 1) move.moveActivity(mContext!!, SplashActivity::class.java) else move.moveActivity(mContext!!, SplashActivity::class.java)
                        finish()
                    } else {
                        Toasty.error(mContext!!, "Username dan password salah", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toasty.error(mContext!!, "Username dan password salah", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                pDialog!!.hide()
                //                new ProgressDialog(mContext)
//                        .setTitle("Oops...")
//                        .d("Koneksi bermasalah!")
//                        .show();
//                pDialog = new ProgressDialog(ActivityLogin.this);
//                //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                pDialog.setMessage("Tidak ada koneksi");
//                pDialog.show();
                Toasty.success(mContext!!, "Koneksi Tidak ada", Toast.LENGTH_LONG).show()
                if (pDialog!!.isShowing) pDialog!!.dismiss()
            }
        })
    }

    private fun setPreference(du: DataUser?) {
        du!!.id_user?.let { Prefs.putInt(SPref.idUser, it) }
        Prefs.putString(SPref.uSERNAME, du.username)
        Prefs.putString(SPref.nAME, du.name)
        Prefs.putString(SPref.eMAIL, du.email)
        Prefs.putString(SPref.noTelp, du.no_telp)
        Prefs.putString(SPref.jenisKelamin, du.jenis_kelamin.toString())
        Prefs.putString(SPref.pHOTO, du.photo)
        Prefs.putString(SPref.lastUpdate, du.last_update.toString())
        Prefs.putString(SPref.aLAMAT, du.alamat)
        du.group_user?.let { Prefs.putInt(SPref.groupUser, it) }
        Prefs.putString(SPref.PASSWORD, du.password.toString())
    }
}

private fun <T> Call<T>?.enqueue(callback: Callback<ResponseLogin>) {

}
