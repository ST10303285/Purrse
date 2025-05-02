package com.example.purrse.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.purrse.R
import com.example.purrse.model.Expense

class ExpensesAdapter : ListAdapter<Expense, ExpensesAdapter.VH>(Diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
    )

    override fun onBindViewHolder(holder: VH, pos: Int) = holder.bind(getItem(pos))

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val amt = itemView.findViewById<TextView>(R.id.amountText)
        private val cat = itemView.findViewById<TextView>(R.id.categoryText)
        private val date = itemView.findViewById<TextView>(R.id.dateText)
        private val desc = itemView.findViewById<TextView>(R.id.descriptionText)

        fun bind(e: Expense) {
            amt.text = "R${e.amount}"
            cat.text = e.categoryId.toString()        // later map ID → name
            date.text = e.date
            desc.text = e.description
        }
    }

    class Diff : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(a: Expense, b: Expense) = a.expenseId == b.expenseId
        override fun areContentsTheSame(a: Expense, b: Expense) = a == b
    }
}

