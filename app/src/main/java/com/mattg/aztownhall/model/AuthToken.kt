package com.mattg.aztownhall.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AuthToken(
    @SerializedName("access_token")
    var accessToken: String,
    @SerializedName("expires_in")
    var expiresIn: Int,
    @SerializedName("permissions")
    var permissions: List<Permission>,
    @SerializedName("AccountId")
    var id: Permission,
    @SerializedName("refresh_token")
    var refreshToken: String,
    @SerializedName("token_type")
    var tokenType: String
)