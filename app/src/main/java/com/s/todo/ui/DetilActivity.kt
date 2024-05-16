package com.s.todo.ui

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.s.todo.Data.taskRepository
import com.s.todo.Data.todoDatabase
import com.s.todo.R
import com.s.todo.databinding.ActivityDetilBinding
import com.s.todo.model.task
import com.s.todo.viewmodel.ViewModelFactory
import com.s.todo.viewmodel.viewModel

class DetilActivity : AppCompatActivity() {

    lateinit var binding:ActivityDetilBinding
    lateinit var viewModell: viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.task.text = intent.getStringExtra("title")
        binding.desc.text = intent.getStringExtra("desc")
        binding.due.text = "Due : "+intent.getStringExtra("due")

        binding.status.setText(intent.getStringExtra("status"))

        val repository =
            taskRepository(todoDatabase.getDatabaseInstance(this@DetilActivity).taskDao())
        val viewModelFactory = ViewModelFactory(repository)
        viewModell = ViewModelProvider(this, viewModelFactory)[viewModel::class.java]

        binding.status.setOnClickListener({

            val dialog = Dialog(this@DetilActivity)
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

        binding.delete.setOnClickListener({

            val task = task(
                intent.getStringExtra("id").toString().toLong(),
                intent.getStringExtra("title").toString(),
                intent.getStringExtra("desc").toString(),
                intent.getStringExtra("due").toString(),
                intent.getStringExtra("reminder").toString().toInt(),
                binding.status.text.toString(),
                intent.getStringExtra("file").toString(),
                intent.getStringExtra("type").toString(),
            )

            viewModell.deleteTask(task)
            finish()

        })


        binding.save.setOnClickListener({

            Log.d("remin",    intent.getStringExtra("reminder").toString())

            val task = task(
                intent.getStringExtra("id").toString().toLong(),
                intent.getStringExtra("title").toString(),
                intent.getStringExtra("desc").toString(),
                intent.getStringExtra("due").toString(),
                intent.getStringExtra("reminder").toString().toInt(),
                binding.status.text.toString(),
                intent.getStringExtra("file").toString(),
                intent.getStringExtra("type").toString(),
            )

            viewModell.updateTask(task)
            finish()

        })



    }
}