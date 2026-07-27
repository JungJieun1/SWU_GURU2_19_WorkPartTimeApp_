package com.example.swu_guru_19_workparttimeapp_

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

class activity_employee_add : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var layoutProfileSelect: FrameLayout
    private lateinit var ivProfileImage: ImageView
    private lateinit var etEmployeeName: EditText
    private lateinit var etEmployeePhone: EditText
    private lateinit var spinnerWorkType: Spinner
    private lateinit var btnDelete: Button
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_employee_add)
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
        layoutProfileSelect = findViewById(R.id.layoutProfileSelect)
        ivProfileImage = findViewById(R.id.ivProfileImage)
        etEmployeeName = findViewById(R.id.etEmployeeName)
        etEmployeePhone = findViewById(R.id.etEmployeePhone)
        spinnerWorkType = findViewById(R.id.spinnerWorkType)
        btnDelete = findViewById(R.id.btnDelete)
        btnSave = findViewById(R.id.btnSave)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        layoutProfileSelect.setOnClickListener {
            Toast.makeText(this, "프로필 사진을 변경합니다.", Toast.LENGTH_SHORT).show()
        }

        btnDelete.setOnClickListener {
            Toast.makeText(this, "직원 정보가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnSave.setOnClickListener {
            val name = etEmployeeName.text.toString().trim()
            val phone = etEmployeePhone.text.toString().trim()
            val workType = spinnerWorkType.selectedItem.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "${name} 직원의 정보가 저장되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}