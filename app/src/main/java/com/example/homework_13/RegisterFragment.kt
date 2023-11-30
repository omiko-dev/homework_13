package com.example.homework_13

import android.content.Context
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_13.databinding.FragmentRegisterBinding
import com.google.gson.Gson
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private lateinit var adapter: FieldBoxRecyclerView
    private lateinit var fieldData: Array<Array<FieldData>>
    private val viewModel: RegisterViewModel by viewModels()

    override fun setUpRecycler() {
        adapter = FieldBoxRecyclerView()
        with(binding){
            recycler.layoutManager = LinearLayoutManager(context)
            recycler.adapter = adapter
        }
    }

    override fun setJsonData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.test(context?.getJsonFromAsset("fieldData.json")!!)
            }
        }
    }

    override fun getJsonData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getUiData().collect {
                    fieldData = it
                    adapter.setList(fieldData)
                }
            }
        }
    }

    private fun Context.getJsonFromAsset(fileName: String): String{
        return this.assets.open(fileName).bufferedReader().use{
            it.readText()
        }
    }
}