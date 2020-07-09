package com.project.coronainfowithmvvm.ui.CoronaGlobal.data

import com.google.gson.annotations.SerializedName

class WorldData {
    @SerializedName("attributes")
    private var attributes: Attributes? = null

    fun getAttributes(): Attributes? {
        return attributes
    }
}