package com.example.homework_13

import com.google.gson.annotations.SerializedName

data class FieldData(
    @SerializedName("field_id") val fieldId: Int,
    @SerializedName("hint") val hint: String,
    @SerializedName("field_type") val fieldType: String,
    @SerializedName("keyboard") val keyBoard: String?,
    @SerializedName("required") val required: Boolean,
    @SerializedName("is_active") val issActive: Boolean,
    @SerializedName("icon") val icon: String
)