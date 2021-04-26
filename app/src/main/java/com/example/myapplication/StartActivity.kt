package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        var actBtn:Button = findViewById(R.id.actBtn)
        var frgBtn:Button = findViewById(R.id.frgmBtn)

        actBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        })

        frgBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, TabActivity::class.java)
            startActivity(intent)
        })
    }
}