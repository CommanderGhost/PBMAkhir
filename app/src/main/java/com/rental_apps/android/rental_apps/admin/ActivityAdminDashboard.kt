package com.rental_apps.android.rental_apps.admin

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Toast
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.api.client.api
import com.rental_apps.android.rental_apps.model.model_dashboard.ResponseInfoDashboard
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import customfonts.MyTextView
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class ActivityAdminDashboard : Fragment(), InitComponent {
    private var jumlah_admin: MyTextView? = null
    private var jumlah_pesanan: MyTextView? = null
    private var jumlah_user: MyTextView? = null
    private var jumlah_mobil: MyTextView? = null
    private var jumlah_total: MyTextView? = null
    //Declare Component View
    private var rootView: View? = null
    //Declate Activity Context
    var mContext: Context? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = activity
        // TODO Auto-generated method stub
        rootView = inflater.inflate(R.layout.activity_admin_dashboard, container, false)
        startInit()
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) { // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) { // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_icon, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                dashboard
                true
            }
            R.id.add -> {
                Toasty.success(mContext!!, "Tambah", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun startInit() {
        initToolbar()
        initUI()
        initValue()
        initEvent()
    }

    override fun initToolbar() {
        activity!!.title = arguments!!.getString(TEXT_FRAGMENT)
    }

    override fun initUI() {
        rootView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        jumlah_admin = rootView!!.findViewById<View>(R.id.jumlah_admin) as MyTextView
        jumlah_pesanan = rootView!!.findViewById<View>(R.id.jumlah_pesanan) as MyTextView
        jumlah_user = rootView!!.findViewById<View>(R.id.jumlah_user) as MyTextView
        jumlah_mobil = rootView!!.findViewById<View>(R.id.jumlah_mobil) as MyTextView
        jumlah_total = rootView!!.findViewById<View>(R.id.jumlah_penghasilan) as MyTextView
    }

    override fun initValue() {
        setDashboard("0", "0", "0", "0", "0")
        dashboard
    }

    override fun initEvent() {}
    //                        mHelpLiveo.get(1).setCounter(Integer.parseInt(response.body().getData().getTRANSAKSI()));
//                        mHelpLiveo.get(2).setCounter(Integer.parseInt(response.body().getData().getMOBIL()));
//                        mHelpLiveo.get(3).setCounter(Integer.parseInt(response.body().getData().getUSER()));
//                        mHelpLiveo.get(4).setCounter(Integer.parseInt(response.body().getData().getADMIN()));
    val dashboard: Unit
        get() {
            val info: Call<ResponseInfoDashboard?>?
            info = api.dataInfoDashboard()
            info!!.enqueue(object : Callback<ResponseInfoDashboard> {
                override fun onResponse(call: Call<ResponseInfoDashboard>, response: Response<ResponseInfoDashboard>) {
                    if (response.isSuccessful) {
                        if (response.body().status!!) {
                            setDashboard(response.body().data!!.gettOTAL(), response.body().data!!.aDMIN, response.body().data!!.uSER, response.body().data!!.tRANSAKSI, response.body().data!!.mOBIL)
                            //                        mHelpLiveo.get(1).setCounter(Integer.parseInt(response.body().getData().getTRANSAKSI()));
//                        mHelpLiveo.get(2).setCounter(Integer.parseInt(response.body().getData().getMOBIL()));
//                        mHelpLiveo.get(3).setCounter(Integer.parseInt(response.body().getData().getUSER()));
//                        mHelpLiveo.get(4).setCounter(Integer.parseInt(response.body().getData().getADMIN()));
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseInfoDashboard>, t: Throwable) {}
            })
        }

    private fun setDashboard(total: String?, admin: String?, user: String?, pesanan: String?, mobil: String?) {
        jumlah_admin!!.text = admin
        jumlah_user!!.text = user
        jumlah_pesanan!!.text = pesanan
        jumlah_mobil!!.text = mobil
        if (total != null) {
            jumlah_total!!.text = "Rp. " + String.format("%,.2f", total.toDouble())
        }
    }

    companion object {
        //Declate Toolbar Tittle
        private const val TEXT_FRAGMENT = "RENTCAR"

        fun newInstance(text: String?): ActivityAdminDashboard {
            val mFragment = ActivityAdminDashboard()
            val mBundle = Bundle()
            mBundle.putString(TEXT_FRAGMENT, text)
            mFragment.arguments = mBundle
            return mFragment
        }
    }
}

private fun <T> Call<T>.enqueue(callback: Callback<ResponseInfoDashboard>) {

}
