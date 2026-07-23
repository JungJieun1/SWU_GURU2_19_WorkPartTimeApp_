package com.example.swu_guru_19_workparttimeapp_

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_workplace_add : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var layoutImageSelect: FrameLayout
    private lateinit var ivWorkplaceImage: ImageView
    private lateinit var etWorkplaceName: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var etAddress: EditText
    private lateinit var btnSearchAddress: Button
    private lateinit var etPhone: EditText
    private lateinit var btnDelete: Button
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_workplace_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()

        setupListeners()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        layoutImageSelect = findViewById(R.id.layoutImageSelect)
        ivWorkplaceImage = findViewById(R.id.ivWorkpalceImage)
        etWorkplaceName = findViewById(R.id.etWorkpalceName)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        etAddress = findViewById(R.id.etAddress)
        btnSearchAddress = findViewById(R.id.btnSearchAddress)
        etPhone = findViewById(R.id.etPhone)
        btnDelete = findViewById(R.id.btnDelete)
        btnSave = findViewById(R.id.btnSave)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        layoutImageSelect.setOnClickListener {
            // 갤러리 open 연동 기능 추가 예정
            Toast.makeText(this, "갤러리를 열어 사진을 선택합니다.", Toast.LENGTH_SHORT).show()
        }

        btnSearchAddress.setOnClickListener {
            // Kakao 주소 검색 API 또는 웹뷰 연동
            Toast.makeText(this, "주소 검색 화면을 엽니다.", Toast.LENGTH_SHORT).show()
        }

        btnDelete.setOnClickListener {
            // DB 또는 데이터 리스트에서 해당 업장 삭제 처리
            Toast.makeText(this, "업장이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnSave.setOnClickListener {
            // 입력한 데이터(업장명, 업종, 주소, 전화번호) 유효성 검사 및 DB 저장
            Toast.makeText(this, "업장 정보가 저장되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}