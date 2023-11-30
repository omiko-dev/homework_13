package com.example.homework_13

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow

class RegisterViewModel : ViewModel() {


    private var uiData: MutableStateFlow<Array<Array<FieldData>>> = MutableStateFlow(emptyArray())

    fun getUiData(): MutableStateFlow<Array<Array<FieldData>>>{
        return uiData
    }
    suspend fun test(jsonString: String){
        val jsonData = Gson().fromJson(jsonString, Array<Array<FieldData>>::class.java)
        uiData.emit(jsonData)
    }

}