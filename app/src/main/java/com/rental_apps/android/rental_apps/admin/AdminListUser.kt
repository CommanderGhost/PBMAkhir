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
import com.rental_apps.android.rental_apps.adapter.UsersAdapter
import com.rental_apps.android.rental_apps.api.client.api
import com.rental_apps.android.rental_apps.model.model_user.DataUser
import com.rental_apps.android.rental_apps.model.model_user.ResponseUser
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
class AdminListUser : Fragment(), InitComponent {
    //Declare Component View
    private val mTxtTitle: TextView? = null
    private var rootView: View? = null
    private var recyclerUsers: RecyclerView? = null
    //Declate Activity Context
    var mContext: Context? = null
    //Declare Object Users
    var dataUsers: ResponseUser? = null
    var listUsers: MutableList<DataUser> = ArrayList()
    //Declare Adapter
    private var mAdapter: UsersAdapter? = null

    fun newInstance(text: String?, gUser: String?): AdminListUser {
        val mFragment = AdminListUser()
        val mBundle = Bundle()
        mBundle.putString(TEXT_FRAGMENT, text)
        mBundle.putString(GroupUser, gUser)
        mFragment.arguments = mBundle
        return mFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = activity
        // TODO Auto-generated method stub
        rootView = inflater.inflate(R.layout.fragment_admin_user, container, false)
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
        if (arguments!!.getString(GroupUser).toInt() == 2) {
            val menuAdd = menu.findItem(R.id.add)
            menuAdd.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                users
                true
            }
            R.id.add -> {
                moveActivity(mContext!!, ActivityCreateAdmin::class.java)
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
        recyclerUsers = rootView!!.findViewById<View>(R.id.rUserList) as RecyclerView
    }

    override fun initValue() {
        prepareUsers()
        users
    }

    override fun initEvent() {}
    val users: Unit
        get() {
            val users = api.dataUser(arguments!!.getString(GroupUser).toInt(), 0)
            users!!.enqueue(object : Callback<ResponseUser?> {
                override fun onResponse(call: Call<ResponseUser?>, response: Response<ResponseUser?>) {
                    dataUsers = response.body()
                    if (response.isSuccessful) {
                        if (dataUsers!!.status!!) {
                            listUsers.clear()
                            listUsers.addAll(dataUsers!!.data!!)
                            mAdapter!!.notifyDataSetChanged()
                        } else {
                            Toasty.error(mContext!!, "Tidak Ada Data Ditemukan", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toasty.error(mContext!!, "Tidak Ada Data Ditemukan", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseUser?>, t: Throwable) {
                    Toasty.error(mContext!!, t.message!!, Toast.LENGTH_LONG).show()
                }
            })
        }

    private fun prepareUsers() {
        mAdapter = UsersAdapter(listUsers)
        recyclerUsers!!.setHasFixedSize(true)
        recyclerUsers!!.layoutManager = LinearLayoutManager(activity)
        recyclerUsers!!.itemAnimator = DefaultItemAnimator()
        recyclerUsers!!.adapter = mAdapter
        recyclerUsers!!.itemAnimator = SlideLeftAlphaAnimator()
    }

    companion object {
        //Declate Toolbar Tittle
        private const val TEXT_FRAGMENT = "RENTCAR"
        private const val GroupUser = "GroupUser"
    }
}