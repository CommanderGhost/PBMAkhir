package com.rental_apps.android.rental_apps.user

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
import com.pixplicity.easyprefs.library.Prefs
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.SPreferenced.SPref
import com.rental_apps.android.rental_apps.adapter.HistoryAdapter
import com.rental_apps.android.rental_apps.api.client
import com.rental_apps.android.rental_apps.model.model_history.DataHistory
import com.rental_apps.android.rental_apps.model.model_history.ResponseHistory
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by Ujang Wahyu on 15/01/2018.
 */
class UserHistoryTransaksi : Fragment(), InitComponent {
    //Declare Component View
    private val mTxtTitle: TextView? = null
    private var rootView: View? = null
    private var recyclerHistory: RecyclerView? = null
    //Declate Activity Context
    var mContext: Context? = null
    //Declare Object History
    var dataHistory: ResponseHistory? = null
    var listHistory: MutableList<DataHistory> = ArrayList()
    //Declare Adapter
    private var mAdapter: HistoryAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = activity
        // TODO Auto-generated method stub
        rootView = inflater.inflate(R.layout.list_history, container, false)
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

    override fun onResume() { // TODO Auto-generated method stub
        super.onResume()
        history
    }

    private fun setItem(menu: Menu) {
        val menuAdd = menu.findItem(R.id.add)
        menuAdd.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                history
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
        recyclerHistory = rootView!!.findViewById<View>(R.id.rTransaksiList) as RecyclerView
    }

    override fun initValue() {
        prepareTransaksi()
        history
    }

    override fun initEvent() {}//                Toasty.error(mContext,t.getMessage(),Toast.LENGTH_LONG).show();
    //register = client.getApi().userUpdate(""+Prefs.getInt(SPref.getIdUser(),0),name.getText().toString(),
//        Log.d("test","hekki");
    val history: Unit
        get() {
            val history = client.api.dataHistory(Prefs.getInt(SPref.idUser, 0))
            //register = client.getApi().userUpdate(""+Prefs.getInt(SPref.getIdUser(),0),name.getText().toString(),
//        Log.d("test","hekki");
            history.enqueue(object : Callback<ResponseHistory?> {
                override fun onResponse(call: Call<ResponseHistory?>, response: Response<ResponseHistory?>) {
                    if (response.isSuccessful) {
                        dataHistory = response.body()
                        if (dataHistory!!.status) {
                            listHistory.clear()
                            listHistory.addAll(dataHistory!!.data)
                            mAdapter!!.notifyDataSetChanged()
                        } else {
                            listHistory.clear()
                            mAdapter!!.notifyDataSetChanged()
                            Toasty.error(mContext!!, "Belum Ada Transaksi", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        listHistory.clear()
                        mAdapter!!.notifyDataSetChanged()
                        Toasty.error(mContext!!, "Belum Ada Transaksi", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseHistory?>, t: Throwable) {
                    Toasty.error(mContext!!, "Belum Ada Transaksi", Toast.LENGTH_LONG).show()
                    //                Toasty.error(mContext,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            })
        }

    private fun prepareTransaksi() {
        mAdapter = HistoryAdapter(listHistory)
        recyclerHistory!!.setHasFixedSize(true)
        recyclerHistory!!.layoutManager = LinearLayoutManager(activity)
        recyclerHistory!!.itemAnimator = DefaultItemAnimator()
        recyclerHistory!!.adapter = mAdapter
        recyclerHistory!!.itemAnimator = SlideLeftAlphaAnimator()
    }

    companion object {
        //Declate Toolbar Tittle
        private const val TEXT_FRAGMENT = "RENTCAR"

        fun newInstance(text: String?): UserHistoryTransaksi {
            val mFragment = UserHistoryTransaksi()
            val mBundle = Bundle()
            mBundle.putString(TEXT_FRAGMENT, text)
            mFragment.arguments = mBundle
            return mFragment
        }
    }
}