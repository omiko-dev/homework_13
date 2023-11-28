package com.example.homework_13

//noinspection SuspiciousImport
import android.R
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_13.databinding.RegisterChooserFieldBinding
import com.example.homework_13.databinding.RegisterInputFieldBinding
import java.text.DateFormatSymbols

class RegisterFieldRecyclerView : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var fieldArray: Array<FieldData> = arrayOf()

    private val birthdayArr: Array<String> by lazy {
        val dataFormatSymbols = DateFormatSymbols()
        dataFormatSymbols.months
    }
    private val gender: Array<String> by lazy {
        arrayOf("Male", "Female")
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
    }

    inner class RegisterChooserFieldViewHolder(private val binding: RegisterChooserFieldBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val field = fieldArray[adapterPosition]
            with(binding.field) {
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
    }


    override fun getItemViewType(position: Int): Int {
        return when (fieldArray[position].fieldType) {
            "input" -> 0
            "choose" -> 1
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
        }
        if (holder is RegisterChooserFieldViewHolder) {
            holder.bind()
        }
    }

    fun setFieldArray(fieldArray: Array<FieldData>) {
        this.fieldArray = fieldArray
        notifyItemRangeChanged(0, fieldArray.size)
    }

}