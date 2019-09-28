package io.github.iamvukasin.hacktravel.models

import io.github.iamvukasin.hacktravel.models.Pet
import java.io.Serializable

data class RequestData(
    val fromDate: String,
    val toDate: String,
    val fromLocation: String,
    val toLocation: String,
    val numAdults: Int,
    val numChildren: Int
) : Serializable
