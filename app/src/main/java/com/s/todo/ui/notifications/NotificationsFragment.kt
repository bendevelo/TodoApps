package com.s.todo.ui.notifications

import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.s.todo.Adapter.AdapterPhotoList
import com.s.todo.Data.taskRepository
import com.s.todo.Data.todoDatabase
import com.s.todo.databinding.FragmentNotificationsBinding
import com.s.todo.util.AdditionalIndicator
import com.s.todo.viewmodel.ViewModelFactory
import com.s.todo.viewmodel.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.cleverpumpkin.calendar.CalendarDate

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private val binding get() = _binding!!
    lateinit var viewModell: viewModel
    lateinit var photoAdapter: AdapterPhotoList

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val repository =
            taskRepository(todoDatabase.getDatabaseInstance(requireContext()).taskDao())
        val viewModelFactory = ViewModelFactory(repository)
        viewModell = ViewModelProvider(this, viewModelFactory)[viewModel::class.java]

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        prepareRecyclerView()


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun prepareRecyclerView() {
        photoAdapter = AdapterPhotoList()
        binding.rvg.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = photoAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    fun getData(){
        CoroutineScope(Dispatchers.IO).launch {
            val listimg = viewModell.getTrx()

            lifecycleScope.launch(Dispatchers.Main) {
                photoAdapter.setData(listimg)
            }
        }
    }
}