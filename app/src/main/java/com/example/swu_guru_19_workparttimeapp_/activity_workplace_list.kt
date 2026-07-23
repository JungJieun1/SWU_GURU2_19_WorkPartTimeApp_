package com.example.swu_guru_19_workparttimeapp_

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class activity_workplace_list : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_workplace_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val workplaceHeader = findViewById<LinearLayout>(R.id.workplaceHeader)
        val btnBack = workplaceHeader.getChildAt(0) as? ImageView
        val recyclerView = findViewById<RecyclerView>(R.id.WorkplaceList)
        val btnWorkpalceAdd = findViewById<Button>(R.id.btnWorkpalceAdd)

        btnBack?.setOnClickListener {
            finish()
        }

        btnWorkpalceAdd.setOnClickListener {
            val intent = Intent(this, activity_workplace_add::class.java)
            startActivity(intent)
        }
    }
}