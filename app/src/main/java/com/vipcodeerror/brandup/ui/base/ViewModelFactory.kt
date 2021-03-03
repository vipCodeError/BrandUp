package com.vipcodeerror.brandup.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vipcodeerror.brandup.data.api.ApiHelper
import com.vipcodeerror.brandup.data.repository.MainRepository
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel

class ViewModelFactory (private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}