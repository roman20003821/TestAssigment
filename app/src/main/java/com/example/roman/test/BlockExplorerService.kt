package com.example.roman.test

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BlockExplorerService {
    @GET("api/addr/{address}/balance")
    fun balance(@Path("address") address: String): Call<ResponseBody>
}