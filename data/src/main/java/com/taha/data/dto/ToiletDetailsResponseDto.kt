package com.taha.data.dto

import com.google.gson.annotations.SerializedName

data class ToiletDetailsResponseDto(
    @SerializedName("record")
    val record: RecordDto
)