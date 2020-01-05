package com.rental_apps.android.rental_apps.helper

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and
import kotlin.experimental.or

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
object Hash {
    @JvmStatic
    fun MD5(md5: String): String? {
        try {
            val md = MessageDigest.getInstance("MD5")
            val array = md.digest(md5.toByteArray())
            val sb = StringBuffer()
            for (i in array.indices) {
                sb.append(Integer.toHexString((array[i] and 0xFF.toByte() or 0x100.toByte()).toInt()).substring(1, 3))
            }
            return sb.toString()
        } catch (e: NoSuchAlgorithmException) {
        }
        return null
    }
}