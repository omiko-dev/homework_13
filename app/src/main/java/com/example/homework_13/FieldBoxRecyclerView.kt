package com.example.homework_13

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_13.databinding.FieldBoxBinding
import com.example.homework_13.databinding.RegisterButtonBinding

class FieldBoxRecyclerView : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var array: Array<Array<FieldData>> = arrayOf()
    private lateinit var adapter: RegisterFieldRecyclerView
    private var resultMap: MutableMap<Int, String> = mutableMapOf()
    private var usersData: MutableList<MutableMap<Int, String>> = mutableListOf()
    private var nullIdChecker: MutableMap<Int, Boolean> = mutableMapOf()
    private var nullHintChecker: MutableMap<Int, String> = mutableMapOf()


    inner class FieldBoxViewHolder(private val binding: FieldBoxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun childRecycler() {
            resultFilter()
            with(binding) {
                adapter = RegisterFieldRecyclerView()
                recycler.layoutManager = LinearLayoutManager(binding.root.context)
                recycler.adapter = adapter
                adapter.setFieldArray(array[adapterPosition])
                test()
            }
        }
    }

    inner class RegisterButtonViewHolder(private val binding: RegisterButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private fun checkValid() {
            nullIdChecker.forEach {
                if (resultMap[it.key] == null || resultMap[it.key] == "") {
                    binding.llError.visibility = View.VISIBLE
                    binding.tvFieldName.text = nullHintChecker[it.key]
                    return
                }
            }
            binding.llError.visibility = View.GONE
            usersData.add(resultMap)
        }

        fun childRecycler() {
            binding.btnRegister.setOnClickListener {
                checkValid()
            }
            test()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            FieldBoxViewHolder(
                FieldBoxBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        } else {
            RegisterButtonViewHolder(
                RegisterButtonBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position < array.size) {
            1
        } else {
            2
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FieldBoxViewHolder) {
            holder.childRecycler()
        } else if (holder is RegisterButtonViewHolder) {
            holder.childRecycler()
        }
    }

    override fun getItemCount(): Int {
        return array.size + 1 // plus button item
    }

    fun setList(array: Array<Array<FieldData>>) {
        this.array = array
        notifyItemRangeChanged(0, this.array.size)
    }

    private fun resultFilter() {
        for (i in array) {
            for (j in i) {
                if (j.required) {
                    nullIdChecker[j.fieldId] = true
                    nullHintChecker[j.fieldId] = j.hint
                }
            }
        }
    }

    fun test() {
        adapter.onClick = {
            resultMap.putAll(it)
        }
    }

}