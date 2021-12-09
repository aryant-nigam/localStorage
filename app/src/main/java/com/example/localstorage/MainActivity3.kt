package com.example.localstorage

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.FileDataSource
import com.google.android.exoplayer2.util.Util
import java.io.File
import kotlin.math.log


class MainActivity3 : AppCompatActivity() {
    lateinit var player: SimpleExoPlayer
    lateinit var  progressiveMediaSourceFactory:ProgressiveMediaSource.Factory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        var playerView=findViewById<PlayerView>(R.id.exp)
        playerView.setControllerShowTimeoutMs(0)
        player=SimpleExoPlayer.Builder(this).build()
        playerView.player=player
        var dataSourceFactory=DefaultDataSourceFactory(this, Util.getUserAgent(this,"app"))
        var audioSource= getFileUriFromLocalStorage("Aryant","m1")?.let { audio->
            ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                audio
            )
        }
        if (audioSource != null) {
            player.prepare(audioSource)
        }
        player.playWhenReady=true



    }

    fun getFileUriFromLocalStorage(taalName:String,taalId:String): MediaItem? {

        val yourAppDir = File(applicationContext.getExternalFilesDir("Aryant").toString(),"m1.mp3")

        val taal=File( "Phone storage//Android//data//com.example.localstorage//files//Aryant//Waalian (Pagalworld.mobi).mp3")
        //val taal = File(this.filesDir.absolutePath +"/"+"Aryant", "song.mp3")
        Log.d("yourapp", yourAppDir.exists().toString())
        Log.d("yourapp", yourAppDir.path.toString())
        Log.d("RawFileHelper", taal.path)
        if (taal.exists())
        {
            Log.d("Aryant", "tall exists")
            val uri=Uri.fromFile(taal)
            var dataSpec= DataSpec(uri)
            var fileDataSource= FileDataSource()
            try{
                fileDataSource.open(dataSpec)
                return MediaItem.Builder().setUri(fileDataSource.uri!!).setMediaId(taalId).build()
            }catch(e: FileDataSource.FileDataSourceException){
                Log.d("RawFileHelper",e.message.toString())
            }
        }
        else{
            Log.d("RawFileHelper","File does not exists")
        }
        return null
    }

}