package com.rental_apps.android.rental_apps.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.google.gson.Gson
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.admin.ActivityDetailUsers
import com.rental_apps.android.rental_apps.api.client
import com.rental_apps.android.rental_apps.model.model_user.DataUser
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class UsersAdapter(private val usersList: List<DataUser>) : RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {
    private var mContext: Context? = null
    private var lastPosition = 0
    private var mView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val view: View
        private val name: TextView
        private val email: TextView
        private val userImage: CircleImageView
        fun bindItem(users: DataUser) {
            name.text = users.name
            email.text = users.email
            if (!users.photo!!.isEmpty()) Picasso.with(mContext)
                    .load(client.baseUrlImage.toString() + users.photo)
                    .resize(250, 250)
                    .centerCrop()
                    .into(userImage)
        }

        init {
            mView = view
            name = view.findViewById<View>(R.id.name) as TextView
            email = view.findViewById<View>(R.id.email) as TextView
            userImage = view.findViewById<View>(R.id.userImage) as CircleImageView
            this.view = view
            mContext = view.context
            view.setOnClickListener {
                val gson = Gson()
                val user = gson.toJson(usersList[adapterPosition])
                val i = Intent(mContext, ActivityDetailUsers::class.java)
                i.putExtra("user", user)
                mContext.startActivity(i)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.design_user, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(usersList[position])
        setAnimation(mView, position)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    private fun setAnimation(viewToAnimate: View?, position: Int) {
        if (position > lastPosition) {
            lastPosition = position
            val animation = AnimationUtils.loadAnimation(mView!!.context, R.anim.slide_left_to_right)
            viewToAnimate!!.startAnimation(animation)
        }
    }

}