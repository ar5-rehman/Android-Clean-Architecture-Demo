package com.mann.ouiparisproject.data.respository.network

import com.mann.ouiparisproject.data.model.DiscoveryPojo
import com.mann.ouiparisproject.data.model.GetActivitiesPojo
import com.mann.ouiparisproject.data.model.SearchPojo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("marketing/home/")
    suspend fun getDiscoveryData(
        @Header("Accept-Language") language: String,
        @Query("api_key") api_key: String,
        @Header("Request-Time") time: String,
        @Query("position") position: String,
    ): Response<DiscoveryPojo>

    @GET("activities/search/")
    suspend fun getSearchResult(
        @Header("Accept-Language") language: String,
        @Query("term") searchKeyWord: String,
        @Query("api_key") api_key: String,
        @Header("Request-Time") time: String,
    ): Response<SearchPojo>

    @GET("activities/")
    suspend fun getActivities(
        @Query("api_key") api_key: String,
        @Header("Request-Time") time: String,
    ): Response<GetActivitiesPojo>

    @GET("activities/")
    suspend fun getActivitiesNextPage(
        @Query(value = "cursor", encoded = true) cursor: String?,
        @Query("api_key") api_key: String?,
        @Header("Request-Time") time: String?,
    ): Response<GetActivitiesPojo>
}