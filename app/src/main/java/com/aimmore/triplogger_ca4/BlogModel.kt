package com.aimmore.triplogger_ca4


data class BlogModel(
    val id: Long,
    val name: String,
    val location: String,
    val blog: String,
    var isFavorite: Boolean = false,
    val image: ByteArray? = null
)