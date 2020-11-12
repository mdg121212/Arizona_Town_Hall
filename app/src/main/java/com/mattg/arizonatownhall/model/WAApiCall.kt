package com.mattg.arizonatownhall.model


import io.swagger.client.models.Event
import io.swagger.client.models.EventsResponse
import retrofit2.Call
import retrofit2.http.*



interface WAApiCall {


    @POST("auth/token")
    fun callPost(@Body Body: okhttp3.RequestBody, @Header("Content-type") type: String): Call<AuthToken>

    @POST("auth/token")
    fun callPostRefresh(@Body Body: okhttp3.RequestBody, @Header("Content-type") type: String): Call<AuthToken>

    @GET("accounts/{accountId}/Events?" + "$" +"filter=isUpComing eq true AND RegistrationEnabled eq true &" + "$" +"sort=ByStartDate asc" )
    fun getEvents(@Path("accountId") id: String, @Header("Authorization") auth: String): Call<EventsResponse>

    @GET("accounts/{accountId}/Events/{eventId}")
    fun getSingleEvent(@Path ("accountId") id: String, @Path("eventId") eventId: String, @Header("Authorization") auth: String): Call<Event>

}
