package com.rental_apps.android.rental_apps.admin

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.view.MenuItem
import android.view.View
import android.widget.*
import cn.pedant.SweetAlert.SweetAlertDialog
import com.rental_apps.android.rental_apps.R
import com.rental_apps.android.rental_apps.api.client.api
import com.rental_apps.android.rental_apps.model.model_mobil.ResponseRegisterCars
import com.rental_apps.android.rental_apps.model.model_user.DataUser
import com.rental_apps.android.rental_apps.myinterface.InitComponent
import com.rental_apps.android.rental_apps.utils.validate.cek
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
class ActivityCreateMobil : AppCompatActivity(), InitComponent, View.OnClickListener {
    //declare component
    private var et_nama_mobil: EditText? = null
    private var et_merk_mobil: EditText? = null
    private var et_deskripsi_mobil: EditText? = null
    private var et_tahun: EditText? = null
    private var et_kapasitas: EditText? = null
    private var et_harga_sewa: EditText? = null
    private var et_warna_mobil: EditText? = null
    private var sp_bensin: Spinner? = null
    private var et_plat: EditText? = null
    private var raktif: RadioButton? = null
    private var rtidakaktif: RadioButton? = null
    private var btnRegister: Button? = null
    private var take: Button? = null
    private var imgshow: ImageView? = null
    private val coordinatorLayout: CoordinatorLayout? = null
    //declare context
    private var mContext: Context? = null
    //declare variable
    private val userData: DataUser? = null
    //declare sweet alert
    private var pDialog: SweetAlertDialog? = null
    private var statusAktif = 0
    private val ketImage = 0
    var filePath = ""
    var selectedImage: Uri? = null
    private var encodedImage: String? = null
    override fun onCreate(SavedInstance: Bundle?) {
        super.onCreate(SavedInstance)
        setContentView(R.layout.activity_add_mobil)
        mContext = this
        startInit()
    }

    override fun startInit() {
        initToolbar()
        initUI()
        initValue()
        initEvent()
    }

