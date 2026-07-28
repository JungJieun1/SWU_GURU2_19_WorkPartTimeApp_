package com.example.swu_guru_19_workparttimeapp_.Boss

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.swu_guru_19_workparttimeapp_.R
import com.example.swu_guru_19_workparttimeapp_.UserDatabaseHelper

class activity_employee_add : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var layoutProfileSelect: FrameLayout
    private lateinit var ivProfileImage: ImageView
    private lateinit var etEmployeeName: EditText
    private lateinit var etEmployeePhone: EditText
    private lateinit var spinnerWorkType: Spinner
    private lateinit var btnDelete: Button
    private lateinit var btnSave: Button
    private lateinit var dbHelper: UserDatabaseHelper

    private var employeeId = -1
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_employee_add)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = UserDatabaseHelper(this)

        initViews()
        loadEmployeeData()
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

    private fun loadEmployeeData() {
        employeeId = intent.getIntExtra("EMPLOYEE_ID", -1)
        isEditMode = employeeId != -1

        if (isEditMode) {
            etEmployeeName.setText(intent.getStringExtra("EMPLOYEE_NAME") ?: "")
            etEmployeePhone.setText(intent.getStringExtra("EMPLOYEE_PHONE") ?: "")
            setSpinnerSelection(intent.getStringExtra("EMPLOYEE_ROLE") ?: "")
            btnDelete.visibility = View.VISIBLE
        } else {
            btnDelete.visibility = View.GONE
        }
    }

    private fun setSpinnerSelection(value: String) {
        for (i in 0 until spinnerWorkType.count) {
            if (spinnerWorkType.getItemAtPosition(i).toString() == value) {
                spinnerWorkType.setSelection(i)
                return
            }
        }
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        layoutProfileSelect.setOnClickListener {
            Toast.makeText(this, "프로필 사진 기능은 준비 중입니다.", Toast.LENGTH_SHORT).show()
        }

        btnSave.setOnClickListener {
            saveEmployee()
        }

        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("직원 삭제")
                .setMessage("이 직원 정보를 삭제할까요?")
                .setPositiveButton("삭제") { _, _ ->
                    val isSuccess = dbHelper.deleteEmployee(employeeId)

                    if (isSuccess) {
                        Toast.makeText(this, "직원 정보가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("취소", null)
                .show()
        }
    }

    private fun saveEmployee() {
        val name = etEmployeeName.text.toString().trim()
        val phone = etEmployeePhone.text.toString().trim()
        val role = spinnerWorkType.selectedItem?.toString() ?: ""

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "이름과 연락처를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val isSuccess = if (isEditMode) {
            dbHelper.updateEmployee(
                id = employeeId,
                name = name,
                phone = phone,
                hourlyWage = "",
                role = role
            )
        } else {
            dbHelper.insertEmployee(
                name = name,
                phone = phone,
                hourlyWage = "",
                role = role
            )
        }

        if (isSuccess) {
            val message = if (isEditMode) "직원 정보가 수정되었습니다." else "직원 정보가 저장되었습니다."
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "저장에 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}