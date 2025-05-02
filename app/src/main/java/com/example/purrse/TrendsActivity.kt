package com.example.purrse

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purrse.adapter.CategoryTotalsAdapter
import com.example.purrse.adapter.ExpensesAdapter
import com.example.purrse.data.AppDatabase
import com.example.purrse.viewModel.TrendsViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

class TrendsActivity : AppCompatActivity() {

    private lateinit var vm: TrendsViewModel
    private lateinit var expAdapter: ExpensesAdapter
    private lateinit var totAdapter: CategoryTotalsAdapter

    override fun onCreate(saved: Bundle?) {
        super.onCreate(saved)
        setContentView(R.layout.activity_trends)

        val userId = intent.getIntExtra("USER_ID", -1)
        val dao    = AppDatabase.getDatabase(this).expenseDao()
        vm = ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(c: Class<T>): T =
                TrendsViewModel(dao) as T
        })[TrendsViewModel::class.java]

        expAdapter = ExpensesAdapter()
        findViewById<RecyclerView>(R.id.rvExpenses).apply {
            layoutManager = LinearLayoutManager(this@TrendsActivity)
            adapter = expAdapter
        }

        totAdapter = CategoryTotalsAdapter()
        findViewById<RecyclerView>(R.id.rvCategoryTotals).apply {
            layoutManager = LinearLayoutManager(this@TrendsActivity)
            adapter = totAdapter
        }

        // pick your date‐range however you like; here we hardcode:
        val from = "2025-05-01"
        val to   = "2025-05-31"
        vm.load(userId, from, to)

        vm.expenses.observe(this) { expAdapter.submitList(it) }
        vm.totals  .observe(this) { totAdapter.submitList(it) }
    }
}

