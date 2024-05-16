package com.s.todo.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.crazylegend.imagepicker.pickers.SingleImagePicker
import com.crazylegend.videopicker.pickers.SingleVideoPicker
import com.crazylegend.videopicker.videos.VideoModel
import com.s.todo.Data.taskRepository
import com.s.todo.Data.todoDatabase
import com.s.todo.R
import com.s.todo.databinding.ActivityInputBinding
import com.s.todo.model.task
import com.s.todo.viewmodel.ViewModelFactory
import com.s.todo.viewmodel.viewModel
import java.util.Calendar
import java.util.Locale

class inputActivity : AppCompatActivity() {

    lateinit var binding: ActivityInputBinding
    private val calendar = Calendar.getInstance()
    lateinit var viewModell: viewModel
    var fileuri ="none"
    var type ="none"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository =
            taskRepository(todoDatabase.getDatabaseInstance(this@inputActivity).taskDao())
        val viewModelFactory = ViewModelFactory(repository)
        viewModell = ViewModelProvider(this, viewModelFactory)[viewModel::class.java]

        binding.due.setOnClickListener({
            showDatePicker()
        })

        binding.image.setOnClickListener({

            SingleImagePicker.showPicker(context = this) {
                binding.file.setText(it.displayName)
                fileuri = it.contentUri.toString()
                type ="img"
            }
        })

        binding.video.setOnClickListener({

            SingleVideoPicker.showPicker(context = this, onPickedVideo = ::loadVideo)
        })

        binding.save.setOnClickListener({

            if (binding.due.text.isNotEmpty() && binding.task.text.isNotEmpty()
                && binding.taskdesc.text.isNotEmpty() && binding.reminder.text.isNotEmpty() && binding.status.text.isNotEmpty()
            ) {

                val task = task(
                    null,
                    binding.task.text.toString(),
                    binding.taskdesc.text.toString(),
                    binding.due.text.toString(),
                    binding.reminder.text.toString().toInt(),
                    binding.status.text.toString(),
                    fileuri,
                    type

                )

                viewModell.insertTask(task)

                finish()
            } else {
                Toast.makeText(this, "Kolom tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }


        })

        binding.status.setOnClickListener({

            val dialog = Dialog(this@inputActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.progress_layout)

            val option1 = dialog.findViewById(R.id.todo) as TextView
            val option2 = dialog.findViewById(R.id.progress) as TextView
            val option3 = dialog.findViewById(R.id.done) as TextView

            option1.setOnClickListener({

                binding.status.setText("To Do")
                dialog.dismiss()
            })

            option2.setOnClickListener({

                binding.status.setText("In Progress")
                dialog.dismiss()
            })

            option3.setOnClickListener({

                binding.status.setText("Done")
                dialog.dismiss()
            })

            dialog.show()

        })
    }


    private fun showDatePicker() {
        // Create a DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this, { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                binding.due.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Show the DatePicker dialog
        datePickerDialog.show()
    }

    fun loadVideo(videoModel: VideoModel) {
        binding.file.setText(videoModel.displayName)
        fileuri = videoModel.contentUri.toString()
        type ="video"
    }
}


