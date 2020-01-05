package com.rental_apps.android.rental_apps.user

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import br.liveo.interfaces.OnItemClickListener
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo
import br.liveo.model.HelpLiveo
import br.liveo.navigationliveo.NavigationLiveo
import com.pixplicity.easyprefs.library.Prefs
import com.rental_apps.android.rental_apps.ActivityLogin
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.SPreferenced.SPref
import com.rental_apps.android.rental_apps.admin.AdminEditProfile
import com.rental_apps.android.rental_apps.api.client
import com.rental_apps.android.rental_apps.utils.move.moveActivity
import com.squareup.picasso.Picasso

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class UserMain : NavigationLiveo(), OnItemClickListener {
    var mContext: Context? = null
    private var mHelpLiveo: HelpLiveo? = null
    //    AdminListUser adminListUser;
    @SuppressLint("ResourceAsColor")
    override fun onInt(savedInstanceState: Bundle) {
        mContext = this
        // User Information
//        adminListUser=new AdminListUser();
        userName.text = Prefs.getString(SPref.nAME, "")
        userName.setTextColor(R.color.nliveo_black)
        userEmail.text = Prefs.getString(SPref.eMAIL, "")
        userEmail.setTextColor(R.color.nliveo_black)
        userBackground.setImageResource(R.drawable.drawer_bg)
        Picasso.with(mContext)
                .load(client.baseUrlImage + Prefs.getString(SPref.pHOTO, ""))
                .resize(250, 250)
                .centerCrop()
                .into(userPhoto)
        mHelpLiveo = HelpLiveo()
        mHelpLiveo!!.add("History Transaksi", R.drawable.ic_action_dock)
        mHelpLiveo!!.add(getString(R.string.mobil), R.drawable.ic_nav_transport)
        with(this).startingPosition(0)
                .addAllHelpItem(mHelpLiveo!!.help)
                .selectorCheck(R.color.nliveo_purple_colorPrimaryDark)
                .colorItemDefault(R.color.white)
                .colorItemSelected(R.color.white)
                .backgroundList(R.color.nliveo_black_light)
                .colorItemIcon(R.color.colorAccent)
                .footerItem(getString(R.string.setting), R.drawable.ic_action_settings)
                .footerSecondItem(R.string.logout, R.drawable.ic_action_screen_locked_to_portrait)
                .footerNameColor(R.color.white)
                .footerIconColor(R.color.colorAccent)
                .footerSecondNameColor(R.color.white)
                .footerSecondIconColor(R.color.colorAccent)
                .setOnClickUser(onClickPhoto)
                .setOnPrepareOptionsMenu(onPrepare)
                .setOnClickFooter(onClickProfile)
                .setOnClickFooterSecond(onClickFooter)
                .build()
        val position = this.currentPosition
        setElevationToolBar(if (position != 2) 15F else 0.toFloat())
    }

    override fun onItemClick(position: Int) {
        var mFragment: Fragment? = null
        val mFragmentManager = supportFragmentManager
        when (position) {
            0 -> mFragment = UserHistoryTransaksi.newInstance(mHelpLiveo!![position].name)
            1 -> mFragment = UserListCars.newInstance(mHelpLiveo!![position].name)
            2 -> {
            }
            3 -> {
            }
            4 -> {
            }
        }
        if (mFragment != null) {
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit()
        }
        setElevationToolBar(if (position != 2) 15F else 0.toFloat())
    }

    private val onPrepare = OnPrepareOptionsMenuLiveo { menu, position, visible -> }
    private val onClickPhoto = View.OnClickListener { closeDrawer() }
    private val onClickFooter = View.OnClickListener {
        Prefs.clear()
        moveActivity(mContext!!, ActivityLogin::class.java)
    }
    private val onClickProfile = View.OnClickListener {
        closeDrawer()
        moveActivity(mContext!!, AdminEditProfile::class.java)
    }
}