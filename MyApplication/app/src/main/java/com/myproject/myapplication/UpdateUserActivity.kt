package com.myproject.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.myproject.myapplication.databinding.ActivityUpdateUserBinding
import com.myproject.myapplication.DatabaseHelper
import com.myproject.myapplication.User

class UpdateUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateUserBinding
    private lateinit var user: User
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        val userBundle = intent.getBundleExtra("userBundle")
        val id: Int = userBundle?.getInt("id") ?: 0
        val username: String = userBundle?.getString("username") ?: ""
        val password: String = userBundle?.getString("password") ?: ""
        val email: String = userBundle?.getString("email") ?: ""
        val phone: String = userBundle?.getString("phone") ?: ""



        // Hiển thị thông tin người dùng hiện tại trong các EditText
        binding.editTextUsername.setText(username)
        binding.editTextEmail.setText(email)
        binding.editTextPhone.setText(phone)


        // Xử lý sự kiện khi người dùng nhấn nút "Lưu cập nhật"
        binding.buttonSaveUpdate.setOnClickListener {
            val newUsername = binding.editTextUsername.text.toString()
            val newEmail = binding.editTextEmail.text.toString()
            val newPhone = binding.editTextPhone.text.toString()

            // Cập nhật thông tin người dùng trong cơ sở dữ liệu
            val updated = dbHelper.updateUser(id, newUsername,password, newEmail, newPhone)

            if (updated > 0) {
                Toast.makeText(this, "Thông tin người dùng đã được cập nhật", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Cập nhật thông tin người dùng không thành công", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
