package com.project.coronainfowithmvvm.network

import com.project.coronainfowithmvvm.ui.CoronaGlobal.data.WorldData
import com.project.coronainfowithmvvm.ui.CoronaGlobaldanIndo.data.GlobalMeninggal
import com.project.coronainfowithmvvm.ui.CoronaGlobaldanIndo.data.GlobalPositif
import com.project.coronainfowithmvvm.ui.CoronaGlobaldanIndo.data.GlobalSembuh
import com.project.coronainfowithmvvm.ui.CoronaGlobaldanIndo.data.Indonesia
import com.project.coronainfowithmvvm.ui.CoronaIndoprov.data.Provinsi
import retrofit2.http.GET
import io.reactivex.Single


interface ApiService {

    @GET("api")
    fun getWorldData(): Single<List<WorldData>>

    @GET("indonesia")
    fun getIDNData(): Single<List<Indonesia>>

    @GET("indonesia/provinsi")
    fun getProvinsiData(): Single<List<Provinsi>>

    @GET("meninggal")
    fun getMeninggalData(): Single<GlobalMeninggal>

    @GET("positif")
    fun getPositifData(): Single<GlobalPositif>

    @GET("sembuh")
    fun getSembuhData(): Single<GlobalSembuh>

}