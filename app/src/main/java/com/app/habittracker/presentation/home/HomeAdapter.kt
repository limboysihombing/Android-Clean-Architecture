package com.app.habittracker.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.habittracker.R
import com.app.habittracker.core.domain.model.Habit
import com.app.habittracker.databinding.ItemListHabitBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ListViewHolder>(){

    private var listData = ArrayList<Habit>()

    fun setData(newListData: List<Habit>?){
        if(newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_habit, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemListHabitBinding.bind(itemView)

        fun bind(data: Habit) {
            with(binding){
                tvItemTitle.text = data.name
                tvItemHabitDay.text = data.dailyGoals[0].name
            }
        }

    }
}