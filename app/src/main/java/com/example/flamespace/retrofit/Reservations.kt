package com.example.flamespace.retrofit

data class Reservations(
    val userId: Int,
    val studentId: Int,
    val roomNumber: String,
    var isAvailable: Boolean,
)
