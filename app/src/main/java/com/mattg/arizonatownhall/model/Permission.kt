package com.mattg.arizonatownhall.model


import com.google.gson.annotations.SerializedName

data class Permission(
    @SerializedName("accountId")
    var accountId: Int,
    @SerializedName("availableScopes")
    var availableScopes: List<String>
)