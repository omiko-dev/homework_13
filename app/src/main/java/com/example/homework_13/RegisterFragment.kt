package com.example.homework_13

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_13.databinding.FragmentRegisterBinding
import com.google.gson.Gson

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private lateinit var adapter: FieldBoxRecyclerView
    private lateinit var fieldData: Array<Array<FieldData>>

    override fun convertJsonToClass() {
        val jsonString = context?.getJsonFromAsset("fieldData.json")
        fieldData = Gson().fromJson(jsonString, Array<Array<FieldData>>::class.java)
    }

    override fun setUpRecycler() {
        adapter = FieldBoxRecyclerView()
        with(binding){
            recycler.layoutManager = LinearLayoutManager(context)
            recycler.adapter = adapter
        }
        adapter.setList(fieldData)
    }

    private fun Context.getJsonFromAsset(fileName: String): String{
        return this.assets.open(fileName).bufferedReader().use{
            it.readText()
        }
    }
}