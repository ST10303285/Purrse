package com.example.purrse

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.purrse.adapter.CategoryAdapter
import com.example.purrse.data.AppDatabase
import com.example.purrse.databinding.ActivityCategorySelectionBinding
import com.example.purrse.repo.CategoryRepo
import com.example.purrse.viewModel.CategoryViewModel
import com.example.purrse.viewModel.factory.CategoryViewModelFactory
import kotlinx.coroutines.launch

class CategorySelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategorySelectionBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var viewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategorySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1) get the userId from the Intent
        val currentUserId = intent.getIntExtra("userId", -1)
        if (currentUserId < 0) {
            // nothing to do without a valid user
            finish()
            return
        }

        // 2) wire up your ViewModel with the factory that now takes userId
        val dao = AppDatabase.getDatabase(this).categoryDao()
        val repo = CategoryRepo(dao)
        val factory = CategoryViewModelFactory(repo, currentUserId)
        viewModel = ViewModelProvider(this, factory)
            .get(CategoryViewModel::class.java)

        // then the rest stays exactly as you had it:
        setupRecyclerView()
        observeCategories()
        setupSearch()
    }

    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter { selectedCategory ->
            val resultIntent = Intent().apply {
                putExtra("SELECTED_CATEGORY", selectedCategory.name)
                putExtra("SELECTED_CATEGORY_ID", selectedCategory.categoryId)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(this@CategorySelectionActivity)
            adapter = categoryAdapter
        }
    }

    private fun observeCategories() {
        lifecycleScope.launch {
            viewModel.allCategories.collect { categoryList ->
                categoryAdapter.submitList(categoryList)
            }
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.query.value = s.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}
