package com.template.kotlincoroutinesparallelsample.ui.main

import androidx.lifecycle.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainViewModel : ViewModel() {
    private val _timeInMillis = MutableLiveData<Long>()

    val timeInMillis: LiveData<String> = Transformations.map(_timeInMillis) { "処理時間(ms): $it" }

    fun doSomethingParallelOn(ms: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                _timeInMillis.value = measureTimeMillis {
                    val deferreds = listOf(
                        async { doSomething(ms) },
                        async { doSomething(ms) },
                        async { doSomething(ms) },
                        async { doSomething(ms) },
                        async { doSomething(ms) }
                    )
                    deferreds.awaitAll()
                }
            }
        }
    }

    fun doSomethingParallelOff(ms: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                _timeInMillis.value = measureTimeMillis {
                    doSomething(ms)
                    doSomething(ms)
                    doSomething(ms)
                    doSomething(ms)
                    doSomething(ms)
                }
            }
        }
    }

    private suspend fun doSomething(ms: Long) {
        delay(ms)
    }
}