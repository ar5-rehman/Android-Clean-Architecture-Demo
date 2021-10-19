package com.mann.ouiparisproject.data.network

import com.mann.ouiparisproject.data.model.DiscoveryPojo
import com.mann.ouiparisproject.data.model.GetActivitiesPojo
import com.mann.ouiparisproject.data.model.Post
import com.mann.ouiparisproject.data.model.SearchPojo
import com.mann.ouiparisproject.data.respository.network.ApiService
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {

    suspend fun getDiscoverData(language: String, keyAPI: String, auth: String, position: String): Response<DiscoveryPojo> = apiService.getDiscoveryData(
        language,
        keyAPI,
        auth,
        position)

    suspend fun getSearchResult(language: String,term: String, keyAPI: String, auth: String): Response<SearchPojo> = apiService.getSearchResult(
        language,
        term,
        keyAPI,
        auth)

    suspend fun getActivities(keyAPI: String, auth: String): Response<GetActivitiesPojo> = apiService.getActivities(
        keyAPI,
        auth)

    suspend fun getActivitiesNextPage(cursor: String, keyAPI: String, auth: String): Response<GetActivitiesPojo> = apiService.getActivitiesNextPage(
        cursor,
        keyAPI,
        auth)
}