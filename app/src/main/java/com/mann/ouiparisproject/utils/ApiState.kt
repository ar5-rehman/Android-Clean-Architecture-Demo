package com.mann.ouiparisproject.utils

import com.mann.ouiparisproject.data.model.DiscoveryPojo
import com.mann.ouiparisproject.data.model.GetActivitiesPojo
import com.mann.ouiparisproject.data.model.SearchPojo
import retrofit2.Response

sealed class ApiState{
    object Loading : ApiState()
    class Failure(val msg:Throwable) : ApiState()
    class DiscoverySuccess(val data: Response<DiscoveryPojo>) : ApiState()
    class SearchSuccess(val data: Response<SearchPojo>) : ApiState()
    class ActivitiesSuccess(val data: Response<GetActivitiesPojo>) : ApiState()
    object Empty : ApiState()
}
