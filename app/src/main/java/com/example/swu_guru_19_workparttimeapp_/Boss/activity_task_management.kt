package com.example.swu_guru_19_workparttimeapp_.Boss

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swu_guru_19_workparttimeapp_.R
import com.example.swu_guru_19_workparttimeapp_.Task
import com.example.swu_guru_19_workparttimeapp_.UserDatabaseHelper
import com.google.android.material.tabs.TabLayout
import java.util.Calendar

class activity_task_management : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var tabLayout: TabLayout
    private lateinit var rvTaskList: RecyclerView
    private lateinit var btnAddTask: Button
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var dbHelper: UserDatabaseHelper

    private val taskList = mutableListOf<Task>()
    private var selectedTaskType = "전체"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task_management)

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
        setupRecyclerView()
        setupListeners()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        tabLayout = findViewById(R.id.tabLayout)
        rvTaskList = findViewById(R.id.rvTaskList)
        btnAddTask = findViewById(R.id.btnAddTask)
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(taskList) { task ->
            showDeleteTaskDialog(task)
        }

        rvTaskList.layoutManager = LinearLayoutManager(this)
        rvTaskList.adapter = taskAdapter
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                selectedTaskType = when (tab?.position) {
                    1 -> "공동업무"
                    2 -> "개인업무"
                    else -> "전체"
                }

                loadTasks()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        btnAddTask.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val dialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_add_task, null)

        val etTaskTitle = dialogView.findViewById<EditText>(R.id.etTaskTitle)
        val spinnerTaskType = dialogView.findViewById<Spinner>(R.id.spinnerTaskType)
        val etDueDate = dialogView.findViewById<EditText>(R.id.etDueDate)

        etDueDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            DatePickerDialog(
                this,
                { _, _, selectedMonth, selectedDay ->
                    etDueDate.setText("${selectedMonth + 1}/${selectedDay} 까지")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        AlertDialog.Builder(this)
            .setTitle("새 업무 추가")
            .setView(dialogView)
            .setPositiveButton("추가") { _, _ ->
                val title = etTaskTitle.text.toString().trim()
                val dueDate = etDueDate.text.toString().trim()
                val taskType = spinnerTaskType.selectedItem?.toString() ?: "공동업무"

                if (title.isEmpty()) {
                    Toast.makeText(this, "업무 이름을 입력해 주세요.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (dueDate.isEmpty()) {
                    Toast.makeText(this, "마감일을 선택해 주세요.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val isSuccess = dbHelper.insertTask(
                    title = title,
                    content = dueDate,
                    taskType = taskType,
                    assignee = ""
                )

                if (isSuccess) {
                    Toast.makeText(this, "업무가 추가되었습니다.", Toast.LENGTH_SHORT).show()

                    selectedTaskType = "전체"
                    tabLayout.getTabAt(0)?.select()
                    loadTasks()
                } else {
                    Toast.makeText(this, "업무 저장에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("취소", null)
            .show()
    }

    private fun showDeleteTaskDialog(task: Task) {
        AlertDialog.Builder(this)
            .setTitle("업무 삭제")
            .setMessage("'${task.title}' 업무를 삭제할까요?")
            .setPositiveButton("삭제") { _, _ ->
                val isSuccess = dbHelper.deleteTask(task.id)

                if (isSuccess) {
                    Toast.makeText(this, "업무가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    loadTasks()
                } else {
                    Toast.makeText(this, "삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("취소", null)
            .show()
    }

    private fun loadTasks() {
        val allTasks = dbHelper.getAllTasks()

        val filteredTasks = when (selectedTaskType) {
            "공동업무" -> allTasks.filter { it.taskType == "공동업무" }
            "개인업무" -> allTasks.filter { it.taskType == "개인업무" }
            else -> allTasks
        }

        taskList.clear()
        taskList.addAll(filteredTasks)
        taskAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }
}