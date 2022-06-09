package com.pkumar7.daggerhilt.ui

import android.util.Log
import androidx.hilt.Assisted
import androidx.lifecycle.*
import com.pkumar7.daggerhilt.di.ApplicationTag
import com.pkumar7.daggerhilt.model.Blog
import com.pkumar7.daggerhilt.repository.MainRepository
import com.pkumar7.daggerhilt.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel

@Inject constructor() : ViewModel() {

    @Inject lateinit var mainRepository: MainRepository;

    @ApplicationTag
    @Inject
    lateinit var appTag: String

    private val _dataState: MutableLiveData<DataState<List<Blog>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Blog>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetBlogEvent -> {
                    mainRepository.getBlogs()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is MainStateEvent.None -> {
                    //who cares
                }
            }
        }
    }

    fun printMessage(){
        Log.d(appTag, "Message from MainViewModel ");
    }
}

sealed class MainStateEvent {
    object GetBlogEvent : MainStateEvent()
    object None : MainStateEvent()
}