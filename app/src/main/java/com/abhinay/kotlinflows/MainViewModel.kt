package com.abhinay.kotlinflows

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch


/**
 * Created by Abhinay on 29/04/25.
 *
 *
 */
class MainViewModel : ViewModel() {

    val countDownFlow = flow<Int> {
        val startingValue = 5
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }

    }

    init {
        collectFlow()
    }

    private fun collectFlow() {

        val flow1 = flow {
            emit(1)
            delay(500)
            emit(2)

        }


        viewModelScope.launch {

         flow1.flatMapConcat { value ->
             flow {
                 emit(value + 1)
                 delay(500L)
                 emit(value + 2)
             }
         }.collect { value ->
                 println("The value is $value")
             }


         }

        /*    val reduceResult = countDownFlow
                .fold(100) { accumulator, value ->
                    accumulator + value

                }
            println("The count is $reduceResult")*/


/*         val count =  countDownFlow
                .filter { time ->
                    time % 2 == 0
                }
                .map { time ->
                    time * time
                }
                .onEach { time ->
                    println(time)
                }
                .count {
                    it % 2 == 0
                }
            println("The Count is $count")*/




               /* .collect { time ->
                *//*delay(1500L)*//*
                println("The current time is $time")
            }*/
        }

    }

