package com.example.swu_guru_19_workparttimeapp_.Boss

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.swu_guru_19_workparttimeapp_.R
import com.example.swu_guru_19_workparttimeapp_.UserDatabaseHelper

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
    private lateinit var dbHelper: UserDatabaseHelper

    private var workplaceId = -1
    private var isEditMode = false

    private val getContent = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            ivWorkplaceImage.setImageURI(it)
            Toast.makeText(this, "사진이 선택되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private val addressLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val address = result.data?.getStringExtra("result_address")

            address?.let {
                etAddress.setText(it)
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
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        dbHelper = UserDatabaseHelper(this)

        initViews()
        loadWorkplaceData()
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

    private fun loadWorkplaceData() {
        workplaceId = intent.getIntExtra("WORKPLACE_ID", -1)
        isEditMode = workplaceId != -1

        if (isEditMode) {
            etWorkplaceName.setText(intent.getStringExtra("WORKPLACE_NAME") ?: "")
            etAddress.setText(intent.getStringExtra("WORKPLACE_ADDRESS") ?: "")
            etPhone.setText(intent.getStringExtra("WORKPLACE_PHONE") ?: "")

            setSpinnerSelection(
                intent.getStringExtra("WORKPLACE_CATEGORY") ?: ""
            )

            btnDelete.visibility = View.VISIBLE
        } else {
            btnDelete.visibility = View.GONE
        }
    }

    private fun setSpinnerSelection(value: String) {
        for (i in 0 until spinnerCategory.count) {
            if (spinnerCategory.getItemAtPosition(i).toString() == value) {
                spinnerCategory.setSelection(i)
                return
            }
        }
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

        btnSave.setOnClickListener {
            saveWorkplace()
        }

        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("업장 삭제")
                .setMessage("이 업장 정보를 삭제할까요?")
                .setPositiveButton("삭제") { _, _ ->
                    val isSuccess = dbHelper.deleteWorkplace(workplaceId)

                    if (isSuccess) {
                        Toast.makeText(
                            this,
                            "업장 정보가 삭제되었습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(this, "삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("취소", null)
                .show()
        }
    }

    private fun saveWorkplace() {
        val name = etWorkplaceName.text.toString().trim()
        val address = etAddress.text.toString().trim()
        val category = spinnerCategory.selectedItem?.toString() ?: ""
        val phone = etPhone.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(this, "업장명을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val isSuccess = if (isEditMode) {
            dbHelper.updateWorkplace(
                id = workplaceId,
                name = name,
                address = address,
                category = category,
                phone = phone
            )
        } else {
            dbHelper.insertWorkplace(
                name = name,
                address = address,
                category = category,
                phone = phone
            )
        }

        if (isSuccess) {
            val message = if (isEditMode) "업장 정보가 수정되었습니다." else "업장 정보가 저장되었습니다."
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "저장에 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}