    override fun initToolbar() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Register Mobil"
    }

    override fun initUI() {
        et_nama_mobil = findViewById<View>(R.id.et_nama_mobil) as EditText
        et_merk_mobil = findViewById<View>(R.id.et_merk_mobil) as EditText
        et_deskripsi_mobil = findViewById<View>(R.id.et_deskripsi_mobil) as EditText
        et_tahun = findViewById<View>(R.id.et_tahun) as EditText
        et_kapasitas = findViewById<View>(R.id.et_kapasitas) as EditText
        et_harga_sewa = findViewById<View>(R.id.et_harga_sewa) as EditText
        et_warna_mobil = findViewById<View>(R.id.et_warna_mobil) as EditText
        sp_bensin = findViewById<View>(R.id.sp_bensin) as Spinner
        et_plat = findViewById<View>(R.id.et_plat) as EditText
        raktif = findViewById<View>(R.id.raktif) as RadioButton
        rtidakaktif = findViewById<View>(R.id.rtidakaktif) as RadioButton
        imgshow = findViewById<View>(R.id.imgshow) as ImageView
        btnRegister = findViewById<View>(R.id.btn_register) as Button
        take = findViewById<View>(R.id.take) as Button
    }

    override fun initValue() {
        val adapter: MutableList<String> = ArrayList()
        adapter.add("Premium")
        adapter.add("Pertalite")
        adapter.add("Pertamax")
        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, adapter)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_bensin!!.adapter = dataAdapter
    }

    override fun initEvent() {
        btnRegister!!.setOnClickListener(this)
        raktif!!.setOnClickListener(this)
        rtidakaktif!!.setOnClickListener(this)
        take!!.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_register -> if (validasi()) register()
            R.id.raktif -> {
                statusAktif = 1
                rtidakaktif!!.isChecked = false
            }
            R.id.rtidakaktif -> {
                statusAktif = 0
                raktif!!.isChecked = false
            }
            R.id.take -> ChooseGallerOrCamera()
        }
    }

    private fun register() {
        pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pDialog!!.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog!!.titleText = "Loading"
        pDialog!!.setCancelable(false)
        pDialog!!.show()
        et_nama_mobil = findViewById<View>(R.id.et_nama_mobil) as EditText
        et_merk_mobil = findViewById<View>(R.id.et_merk_mobil) as EditText
        et_deskripsi_mobil = findViewById<View>(R.id.et_deskripsi_mobil) as EditText
        et_tahun = findViewById<View>(R.id.et_tahun) as EditText
        et_kapasitas = findViewById<View>(R.id.et_kapasitas) as EditText
        et_harga_sewa = findViewById<View>(R.id.et_harga_sewa) as EditText
        et_warna_mobil = findViewById<View>(R.id.et_warna_mobil) as EditText
        sp_bensin = findViewById<View>(R.id.sp_bensin) as Spinner
        et_plat = findViewById<View>(R.id.et_plat) as EditText
        raktif = findViewById<View>(R.id.raktif) as RadioButton
        rtidakaktif = findViewById<View>(R.id.rtidakaktif) as RadioButton
        val register: Call<ResponseRegisterCars?>?
        register = api.mobilRegister(et_nama_mobil!!.text.toString(),
                et_merk_mobil!!.text.toString(),
                et_deskripsi_mobil!!.text.toString(),
                et_tahun!!.text.toString(),
                et_kapasitas!!.text.toString(),
                et_harga_sewa!!.text.toString(),
                et_warna_mobil!!.text.toString(),
                sp_bensin!!.selectedItemPosition + 1,
                et_plat!!.text.toString(),
                "" + statusAktif,
                encodedImage)
        register!!.enqueue(object : Callback<ResponseRegisterCars> {
            override fun onResponse(call: Call<ResponseRegisterCars>, response: Response<ResponseRegisterCars>) {
                pDialog!!.hide()
                if (response.isSuccessful) {
                    if (response.body().status!!) {
                        SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Info")
                                .setContentText("Data Mobil Berhasil Di Buat!")
                                .show()
                    } else {
                        SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Info")
                                .setContentText("Data Mobil Gagal Di Buat!")
                                .show()
                    }
                } else {
                    SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Info")
                            .setContentText("Data Mobil Gagal Di Buat!")
                            .show()
                }
            }

            override fun onFailure(call: Call<ResponseRegisterCars>, t: Throwable) {
                pDialog!!.hide()
                SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Koneksi bermasalah!")
                        .show()
            }
        })
    }

    private fun validasi(): Boolean {
        return if (!cek(et_nama_mobil!!)
                && !cek(et_merk_mobil!!)
                && !cek(et_deskripsi_mobil!!)
                && !cek(et_tahun!!)
                && !cek(et_kapasitas!!)
                && !cek(et_harga_sewa!!)
                && !cek(et_warna_mobil!!)
                && !cek(et_plat!!)) {
            true
        } else {
            false
        }
    }

    private fun ChooseGallerOrCamera() {
        val items = arrayOf<CharSequence>("Choose from Gallery",
                "Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Photo")
        builder.setItems(items, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, item: Int) {
                if (items[item] == "Take Photo") {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    var photo: File? = null
                    try { // place where to store camera taken picture
                        photo = createTemporaryFile("picture", ".jpg")
                        photo.delete()
                    } catch (e: Exception) {
                    }
                    selectedImage = Uri.fromFile(File("/sdcard/sample.jpg"))
                    cameraIntent.putExtra("ket", "1")
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImage)
                    startActivityForResult(cameraIntent, REQUEST_CAMERA)
                } else if (items[item] == "Choose from Gallery") {
                    val intent = Intent()
                    intent.type = "image/*" //set type for files (image type)
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FROM_GALLERY)
                } else if (items[item] == "Cancel") {
                    dialog.dismiss()
                }
            }

            @Throws(Exception::class)
            private fun createTemporaryFile(part: String, ext: String): File {
                var tempDir = Environment.getExternalStorageDirectory()
                tempDir = File(tempDir.absolutePath + "/.temp/")
                if (!tempDir.exists()) {
                    tempDir.mkdirs()
                }
                return File.createTempFile(part, ext, tempDir)
            }
        })
        builder.show()
    }

    fun getImageUri(inContext: Context, inImage: Bitmap?): Uri {
        val bytes = ByteArrayOutputStream()
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getRealPathFromURI(uri: Uri?): String {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }

    private fun getRealPathFromURIPath(contentURI: Uri?, activity: Activity): String {
        val cursor = activity.contentResolver.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI!!.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            cursor.getString(idx)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                val cr = this.contentResolver
                var photo: Bitmap? = null
                try {
                    photo = MediaStore.Images.Media.getBitmap(cr, selectedImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                //                selectedImage = getImageUri(getApplicationContext(), photo);
//                filePath=getRealPathFromURI(selectedImage);
//                setImageView(filePath);
            } else if (resultCode == Activity.RESULT_CANCELED) { // user cancelled Image capture
                Toast.makeText(applicationContext,
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show()
            } else { // failed to capture image
                Toast.makeText(applicationContext,
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show()
            }
        } else if (requestCode == PICK_FROM_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                selectedImage = data!!.data
                //                tampil_gambar_sk_hilang.setImageURI(selectedImage);
                filePath = getRealPathFromURIPath(selectedImage, this)
                setImageView(filePath)
            }
        }
    }

    private fun setImageView(filepath: String) {
        val imgFile = File(filepath)
        val bm = BitmapFactory.decodeFile(filepath)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos) //bm is the bitmap object
        val ba = baos.toByteArray()
        encodedImage = Base64.encodeToString(ba, Base64.DEFAULT)
        if (imgFile.exists()) {
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            val f = File(filepath)
            var b: Bitmap? = null
            try {
                var fis = FileInputStream(f)
                BitmapFactory.decodeStream(fis, null, o)
                fis.close()
                var sc = 0.0f
                var scale = 1
                //if image height is greater than width
                if (o.outHeight > o.outWidth) {
                    sc = o.outHeight / 400.toFloat()
                    scale = Math.round(sc)
                } else {
                    sc = o.outWidth / 400.toFloat()
                    scale = Math.round(sc)
                }
                // Decode with inSampleSize
                val o2 = BitmapFactory.Options()
                o2.inSampleSize = scale
                fis = FileInputStream(f)
                b = BitmapFactory.decodeStream(fis, null, o2)
                //                if (ketImage==1) {
//                    tampil_gambar_rt.setImageBitmap(b);
//                    listOfImagesPath.set(0,filepath);
//                }else if (ketImage==2) {
//                    tampil_gambar_kk.setImageBitmap(b);
//                    listOfImagesPath.set(1,filepath);
//                }else if (ketImage==3) {
//                    tampil_gambar_nikah.setImageBitmap(b);
//                    listOfImagesPath.set(2,filepath);
//                }else if (ketImage==4) {
//                    tampil_gambar_akta.setImageBitmap(b);
//                    listOfImagesPath.set(3,filepath);
//                }else if (ketImage==5) {
//                    tampil_gambar_master_kk.setImageBitmap(b);
//                    listOfImagesPath.set(4,filepath);
//                }
                imgshow!!.setImageBitmap(b)
                fis.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val REQUEST_CAMERA = 1888
        private const val SELECT_FILE = 1887
        private const val PICK_FROM_GALLERY = 2
    }
}

private fun <T> Call<T>.enqueue(callback: Callback<ResponseRegisterCars>) {

}
