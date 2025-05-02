package com.example.purrse

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.purrse.data.AppDatabase
import com.example.purrse.databinding.ActivityCategoryCreationBinding
import com.example.purrse.model.Category
import kotlinx.coroutines.launch

class CategoryCreationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryCreationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getIntExtra("userId", -1)
        if (userId < 0) {
            Toast.makeText(this, "Invalid user", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.btnSaveCategory.setOnClickListener {
            val name = binding.etCategoryName.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, "Enter a category name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newCategory = Category(userId = userId, name = name)
            val dao = AppDatabase.getDatabase(this).categoryDao()

            lifecycleScope.launch {
                dao.insertCategory(newCategory)
                Toast.makeText(this@CategoryCreationActivity, "Category saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}