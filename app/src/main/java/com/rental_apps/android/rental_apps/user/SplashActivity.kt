package com.rental_apps.android.rental_apps.user

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.rental_apps.android.rental_apps.R

class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(4000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    startActivity(Intent(this@SplashActivity, UserMain::class.java))
                    finish()
                }
            }
        }
        thread.start()
    }
}