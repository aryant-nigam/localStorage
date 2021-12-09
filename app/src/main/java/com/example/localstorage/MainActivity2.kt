package com.example.localstorage

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.gms.tasks.Task
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

class MainActivity2 : AppCompatActivity() {
    var path:String="https://firebasestorage.googleapis.com/v0/b/localstoragetest-f5af0.appspot.com/o/mymusic%2Fm1.mp3?alt=media&token=18c0c0f4-f755-4ba2-b433-0347a0aed331"
    var fileName:String="m1.mp3"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        /*var databaseReference= Firebase.database.getReference().child("mymusic").child("m1")
        databaseReference.get().addOnSuccessListener() {
            if(it.exists())
            {
                path=it.value as String
            }
        }.addOnFailureListener(){

        }

        findViewById<Button>(R.id.getbt).setOnClickListener(){
            downloadSong(fileName,path)
        }*/
    }

    /*private fun downloadSong(fileName: String, path: String) {
       /*val a= AsyncTask<Unit,Int,Boolean>(){
            doIn
        }*/
        GlobalScope.launch (Dispatchers.IO){
            var file: File = getFileStreamPath(fileName)
            if(!file.exists()) {
                try {
                    var fileOutputStream:FileOutputStream=openFileOutput(fileName,Context.MODE_PRIVATE)
                    val url:URL= URL(path)
                    val connection=url.openConnection()
                    val contentLength=connection.contentLength
                    var ip:InputStream=BufferedInputStream(url.openStream())
                    var data= ByteArray(contentLength)
                    var total=0
                    var count:Int
                    while(ip.read(data) !=-1)
                    {
                        count=ip.read()
                        total+=count
                        fileOutputStream.write(data,0,count)
                    }
                    fileOutputStream.flush()
                    fileOutputStream.close()
                    launch(Dispatchers.Main) {
                        playMusic(fileName)
                    }

                }
                catch (e:Exception)
                {
                    Log.d("main2", "failed downloading song")
                }

            }
        }

    }

    private fun playMusic(fileName: String) {
        var file= getFileStreamPath(fileName)
        var musicPlayer=MediaPlayer.create(this,Uri.fromFile(file.absoluteFile))
        musicPlayer.start()
    }*/
}