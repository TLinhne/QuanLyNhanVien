package com.myproject.myapplication

import android.content.ContentValues
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.myproject.myapplication.databinding.ActivityRegisterBinding
import com.myproject.myapplication.DatabaseHelper

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        // Xử lý sự kiện khi người dùng nhấn nút "Đăng ký"
        binding.buttonRegister.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            val email = binding.editTextEmail.text.toString()
            val phone = binding.editTextPhone.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {
                // Kiểm tra xem tên người dùng đã tồn tại trong cơ sở dữ liệu chưa
                if (!dbHelper.isUsernameExists(username)) {
                    // Tên người dùng chưa tồn tại, tiến hành đăng ký
                    val result = dbHelper.insertUser(username, password, email, phone)

                    if (result > 0) {
                        // Đăng ký thành công
                        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        // Đăng ký thất bại
                        Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Tên người dùng đã tồn tại
                    Toast.makeText(this, "Tên người dùng đã tồn tại", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Hiển thị thông báo nếu người dùng chưa nhập đủ thông tin
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
