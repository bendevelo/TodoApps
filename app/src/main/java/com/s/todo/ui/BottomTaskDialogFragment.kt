package com.s.todo.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.s.todo.Adapter.AdapterTask
import com.s.todo.databinding.BottomtaskdialogBinding
import com.s.todo.model.task

class BottomTaskDialogFragment(val list: List<task>?,val date:String) : BottomSheetDialogFragment() {

    lateinit var binding: BottomtaskdialogBinding
    lateinit var bottomlist: BottomListLocations
    private val mapListAdapter: AdapterTask by lazy {
        AdapterTask(this)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        if (list != null) {
            mapListAdapter.setData(list)
        }
        binding = BottomtaskdialogBinding.inflate(layoutInflater)

        binding.rv.apply {
            adapter = mapListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        }

        binding.date.text = date


        return binding.root
    }


    interface BottomListLocations {
        fun onButtonClick(task:String )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            bottomlist = context as BottomListLocations
        } catch (e: ClassCastException) {
        }
    }
}