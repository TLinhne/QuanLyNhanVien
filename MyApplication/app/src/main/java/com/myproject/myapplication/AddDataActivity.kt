package com.myproject.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.myproject.myapplication.databinding.ActivityAddDataBinding

class AddDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddDataBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        // Xử lý sự kiện khi người dùng nhấn nút "Thêm"
        binding.buttonAddData.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            val email = binding.editTextEmail.text.toString()
            val phone = binding.editTextPhone.text.toString()

            // Kiểm tra xem các trường dữ liệu có được nhập hay không
            if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {
                // Thực hiện thêm dữ liệu mới vào cơ sở dữ liệu
                val inserted = dbHelper.insertUser(username, password, email, phone)
                if (inserted != -1L) {
                    Toast.makeText(this, "Dữ liệu đã được thêm", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, DisplayDataActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Thêm dữ liệu không thành công", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
