package com.kennan.backgroundattribute

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.kennan.lib.backdrop.Backdrop

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Backdrop.initBackDrop(this)
        setContentView(R.layout.activity_main)

        R.attr.background
        findViewById<View>(R.id.main).setOnClickListener { startActivity(Intent(this, MainActivityB::class.java)) }
    }
}