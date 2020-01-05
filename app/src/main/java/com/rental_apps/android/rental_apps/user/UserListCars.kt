package com.rental_apps.android.rental_apps.user

import android.content.Context
import android.graphics.drawable.LayerDrawable
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
import com.rental_apps.android.rental_apps.SPreferenced.SPref
import com.rental_apps.android.rental_apps.adapter.CarsUserAdapter
import com.rental_apps.android.rental_apps.adapter.Carts
import com.rental_apps.android.rental_apps.admin.ActivityCreateMobil
import com.rental_apps.android.rental_apps.api.client
import com.rental_apps.android.rental_apps.helper.DrawableCounter
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
class UserListCars : Fragment(), InitComponent {
    //Declare Component View
    private val mTxtTitle: TextView? = null
    private var rootView: View? = null
    private var recyclerCars: RecyclerView? = null
    //Declate Activity Context
    var mContext: Context? = null
    //Declare Object Cars
    var dataCars: ResponseCars? = null
    var listCars: MutableList<DataCars> = ArrayList()
    var mnn: Menu? = null
    //Declare Adapter
    private var mAdapter: CarsUserAdapter? = null

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
        inflater.inflate(R.menu.menu_user_icon, menu)
        setCart(menu)
    }

    fun setCart(menu: Menu) {
        val menuItem = menu.findItem(R.id.cart)
        val icon = menuItem.icon as LayerDrawable
        val badge: DrawableCounter
        // Reuse drawable if possible
        val reuse = icon.findDrawableByLayerId(R.id.ic_group_count)
        badge = if (reuse != null && reuse is DrawableCounter) {
            reuse
        } else {
            DrawableCounter(mContext)
        }
        badge.setCount("" + Carts.getSize(SPref.CARTS))
        icon.mutate()
        icon.setDrawableByLayerId(R.id.ic_group_count, badge)
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
            R.id.cart -> {
                moveActivity(mContext!!, ActivityListTransaksi::class.java)
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
    val cars: Unit
        get() {
            val cars = client.api.dataMobil()
            cars.enqueue(object : Callback<ResponseCars?> {
                override fun onResponse(call: Call<ResponseCars?>, response: Response<ResponseCars?>) {
                    if (response.isSuccessful) {
                        dataCars = response.body()
                        if (dataCars!!.status) {
                            listCars.clear()
                            listCars.addAll(dataCars!!.data)
                            mAdapter!!.notifyDataSetChanged()
                        } else {
                            Toasty.error(mContext!!, "gagal", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toasty.error(mContext!!, "gagal", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseCars?>, t: Throwable) {
                    Toasty.error(mContext!!, t.message!!, Toast.LENGTH_LONG).show()
                }
            })
        }

    private fun prepareCars() {
        mAdapter = CarsUserAdapter(listCars)
        recyclerCars!!.setHasFixedSize(true)
        recyclerCars!!.layoutManager = LinearLayoutManager(activity)
        recyclerCars!!.itemAnimator = DefaultItemAnimator()
        recyclerCars!!.adapter = mAdapter
        recyclerCars!!.itemAnimator = SlideLeftAlphaAnimator()
        recyclerCars!!.itemAnimator!!.addDuration = 500
        recyclerCars!!.itemAnimator!!.removeDuration = 500
    }

    override fun onResume() {
        super.onResume()
        activity!!.invalidateOptionsMenu()
    }

    companion object {
        //Declate Toolbar Tittle
        private const val TEXT_FRAGMENT = "RENTCAR"

        fun newInstance(text: String?): UserListCars {
            val mFragment = UserListCars()
            val mBundle = Bundle()
            mBundle.putString(TEXT_FRAGMENT, text)
            mFragment.arguments = mBundle
            return mFragment
        }
    }
}