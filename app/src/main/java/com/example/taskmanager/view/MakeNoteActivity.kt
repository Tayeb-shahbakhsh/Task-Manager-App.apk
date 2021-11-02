package com.example.taskmanager.view

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.taskmanager.R
import com.example.taskmanager.databinding.ActivityMakeNoteBinding
import com.example.taskmanager.databinding.SelectImageDialogBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.util.jar.Manifest

class MakeNoteActivity : AppCompatActivity() {
    private lateinit var alertDialog : AlertDialog
    private lateinit var binding: ActivityMakeNoteBinding
    private lateinit var realImagePath: String
    private lateinit var imageContainer: ImageView
   private lateinit var dialogBinding: SelectImageDialogBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitButton.setOnClickListener {

            val title = binding.titleEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {

                Toast.makeText(this, "fill the inputs", Toast.LENGTH_LONG)
                    .show()

            } else {

                val intent = Intent()
                intent.putExtra("title", title)
                intent.putExtra("desc", description)
                intent.putExtra("imagePath", realImagePath)
                setResult(1, intent)
                showNotification("Task manager", "Add note done!")
                finish()

            }

        }

        binding.addNoteImageView.setOnClickListener { permissionsGranted() }
    }


    private fun showNotification(title: String, description: String = ""){

        val channelId = "TodoApp"

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.mipmap.ic_launcher_round)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >=    Build.VERSION_CODES.O){

            val channel = NotificationChannel(channelId, "note status", NotificationManager.IMPORTANCE_HIGH)

            notificationManager.createNotificationChannel(channel)

        }

        notificationManager.notify(0, notification.build())
    }


    private fun permissionsGranted(): Boolean {

        Dexter.withActivity(this)
            .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object :PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    Toast.makeText(this@MakeNoteActivity, "Gallery permission given select image", Toast.LENGTH_LONG).show()
                    selectImageDialog()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(this@MakeNoteActivity, "Give permission to select image", Toast.LENGTH_LONG).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    showRationalPermissionDialog()
                }


            }).onSameThread()
            .check()


        return true
    }

    private fun showRationalPermissionDialog() {

        AlertDialog.Builder(this).setMessage("permissions required for this feature are off  " +
                "you can give permissions from Application Settings")
            .setPositiveButton("GO TO SETTINGS"){
                _,_ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri  = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }catch (e: ActivityNotFoundException){
                    e.printStackTrace()
            }
        }
            .setNegativeButton("Cancel")
            {dialog,_ ->
                dialog.dismiss()
            }.show()

    }


    private fun getRealPathUri(uri: Uri?) : String{
        val filePath = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = managedQuery(uri, filePath, null, null, null)
        cursor.moveToFirst()
        val columnIndex = cursor.getColumnIndex(filePath[0])
        Log.d("dfklgmsdk", "getRealPathUri: $filePath -------------- $columnIndex -----------------$cursor")
        return cursor.getString(columnIndex)
    }

    private fun selectImageDialog(){
        alertDialog = AlertDialog.Builder(this).create()

        dialogBinding = SelectImageDialogBinding.inflate(LayoutInflater.from(this))
        alertDialog.setView(dialogBinding.root)


        dialogBinding.goToGalleryBtn.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 30)
        }
        alertDialog.show()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        imageContainer = binding.addNoteImageView
        if (requestCode == 30) {

            val imagePath = data?.data
            realImagePath = getRealPathUri(imagePath)

            Glide.with(this)
                .load(realImagePath)
                .into(imageContainer)
            alertDialog.hide()

        }
    }

}