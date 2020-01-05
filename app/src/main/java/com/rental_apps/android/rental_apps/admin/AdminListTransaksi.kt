package com.rental_apps.android.rental_apps.admin

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import android.widget.Toast
import com.mikepenz.itemanimators.SlideLeftAlphaAnimator
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.adapter.TransaksiAdapter
import com.rental_apps.android.rental_apps.api.client.api
import com.rental_apps.android.rental_apps.model.model_transaksi.DataTransaksi
import com.rental_apps.android.rental_apps.model.model_transaksi.ResponseTransaksi
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class AdminListTransaksi : Fragment(), InitComponent {
    //Declare Component View
    private val mTxtTitle: TextView? = null
    private var rootView: View? = null
    private var recyclerTransaksi: RecyclerView? = null
    //Declate Activity Context
    var mContext: Context? = null
    //Declare Object Transaksi
    var dataTransaksi: ResponseTransaksi? = null
    var listTransaksi: MutableList<DataTransaksi> = ArrayList()
    //Declare Adapter
    private var mAdapter: TransaksiAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = activity
        // TODO Auto-generated method stub
        rootView = inflater.inflate(R.layout.fragment_admin_transaksi, container, false)
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
        setItem(menu)
    }

    private fun setItem(menu: Menu) {
        val menuAdd = menu.findItem(R.id.add)
        menuAdd.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                transaksi
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
        recyclerTransaksi = rootView!!.findViewById<View>(R.id.rTransaksiList) as RecyclerView
    }

    override fun initValue() {
        prepareTransaksi()
        transaksi
    }

    override fun initEvent() {}
    //                Toasty.error(mContext,t.getMessage(),Toast.LENGTH_LONG).show();
    val transaksi: Unit
        get() {
            val transaksi = api.dataTransaksi()
            transaksi!!.enqueue(object : Callback<ResponseTransaksi?> {
                override fun onResponse(call: Call<ResponseTransaksi?>, response: Response<ResponseTransaksi?>) {
                    if (response.isSuccessful) {
                        dataTransaksi = response.body()
                        if (dataTransaksi!!.status!!) {
                            listTransaksi.clear()
                            listTransaksi.addAll(dataTransaksi!!.data!!)
                            mAdapter!!.notifyDataSetChanged()
                        } else {
                            Toasty.error(mContext!!, "Tidak Di Temukan Data", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toasty.error(mContext!!, "Tidak Di Temukan Data", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseTransaksi?>, t: Throwable) {
                    Toasty.error(mContext!!, "Tidak Di Temukan Data", Toast.LENGTH_LONG).show()
                    //                Toasty.error(mContext,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            })
        }

    private fun prepareTransaksi() {
        mAdapter = TransaksiAdapter(listTransaksi)
        recyclerTransaksi!!.setHasFixedSize(true)
        recyclerTransaksi!!.layoutManager = LinearLayoutManager(activity)
        recyclerTransaksi!!.itemAnimator = DefaultItemAnimator()
        recyclerTransaksi!!.adapter = mAdapter
        recyclerTransaksi!!.itemAnimator = SlideLeftAlphaAnimator()
    }

    companion object {
        //Declate Toolbar Tittle
        private const val TEXT_FRAGMENT = "RENTCAR"

        fun newInstance(text: String?): AdminListTransaksi {
            val mFragment = AdminListTransaksi()
            val mBundle = Bundle()
            mBundle.putString(TEXT_FRAGMENT, text)
            mFragment.arguments = mBundle
            return mFragment
        }
    }
}