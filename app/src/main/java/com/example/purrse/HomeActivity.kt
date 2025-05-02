package com.example.purrse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.purrse.data.AppDatabase
import com.example.purrse.databinding.ActivityHomeBinding
import com.example.purrse.repo.ExpenseRepo
import com.example.purrse.repo.GoalRepo
import com.example.purrse.repo.UserRepo
import com.example.purrse.viewModel.HomeViewModel
import com.example.purrse.viewModel.factory.HomeViewModelFactory
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1) Inflate via ViewBinding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2) Grab userId
        val userId = intent.getIntExtra("userId", -1)
        if (userId == -1) {
            Toast.makeText(this, "Invalid User ID", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // 3) Get DAOs and inject into ViewModel
        val db = AppDatabase.getDatabase(applicationContext)
        val expenseDao = db.expenseDao()
        val goalDao    = db.goalDao()

        val factory = HomeViewModelFactory(expenseDao, goalDao, userId)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        // 4) Observe LiveData using binding
        viewModel.balance.observe(this) { bal ->
            binding.textBalance.text = "Balance: R%.2f".format(bal)
        }
        viewModel.income.observe(this) { inc ->
            binding.textIncome.text = "Income: R%.2f".format(inc)
        }
        viewModel.expense.observe(this) { exp ->
            binding.textExpense.text = "Expense: R%.2f".format(exp)
        }
        viewModel.budgetPercentage.observe(this) { pct ->
            binding.textBudgetPercentage.text = "$pct%"
            binding.budgetProgress.progress = pct
        }

        // 5) Bottom navigation
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_set_budget -> {
                    startActivity(Intent(this, GoalsActivity::class.java))
                    true
                }
                R.id.nav_add_expense -> {
                    startActivity(Intent(this, AddExpenseActivity::class.java))
                    true
                }
                R.id.nav_trends -> {
                    startActivity(Intent(this, TrendsActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // 6) Quick visibility check
        Toast.makeText(this, "HomeActivity loaded", Toast.LENGTH_SHORT).show()
    }
}