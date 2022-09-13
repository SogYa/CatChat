package com.example.realtimechat.screens.screenMenu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.realtimechat.data.model.MenuItem

class MenuVM : ViewModel() {

    private val menuLiveData: MutableLiveData<ArrayList<MenuItem>> = MutableLiveData()

    init {
        menuLiveData.postValue(MenuItem.menuItemsList)
    }

    fun getMenuLiveData(): MutableLiveData<ArrayList<MenuItem>> {
        return menuLiveData
    }
}