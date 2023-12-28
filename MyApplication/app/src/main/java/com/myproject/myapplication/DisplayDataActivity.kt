package com.myproject.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.myproject.myapplication.databinding.ActivityDisplayDataBinding

class DisplayDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDisplayDataBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var userList: List<User>
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        // Initialize RecyclerView and UserAdapter
        userList = dbHelper.getAllUsers()
        userAdapter = UserAdapter(this, userList) { selectedUser ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("userId", selectedUser.id)
            startActivity(intent)
        }

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = userAdapter

        // Handle any other actions as needed
    }
}
