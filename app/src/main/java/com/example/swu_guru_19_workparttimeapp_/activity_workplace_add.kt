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
import androidx.activity.result.contract.ActivityResultContracts
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

    // 1. 갤러리 연동용 Launcher
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            ivWorkplaceImage.setImageURI(it)
            Toast.makeText(this, "사진이 선택되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // 2. 주소 검색 웹뷰 연동용 Launcher (추가됨)
    private val addressLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val address = result.data?.getStringExtra("result_address")
            address?.let {
                etAddress.setText(it) // 검색 결과를 주소 EditText에 세팅
                Toast.makeText(this, "주소가 입력되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

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
            getContent.launch("image/*")
        }

        btnSearchAddress.setOnClickListener {
            val intent = Intent(this, KakaoAddressActivity::class.java)
            addressLauncher.launch(intent)
        }

        btnDelete.setOnClickListener {
            Toast.makeText(this, "업장이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnSave.setOnClickListener {
            Toast.makeText(this, "업장 정보가 저장되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}