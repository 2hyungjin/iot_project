package com.example.iotproject

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @POST("token")
    suspend fun postToken(@Body token: String)

    @GET("led/on")
    suspend fun ledOn()

    @GET("led/off")
    suspend fun ledOff()

    @POST("key")
    suspend fun postServerKey(@Body key:String)
}