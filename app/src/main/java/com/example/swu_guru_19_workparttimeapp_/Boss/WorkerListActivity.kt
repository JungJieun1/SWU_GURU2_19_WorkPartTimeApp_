package com.example.swu_guru_19_workparttimeapp_.Boss

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.swu_guru_19_workparttimeapp_.R // 상위 폴더 R 가져옴

class WorkerListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boss_worker_list) // 화면 연결

        val ivBack = findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener {
            finish()
        }
    }
}