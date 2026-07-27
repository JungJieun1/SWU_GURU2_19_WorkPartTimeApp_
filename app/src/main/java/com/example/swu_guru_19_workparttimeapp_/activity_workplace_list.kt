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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class activity_workplace_list : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WorkplaceAdapter
    private var workplaceList = mutableListOf<Workplace>()

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
        recyclerView = findViewById(R.id.WorkplaceList)
        val btnWorkplaceAdd = findViewById<Button>(R.id.btnWorkplaceAdd)

        adapter = WorkplaceAdapter(workplaceList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnBack?.setOnClickListener {
            finish()
        }

        btnWorkplaceAdd?.setOnClickListener {
            val intent = Intent(this, activity_workplace_add::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // 9번(추가/삭제) 화면에서 저장하고 돌아왔을 때
        // DB 목록을 읽어와서 workplaceList를 갱신, adapter.notifyDataSetChanged()를 호출
    }
}