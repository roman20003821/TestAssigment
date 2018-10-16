package com.example.roman.test.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.roman.test.R
import com.example.roman.test.databinding.ActivityMainBinding
import com.example.roman.test.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        subscribeOnAddressValidation()
    }

    fun subscribeOnAddressValidation() {
        viewModel.isAddressValid.observe(this, Observer {
            if (it == false) {
                textInputLayout?.editText?.error = resources.getString(R.string.error_input)
                balance.text = ""
            }
        })
    }

    fun checkBalanceClick(view: View) {
        viewModel.checkForValidation(textInputLayout.editText?.text.toString())
    }
}
