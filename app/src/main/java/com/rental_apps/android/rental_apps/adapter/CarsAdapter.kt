package com.rental_apps.android.rental_apps.adapter

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.admin.ActivityDetailCars
import com.rental_apps.android.rental_apps.api.client
import com.rental_apps.android.rental_apps.helper.DrawableColor.setColor
import com.rental_apps.android.rental_apps.model.model_mobil.DataCars
import com.squareup.picasso.Picasso

/**
 * Created by Asus on 04/01/2018.
 */
class CarsAdapter(private val carsList: List<DataCars>) : RecyclerView.Adapter<CarsAdapter.MyViewHolder>() {
    private var lastPosition = -1
    private var mView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val carName: TextView
        private val year: TextView
        private val capacity: TextView
        private val color: TextView
        private val bensin: TextView
        private val price: TextView
        private val status: TextView
        private val ic_year: ImageView
        private val ic_color: ImageView
        private val ic_capacity: ImageView
        private val ic_bensin: ImageView
        private val imgCar: ImageView
        private val view: View
        fun bindItem(cars: DataCars) {
            carName.text = cars.nAMAMOBIL.toString()
            year.text = cars.tAHUNMOBIL.toString()
            capacity.text = cars.kAPASITASMOBIL.toString()
            color.text = cars.wARNAMOBIL.toString()
            bensin.text = cars.bENSINMOBIL.toString()
            price.text = "Rp. " + String.format("%,.2f", cars.hARGAMOBIL.toString().toDouble())
            if (cars.sTATUSSEWA.toString().toInt() == 1) {
                status.text = "Disewa"
            } else {
                status.text = "Tersedia"
            }
            if (cars.iMAGE!!.size > 0) Picasso.with(view.context).load(client.baseImg.toString() + "mobil/" + cars.iMAGE!![0]).into(imgCar)
            val yearIcon = ContextCompat.getDrawable(view.context, R.drawable.ic_action_go_to_today)
            val capacityIcon = ContextCompat.getDrawable(view.context, R.drawable.ic_action_cc_bcc)
            val colorIcon = ContextCompat.getDrawable(view.context, R.drawable.ic_action_picture)
            val fuelIcon = ContextCompat.getDrawable(view.context, R.drawable.ic_action_fuel)
            ic_year.setImageDrawable(setColor(yearIcon, R.color.nliveo_orange_colorPrimaryDark))
            ic_capacity.setImageDrawable(setColor(capacityIcon, R.color.nliveo_orange_colorPrimaryDark))
            ic_color.setImageDrawable(setColor(colorIcon, R.color.nliveo_orange_colorPrimaryDark))
            ic_bensin.setImageDrawable(setColor(fuelIcon, R.color.nliveo_orange_colorPrimaryDark))
        }

        init {
            mView = view
            carName = view.findViewById<View>(R.id.carName) as TextView
            year = view.findViewById<View>(R.id.year) as TextView
            capacity = view.findViewById<View>(R.id.capacity) as TextView
            color = view.findViewById<View>(R.id.color) as TextView
            bensin = view.findViewById<View>(R.id.bensin) as TextView
            price = view.findViewById<View>(R.id.price) as TextView
            status = view.findViewById<View>(R.id.status) as TextView
            ic_year = view.findViewById<View>(R.id.ic_year) as ImageView
            ic_color = view.findViewById<View>(R.id.ic_color) as ImageView
            ic_capacity = view.findViewById<View>(R.id.ic_capacity) as ImageView
            ic_bensin = view.findViewById<View>(R.id.ic_bensin) as ImageView
            imgCar = view.findViewById<View>(R.id.imgCar) as ImageView
            view.setOnClickListener { view ->
                val gson = Gson()
                val car = gson.toJson(carsList[adapterPosition])
                val i = Intent(view.context, ActivityDetailCars::class.java)
                i.putExtra("car", car)
                view.context.startActivity(i)
            }
            this.view = view
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.design_cars, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(carsList[position])
        setAnimation(mView, position)
    }

    override fun getItemCount(): Int {
        return carsList.size
    }

    private fun setAnimation(viewToAnimate: View?, position: Int) {
        if (position > lastPosition) {
            lastPosition = position
            val animation = AnimationUtils.loadAnimation(mView!!.context, R.anim.slide_left_to_right)
            viewToAnimate!!.startAnimation(animation)
        }
    }

}