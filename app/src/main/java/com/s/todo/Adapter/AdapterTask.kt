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

class AdapterTask(var bottomListLocations: BottomTaskDialogFragment) : RecyclerView.Adapter<AdapterTask.CustomHolder>() {

    private val list = mutableListOf<task>()
    private lateinit var mContext: Context
    lateinit var bottomlist: BottomTaskDialogFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        mContext = parent.context
        val inflater = LayoutInflater.from(mContext)
        val binding = ListTaskprimerBinding.inflate(inflater, parent, false)
        return CustomHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        holder.bind(list[position])
        bottomlist = bottomListLocations
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

                    binding.rl.setOnClickListener({

                        val intent = Intent(mContext,DetilActivity::class.java)

                        intent.putExtra("id",maps.id.toString())
                        intent.putExtra("title",maps.title)
                        intent.putExtra("desc",maps.description)
                        intent.putExtra("due",maps.due)
                        intent.putExtra("reminder",maps.reminder.toString())
                        intent.putExtra("file",maps.file)
                        intent.putExtra("status",maps.status)
                        intent.putExtra("type",maps.type)

                        mContext.startActivity(intent)
                        bottomlist.dismiss()

                    })

                }
            }
        }
    }

}