package com.taha.data.dto

import com.google.gson.annotations.SerializedName

data class RecordDto(
    @SerializedName("datasetid") val datasetId: String,
    @SerializedName("recordid") val recordId: String,
    val fields: FieldsDto,
    val geometry: GeometryDto,
    @SerializedName("record_timestamp") val recordTimestamp: String
)