package com.mann.ouiparisproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mann.ouiparisproject.data.repository.MainRepository
import com.mann.ouiparisproject.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val getDiscoveryStateFlow:MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    val _getDiscoveryStateFlow:StateFlow<ApiState> = getDiscoveryStateFlow

    fun getDiscoveryData(language: String, keyAPI: String, auth: String, position: String) = viewModelScope.launch {
        getDiscoveryStateFlow.value = ApiState.Loading
        mainRepository.getDiscoveryData( language,
            keyAPI,
            auth,
            position)
            .catch { e->
                getDiscoveryStateFlow.value=ApiState.Failure(e)
            }.collect { data->
                getDiscoveryStateFlow.value=ApiState.DiscoverySuccess(data)
            }
    }

    private val getSearchStateFlow:MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    val _getSearchStateFlow:StateFlow<ApiState> = getSearchStateFlow

    fun getSearchResult(language: String,term: String, keyAPI: String, auth: String) = viewModelScope.launch {
        getSearchStateFlow.value = ApiState.Loading
        mainRepository.getSearchResult(
            language,
            term,
            keyAPI,
            auth)
            .catch { e->
                getSearchStateFlow.value=ApiState.Failure(e)
            }.collect { data->
                getSearchStateFlow.value=ApiState.SearchSuccess(data)
            }
    }


    private val getActivitiesStateFlow:MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    val _getActivitiesStateFlow:StateFlow<ApiState> = getActivitiesStateFlow

    fun getActivities(keyAPI: String, auth: String) = viewModelScope.launch {
        getActivitiesStateFlow.value = ApiState.Loading
        mainRepository.getActivities(
            keyAPI,
            auth)
            .catch { e->
                getActivitiesStateFlow.value=ApiState.Failure(e)
            }.collect { data->
                getActivitiesStateFlow.value=ApiState.ActivitiesSuccess(data)
            }
    }

    private val getActivitiesNextPageStateFlow:MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    val _getActivitiesNextPageStateFlow:StateFlow<ApiState> = getActivitiesNextPageStateFlow

    fun getActivitiesNextPage(cursor: String, keyAPI: String, auth: String) = viewModelScope.launch {
        getActivitiesNextPageStateFlow.value = ApiState.Loading
        mainRepository.getActivitiesNextPage(
            cursor,
            keyAPI,
            auth)
            .catch { e->
                getActivitiesNextPageStateFlow.value=ApiState.Failure(e)
            }.collect { data->
                getActivitiesNextPageStateFlow.value=ApiState.ActivitiesSuccess(data)
            }
    }
}