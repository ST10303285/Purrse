package com.example.purrse

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.purrse.data.AppDatabase
import com.example.purrse.databinding.ActivityAddExpenseBinding
import com.example.purrse.repo.CategoryRepo
import com.example.purrse.repo.ExpenseRepo
import com.example.purrse.viewModel.AddExpenseViewModel
import com.example.purrse.viewModel.factory.AddExpenseViewModelFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddExpenseBinding
    private val viewModel: AddExpenseViewModel by viewModels {
        val db = AppDatabase.getDatabase(this)
        AddExpenseViewModelFactory(
            ExpenseRepo(db.expenseDao()),
            CategoryRepo(db.categoryDao())
        )
    }

    private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getIntExtra("userId", -1)

        // Category name input
        binding.etCategoryName.doAfterTextChanged {
            viewModel.categoryName.value = it.toString()
        }

        // Date picker
        binding.tvDate.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(this, { _, y, m, d ->
                val selected = Calendar.getInstance().apply { set(y, m, d) }
                val formatted = DATE_FORMAT.format(selected.time)
                viewModel.date.value = formatted
                binding.tvDate.text = formatted
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Bind amount & description
        binding.etAmount.doAfterTextChanged { viewModel.amount.value = it.toString() }
        binding.etDescription.doAfterTextChanged { viewModel.description.value = it.toString() }

        // Save expense
        binding.btnSave.setOnClickListener {
            viewModel.saveExpense(userId)
        }

        lifecycleScope.launch {
            viewModel.saveState.collect { success ->
                if (success) {
                    Toast.makeText(this@AddExpenseActivity, "Saved!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@AddExpenseActivity, "Please fill all required fields", Toast.LENGTH_LONG).show()
                }
            }
        }

        // Receipt image picker
        binding.btnAddReceipt.setOnClickListener {
            openReceiptPicker()
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK_RECEIPT = 1001
    }

    private fun openReceiptPicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(intent, REQUEST_CODE_PICK_RECEIPT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_RECEIPT && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                binding.ivReceiptThumbnail.setImageURI(uri)
                viewModel.receiptUri.value = uri.toString()
            }
        }
    }
}
