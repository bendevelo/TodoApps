package com.s.todo.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s.todo.databinding.ListTaskprimerBinding
import com.s.todo.model.task
import com.s.todo.ui.BottomTaskDialogFragment
import com.s.todo.ui.DetilActivity

class AdapterTaskHome() : RecyclerView.Adapter<AdapterTaskHome.CustomHolder>() {

    private val list = mutableListOf<task>()
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        mContext = parent.context
        val inflater = LayoutInflater.from(mContext)
        val binding = ListTaskprimerBinding.inflate(inflater, parent, false)
        return CustomHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listt: List<task>) {
        list.clear()
        list.addAll(listt)
        notifyDataSetChanged()
    }

    inner class CustomHolder(private val binding: ListTaskprimerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(maps: task) {
            binding.apply {
                maps.also {

                    binding.task.text= maps.title
                    binding.status.text= maps.status


                }
            }
        }
    }

}