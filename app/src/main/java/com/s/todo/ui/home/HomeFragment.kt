package com.s.todo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.todo.Adapter.AdapterTask
import com.s.todo.Adapter.AdapterTaskHome
import com.s.todo.Data.taskRepository
import com.s.todo.Data.todoDatabase
import com.s.todo.databinding.FragmentHomeBinding
import com.s.todo.ui.BottomTaskDialogFragment
import com.s.todo.viewmodel.ViewModelFactory
import com.s.todo.viewmodel.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    lateinit var viewModell: viewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val ListAdapter: AdapterTaskHome by lazy {
        AdapterTaskHome()
    }

    private val ListAdapterDone: AdapterTaskHome by lazy {
        AdapterTaskHome()
    }

    private val ListAdapterProgress: AdapterTaskHome by lazy {
        AdapterTaskHome()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val repository =
            taskRepository(todoDatabase.getDatabaseInstance(requireContext()).taskDao())
        val viewModelFactory = ViewModelFactory(repository)
        viewModell = ViewModelProvider(this, viewModelFactory)[viewModel::class.java]

        binding.rvTodo.apply {
            adapter = ListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        }

        binding.rvDone.apply {
            adapter = ListAdapterDone
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        }

        binding.rvProgress.apply {
            adapter = ListAdapterProgress
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        }

        getDataProgress("In Progress")
        getData("To Do")
        getDataDone("Done")


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getData(status:String){

        CoroutineScope(Dispatchers.IO).launch {
            val listt = viewModell.getTaskbyStatus(status)

            lifecycleScope.launch(Dispatchers.Main) {

                if(listt.size <=3){

                    binding.moretodo.isGone = true
                    ListAdapter.setData(listt)
                }else{
                    ListAdapter.setData(listt.slice(0..2))
                    binding.moretodo.setOnClickListener({
                        ListAdapter.setData(listt)
                    })
                }


            }

        }
    }

    fun getDataDone(status:String){

        CoroutineScope(Dispatchers.IO).launch {
            val listt = viewModell.getTaskbyStatus(status)

            lifecycleScope.launch(Dispatchers.Main) {

                if(listt.size <=3){

                    binding.moreprodone.isGone = true
                    ListAdapterDone.setData(listt)
                }else{
                    ListAdapterDone.setData(listt.slice(0..2))
                    binding.moreprodone.setOnClickListener({
                        ListAdapterDone.setData(listt)
                    })
                }


            }

        }
    }

    fun getDataProgress(status:String){

        CoroutineScope(Dispatchers.IO).launch {
            val listt = viewModell.getTaskbyStatus(status)

            lifecycleScope.launch(Dispatchers.Main) {

                if(listt.size <=3){
                    ListAdapterProgress.setData(listt)
                    binding.moreprogress.isGone = true

                }else{
                    ListAdapterProgress.setData(listt.slice(0..2))
                    binding.moreprogress.setOnClickListener({
                        ListAdapterProgress.setData(listt)
                    })
                }


            }

        }
    }
}