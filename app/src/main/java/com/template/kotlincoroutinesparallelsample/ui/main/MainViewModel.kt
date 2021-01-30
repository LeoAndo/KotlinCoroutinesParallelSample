package com.template.kotlincoroutinesparallelsample.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {
    /*
    2020-12-07 19:01:19.760 15198-15198/com.template.kotlincoroutinesparallelsample D/MainViewModel: IN
    2020-12-07 19:01:20.959 15198-15198/com.template.kotlincoroutinesparallelsample D/MainViewModel: onSuccess
     */
    fun doSomethingParallelOn(ms: Long) {
        Log.d("MainViewModel", "IN")
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    // https://developer.android.com/kotlin/coroutines-adv?hl=ja#parallel
                    val deferreds = listOf(
                        async { doSomething(ms) },
                        async { doSomething(ms) },
                        async { doSomething(ms) },
                        async { doSomething(ms) },
                        async { doSomething(ms) }
                    )
                    deferreds.awaitAll()
                }
            }.onSuccess {
                Log.d("MainViewModel", "onSuccess")
            }
        }
    }

    /**
     * 2020-12-07 19:01:45.454 15198-15198/com.template.kotlincoroutinesparallelsample D/MainViewModel: IN
     * 2020-12-07 19:01:50.469 15198-15198/com.template.kotlincoroutinesparallelsample D/MainViewModel: onSuccess
     */
    fun doSomethingParallelOff(ms: Long) {
        Log.d("MainViewModel", "IN")
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    doSomething(ms)
                    doSomething(ms)
                    doSomething(ms)
                    doSomething(ms)
                    doSomething(ms)
                }
            }.onSuccess {
                Log.d("MainViewModel", "onSuccess")
            }
        }
    }

    private suspend fun doSomething(ms: Long) {
        delay(ms)
    }
}