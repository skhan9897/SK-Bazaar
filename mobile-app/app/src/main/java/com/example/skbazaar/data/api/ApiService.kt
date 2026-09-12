package com.example.skbazaar.data.api

import com.example.skbazaar.data.model.Product
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @POST("auth/send-otp")
    suspend fun sendOtp(@Query("mobile") mobile: String): String

    @POST("auth/verify-otp")
    suspend fun verifyOtp(
        @Query("mobile") mobile: String,
        @Query("code") code: String
    ): AuthResponse
}

data class AuthResponse(
    val token: String,
    val email: String,
    val role: String
)
