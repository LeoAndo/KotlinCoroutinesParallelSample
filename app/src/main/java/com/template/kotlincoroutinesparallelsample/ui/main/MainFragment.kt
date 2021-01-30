package com.template.kotlincoroutinesparallelsample.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.template.kotlincoroutinesparallelsample.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        view.findViewById<Button>(R.id.buttonParallelOn).setOnClickListener {
            kotlin.runCatching {
                val inputValue =
                    view.findViewById<EditText>(R.id.editTextNumberSigned).text.toString().toLong()
                viewModel.doSomethingParallelOn(inputValue)
            }.onFailure {
                // NumberFormatException
                Log.e("MainFragment", "message: " + it.message)
            }
        }
        view.findViewById<Button>(R.id.buttonParallelOff).setOnClickListener {
            kotlin.runCatching {
                val inputValue =
                    view.findViewById<EditText>(R.id.editTextNumberSigned).text.toString().toLong()
                viewModel.doSomethingParallelOff(inputValue)
            }.onFailure {
                Log.e("MainFragment", "message: " + it.message)
            }
        }
    }

}