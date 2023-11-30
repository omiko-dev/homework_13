package com.example.homework_13

import android.R
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_13.databinding.RegisterChooserFieldBinding
import com.example.homework_13.databinding.RegisterInputFieldBinding
import java.text.DateFormatSymbols
import kotlin.collections.set

class RegisterFieldRecyclerView : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var fieldArray: Array<FieldData> = arrayOf()
    private var finalResult: MutableMap<Int, String> = mutableMapOf()
    var onClick: ((MutableMap<Int, String>) -> Unit)? = null

    private val birthdayArr: Array<String?> by lazy {
        val dataFormatSymbols = DateFormatSymbols()
        arrayOf(null, *dataFormatSymbols.months)
    }

    private val gender: Array<String?> by lazy {
        arrayOf(null, Gender.MALE.gender, Gender.FEMALE.gender)
    }

    inner class RegisterInputFieldViewHolder(private val binding: RegisterInputFieldBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val field = fieldArray[adapterPosition]
            with(binding.field) {
                hint = field.hint
                inputType = if (field.keyBoard == "text") InputType.TYPE_CLASS_TEXT
                else InputType.TYPE_CLASS_NUMBER
            }
        }

        fun validator() {
            val fieldData = fieldArray[adapterPosition]

            with(binding) {
                field.addTextChangedListener {
                    tvError.visibility = View.GONE
                    setResult(fieldData.fieldId, field.text.toString())
                    onClick?.invoke(finalResult)
                }
            }
        }
    }

    fun setResult(key: Int, value: String) {
        finalResult[key] = value
    }

    inner class RegisterChooserFieldViewHolder(private val binding: RegisterChooserFieldBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val field = fieldArray[adapterPosition]
            with(binding.spinner) {
                prompt = field.hint
                val adapter = ArrayAdapter<String>(context, R.layout.simple_spinner_item)
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

                if (field.hint == "Gender") {
                    for (i in birthdayArr) {
                        adapter.add(i)
                    }
                } else {
                    for (i in gender) {
                        adapter.add(i)
                    }
                }
                this.adapter = adapter
            }
        }

        fun getFieldData() {
            with(binding) {
                val fieldData = fieldArray[adapterPosition]
                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val value = parent?.getItemAtPosition(position).toString()
                        setResult(fieldData.fieldId, value)
                        onClick?.invoke(finalResult)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (fieldArray[position].fieldType) {
            FieldType.INPUT.type -> 0
            FieldType.CHOOSE.type -> 1
            else -> -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            RegisterInputFieldViewHolder(
                RegisterInputFieldBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        } else {
            RegisterChooserFieldViewHolder(
                RegisterChooserFieldBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return fieldArray.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RegisterInputFieldViewHolder) {
            holder.bind()
            holder.validator()
        }
        if (holder is RegisterChooserFieldViewHolder) {
            holder.bind()
            holder.getFieldData()
        }
    }

    fun setFieldArray(fieldArray: Array<FieldData>) {
        this.fieldArray = fieldArray
        notifyItemRangeChanged(0, fieldArray.size)
    }
}