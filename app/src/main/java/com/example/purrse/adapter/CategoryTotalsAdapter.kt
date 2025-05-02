package com.example.purrse.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.purrse.R
import com.example.purrse.data.CategorySpending

class CategoryTotalsAdapter : ListAdapter<CategorySpending, CategoryTotalsAdapter.VH>(Diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_total, parent, false)
    )

    override fun onBindViewHolder(holder: VH, pos: Int) = holder.bind(getItem(pos))

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.categoryNameText)
        private val tot  = itemView.findViewById<TextView>(R.id.totalText)

        fun bind(c: CategorySpending) {
            name.text = c.categoryId.toString()       // later map ID → name
            tot.text  = "R${c.total}"
        }
    }

    class Diff : DiffUtil.ItemCallback<CategorySpending>() {
        override fun areItemsTheSame(a: CategorySpending, b: CategorySpending) =
            a.categoryId == b.categoryId
        override fun areContentsTheSame(a: CategorySpending, b: CategorySpending) = a == b
    }
}

