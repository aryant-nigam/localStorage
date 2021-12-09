package com.example.localstorage

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.getSystemService
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var databaseReference:DatabaseReference;
    lateinit var sr:StorageReference
    lateinit var outputfile:File;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.play).setOnClickListener()
        {
            startActivity(Intent(applicationContext,MainActivity3 ::class.java))
        }
        val getLocSTP = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                if(it.resultCode== RESULT_OK) {
                    downloadFile()
                    //Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
                }
            })

        val requestPermission=registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                downloadFile()
                //getLocSTP.launch(Intent(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            } else {
                Toast.makeText(applicationContext, "failed to grab the permission to store the data on local storage", Toast.LENGTH_SHORT
                ).show()
            }
        }

        findViewById<Button>(R.id.download).setOnClickListener() {
            requestPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

}
    fun downloadFile():Boolean{
        val yourAppDir = File(applicationContext.getExternalFilesDir("Aryant").toString())
        if(!yourAppDir.exists() && yourAppDir.isDirectory())
            yourAppDir.mkdir()
        databaseReference=Firebase.database.getReference().child("mymusic").child("m1")
        databaseReference.get().addOnSuccessListener(){
            if(it.exists()){
                val path=it.value as String
                val request:DownloadManager.Request=DownloadManager.Request(Uri.parse(path))

                request.setAllowedNetworkTypes( DownloadManager.Request.NETWORK_MOBILE)
                //request.setTitle("Download")
                //request.setDescription("getting file ready . . .")
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalPublicDir(applicationContext.getExternalFilesDir("Aryant").toString(),"m1")
                val dataManager:DownloadManager= getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                dataManager.enqueue(request)
            }//applicationContext.filesDir.toString()
        }.addOnFailureListener(){
            Toast.makeText(applicationContext,"Failed to access the data",Toast.LENGTH_SHORT).show()
        }
        return false
    }

}
