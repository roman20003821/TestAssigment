package com.example.roman.test.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.roman.test.BlockExplorerService
import com.example.roman.test.Validator
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


class MainActivityViewModel : ViewModel() {
    val balance = MutableLiveData<String>()
    val isAddressValid = MutableLiveData<Boolean>()
    var retrofit = getRetrofitInstance()
    val blockExplorerService: BlockExplorerService = retrofit.create(BlockExplorerService::class.java)

    fun checkForValidation(address: String) {
        isAddressValid.value = Validator.Bitcoin.validateAddress(address)
        if (isAddressValid.value == true)
            checkBalance(address)
    }

    fun checkBalance(address: String) {
        val call: Call<ResponseBody> = blockExplorerService.balance(address)
        call.enqueue(balanceCall())
    }

    private fun balanceCall(): Callback<ResponseBody> {
        return object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                balance.value = "something went wrong"
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                balance.value = response?.body()?.string()
            }

        }
    }

    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://blockexplorer.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
    }
}