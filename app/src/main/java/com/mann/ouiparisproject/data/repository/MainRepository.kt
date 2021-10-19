package com.mann.ouiparisproject.data.repository

import com.mann.ouiparisproject.data.model.DiscoveryPojo
import com.mann.ouiparisproject.data.model.GetActivitiesPojo
import com.mann.ouiparisproject.data.model.Post
import com.mann.ouiparisproject.data.model.SearchPojo
import com.mann.ouiparisproject.data.network.ApiServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class MainRepository
@Inject
constructor(private val apiServiceImpl: ApiServiceImpl) {

    fun getDiscoveryData(language: String, keyAPI: String, auth: String, position: String):Flow<Response<DiscoveryPojo>> = flow {
        emit(apiServiceImpl.getDiscoverData( language,
            keyAPI,
            auth,
            position))
    }.flowOn(Dispatchers.IO)

    fun getSearchResult(language: String,term: String, keyAPI: String, auth: String):Flow<Response<SearchPojo>> = flow {
        emit(apiServiceImpl.getSearchResult(
            language,
            term,
            keyAPI,
            auth))
    }.flowOn(Dispatchers.IO)

    fun getActivities(keyAPI: String, auth: String):Flow<Response<GetActivitiesPojo>> = flow {
        emit(apiServiceImpl.getActivities(
            keyAPI,
            auth))
    }.flowOn(Dispatchers.IO)

    fun getActivitiesNextPage(cursor: String, keyAPI: String, auth: String):Flow<Response<GetActivitiesPojo>> = flow {
        emit(apiServiceImpl.getActivitiesNextPage(
            cursor,
            keyAPI,
            auth))
    }.flowOn(Dispatchers.IO)

}