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
import com.rental_apps.android.rental_apps.adapter.CarsAdapter
import com.rental_apps.android.rental_apps.api.client.api
import com.rental_apps.android.rental_apps.model.model_mobil.DataCars
import com.rental_apps.android.rental_apps.model.model_mobil.ResponseCars
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import com.rental_apps.android.rental_apps.utils.move.moveActivity
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class AdminListCart : Fragment(), InitComponent {
    //Declare Component View
    private val mTxtTitle: TextView? = null
    private var rootView: View? = null
    private var recyclerCars: RecyclerView? = null
    //Declate Activity Context
    var mContext: Context? = null
    //Declare Object Cars
    var dataCars: ResponseCars? = null
    var listCars: MutableList<DataCars> = ArrayList()
    //Declare Adapter
    private var mAdapter: CarsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = activity
        // TODO Auto-generated method stub
        rootView = inflater.inflate(R.layout.fragment_admin_cars, container, false)
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
        //        MenuItem menuItem = menu.findItem(R.id.ic_group);
//        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();
//
//        DrawableCounter badge;
//
//        // Reuse drawable if possible
//        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
//        if (reuse != null && reuse instanceof DrawableCounter) {
//            badge = (DrawableCounter) reuse;
//        } else {
//            badge = new DrawableCounter(mContext);
//        }
//
//        badge.setCount("10");
//        icon.mutate();
//        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                cars
                true
            }
            R.id.add -> {
                moveActivity(mContext!!, ActivityCreateMobil::class.java)
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
        recyclerCars = rootView!!.findViewById<View>(R.id.rCarList) as RecyclerView
    }

    override fun initValue() {
        prepareCars()
        cars
    }

    override fun initEvent() {}
    //                Toasty.error(mContext, "Tidak Di Temukan Data", Toast.LENGTH_LONG).show();
    val cars: Unit
        get() {
            val cars = api.dataMobil()
            cars!!.enqueue(object : Callback<ResponseCars?> {
                override fun onResponse(call: Call<ResponseCars?>, response: Response<ResponseCars?>) {
                    if (response.isSuccessful) {
                        dataCars = response.body()
                        if (dataCars!!.status!!) {
                            listCars.clear()
                            listCars.addAll(dataCars!!.data!!)
                            mAdapter!!.notifyDataSetChanged()
                        } else {
                            Toasty.error(mContext!!, "Tidak Di Temukan Data", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toasty.error(mContext!!, "Tidak Di Temukan Data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseCars?>, t: Throwable) { //                Toasty.error(mContext, "Tidak Di Temukan Data", Toast.LENGTH_LONG).show();
                    Toasty.error(mContext!!, t.message!!, Toast.LENGTH_LONG).show()
                }
            })
        }

    private fun prepareCars() {
        mAdapter = CarsAdapter(listCars)
        recyclerCars!!.setHasFixedSize(true)
        recyclerCars!!.layoutManager = LinearLayoutManager(activity)
        recyclerCars!!.itemAnimator = DefaultItemAnimator()
        recyclerCars!!.adapter = mAdapter
        recyclerCars!!.itemAnimator = SlideLeftAlphaAnimator()
        recyclerCars!!.itemAnimator!!.addDuration = 500
        recyclerCars!!.itemAnimator!!.removeDuration = 500
    }

    companion object {
        //Declate Toolbar Tittle
        private const val TEXT_FRAGMENT = "RENTCAR"

        fun newInstance(text: String?): AdminListCart {
            val mFragment = AdminListCart()
            val mBundle = Bundle()
            mBundle.putString(TEXT_FRAGMENT, text)
            mFragment.arguments = mBundle
            return mFragment
        }
    }
}