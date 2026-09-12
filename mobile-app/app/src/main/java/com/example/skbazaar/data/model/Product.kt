package com.example.skbazaar.data.model

data class Product(
    val id: Long,
    val name: String,
    val price: Double,
    val mrp: Double,
    val discount: Double,
    val image: String,
    val description: String,
    val brand: String,
    val category: String
)
