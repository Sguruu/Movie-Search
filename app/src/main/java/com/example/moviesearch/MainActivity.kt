package com.example.moviesearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this,BottomNavigationViewActivity2::class.java)

        val handler = android.os.Handler()
        handler.postDelayed({
            startActivity(intent)
        },1800)
    }

}