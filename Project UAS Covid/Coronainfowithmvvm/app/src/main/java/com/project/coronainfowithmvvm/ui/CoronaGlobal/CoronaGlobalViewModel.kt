package com.project.coronainfowithmvvm.ui.CoronaGlobal

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.coronainfowithmvvm.network.NetworkConfig
import com.project.coronainfowithmvvm.ui.CoronaGlobal.data.WorldData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CoronaGlobalViewModel : ViewModel() {

    private var data = MutableLiveData<List<WorldData>>()
    private var status = MutableLiveData<Boolean>()

    init {
        getWorldData()
    }

    @SuppressLint("CheckResult")
    private fun getWorldData() {
        status.value = true
        NetworkConfig().api().getWorldData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {world ->
                    data.postValue(world)
                    status.postValue(false)
                },
                { error ->
                    status.postValue(false)
                })
    }

    fun getData() : MutableLiveData<List<WorldData>> {
        return data
    }


    fun getStatus():MutableLiveData<Boolean>{
        return status
    }
}


