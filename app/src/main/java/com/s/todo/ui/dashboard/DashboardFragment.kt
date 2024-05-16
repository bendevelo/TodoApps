package com.s.todo.ui.dashboard

import android.content.Intent
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.s.todo.Data.taskRepository
import com.s.todo.Data.todoDatabase
import com.s.todo.databinding.FragmentDashboardBinding
import com.s.todo.model.task
import com.s.todo.ui.BottomTaskDialogFragment
import com.s.todo.ui.inputActivity
import com.s.todo.util.AdditionalIndicator
import com.s.todo.viewmodel.ViewModelFactory
import com.s.todo.viewmodel.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.CalendarView
import java.util.Calendar
import java.util.Locale

class DashboardFragment : Fragment(),BottomTaskDialogFragment.BottomListLocations {

    private var _binding: FragmentDashboardBinding? = null
    lateinit var indicators: ArrayList<CalendarView.DateIndicator>
    lateinit var viewModell: viewModel


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val repository =
            taskRepository(todoDatabase.getDatabaseInstance(requireContext()).taskDao())
        val viewModelFactory = ViewModelFactory(repository)
        viewModell = ViewModelProvider(this, viewModelFactory)[viewModel::class.java]

        //binding
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        // Set up calendar
        val calendar = Calendar.getInstance()
        val initialDate = CalendarDate(calendar.time)
        binding.calendarView.setupCalendar()

        binding.calendarView.setupCalendar(
            initialDate = initialDate,
            showYearSelectionView = true
        )


        binding.calendarView.onDateClickListener = { date ->
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(date.date.time)

            CoroutineScope(Dispatchers.IO).launch {
                val listt = viewModell.getTaskbyDue(formattedDate)

                lifecycleScope.launch(Dispatchers.Main) {

                    val bottom = BottomTaskDialogFragment(listt,formattedDate)
                    bottom.show(parentFragmentManager, "data")
                }

            }


        }

        binding.add.setOnClickListener({
            val intent = Intent(requireContext(), inputActivity::class.java)
            startActivity(intent)
        })

generateAdditionalindicator()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun generateAdditionalindicator() {

        val additionaIndicator = mutableListOf<AdditionalIndicator>()
        CoroutineScope(Dispatchers.IO).launch {
            val viewDate = viewModell.getTrx().distinctBy { it.due }

            lifecycleScope.launch(Dispatchers.Main) {
                val formatter = SimpleDateFormat("dd-MM-yyyy")

                for ( date in viewDate ){
                    additionaIndicator.add(AdditionalIndicator(Color.BLUE, CalendarDate(formatter.parse(date.due))))

                }

                binding.calendarView.datesIndicators = additionaIndicator
                Log.d("tanggal ",additionaIndicator.size.toString())
            }

        }



    }

    override fun onResume() {
        super.onResume()
        generateAdditionalindicator()
    }

    override fun onButtonClick(task: String) {
        TODO("Not yet implemented")
    }
}