package com.mattg.aztownhall.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Permission(
    @SerializedName("accountId")
    var accountId: Int,
    @SerializedName("availableScopes")
    var availableScopes: List<String>
)