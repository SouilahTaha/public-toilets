package com.taha.publictoilets.navigation

import kotlinx.serialization.Serializable


@Serializable
object ToiletsScreen

@Serializable
data class ToiletDetailsScreen(val toiletId: String)