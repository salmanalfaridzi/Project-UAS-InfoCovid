package com.project.coronainfowithmvvm.ui.CoronaGlobaldanIndo

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.coronainfowithmvvm.network.NetworkConfig
import com.project.coronainfowithmvvm.ui.CoronaGlobaldanIndo.data.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers.io

class GlobalindoViewModel : ViewModel() {

    private var indo_= MutableLiveData<List<Indonesia>>()
    private var global_= MutableLiveData<ArrayList<Global>>()
    private var status = MutableLiveData<Boolean>()
    private lateinit var globalData : ArrayList<Global>

    init {
        getGlobal()
    }

    @SuppressLint("CheckResult")
    private fun getGlobal() {
        status.value = true
        Single.zip(
            NetworkConfig().api().getMeninggalData() .subscribeOn(io()),
            NetworkConfig().api().getSembuhData().subscribeOn(io()),
            NetworkConfig().api().getPositifData().subscribeOn(io()),
            NetworkConfig().api().getIDNData().subscribeOn(io()),
            Function4<GlobalMeninggal, GlobalSembuh, GlobalPositif,List<Indonesia>, ArrayList<Global>>
            { globalmeninggal, globalsembuh, globalpositif,indo->
                return@Function4 dataGlobal(globalmeninggal,globalsembuh,globalpositif,indo)
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { global ->
                    global_.postValue(global)
                    status.value = false
                },
                { error ->
                    status.value = false
                    Log.e("error",error.message.toString()) }
            )
    }

    fun dataGlobal(meninggal: GlobalMeninggal,
                   sembuh: GlobalSembuh,
                   positif: GlobalPositif,
    indonesia: List<Indonesia>) : ArrayList<Global> {

        globalData = ArrayList()
        //global meninggal
        val globalmeniggal = Global()
        globalmeniggal.name = meninggal.name
        globalmeniggal.value = meninggal.value
        globalData.add(globalmeniggal)
        // global sembuh
        val globalsembuh = Global()
        globalsembuh.name = sembuh.name
        globalsembuh.value = sembuh.value
        globalData.add(globalsembuh)
        // global positif
        val globalpositif = Global()
        globalpositif.name = positif.name
        globalpositif.value = positif.value
        globalData.add(globalpositif)

        indo_.postValue(indonesia)

        return globalData
    }

    fun getData() : MutableLiveData<ArrayList<Global>> {
        return global_
    }

    fun getDataIndo() : MutableLiveData<List<Indonesia>> {
        return indo_
    }

    fun getStatus():MutableLiveData<Boolean>{
        return status
    }

}
