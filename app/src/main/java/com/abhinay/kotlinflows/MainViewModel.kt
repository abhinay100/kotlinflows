package com.abhinay.kotlinflows

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
class MainViewModel(
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    val countDownFlow = flow<Int> {
        val startingValue = 5
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }

    }.flowOn(dispatchers.main)

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
       // collectFlow()

        viewModelScope.launch(dispatchers.main) {
            sharedFlow.collect { number ->
                delay(2000L)
                println("FIRST FLOW: The received number is $number")
            }
        }
        viewModelScope.launch(dispatchers.main) {
            sharedFlow.collect { number ->
                delay(3000L)
                println("SECOND FLOW: The received number is $number")
            }
        }
        squareNumber(3)
    }

    fun squareNumber(number: Int) {
        viewModelScope.launch(dispatchers.main) {
            _sharedFlow.emit(number * number)
        }

    }

    fun incrementCounter() {
        _stateFlow.value += 1
    }

    private fun collectFlow() {

        val flow = flow {
            delay(250)
            emit("Appetizer")
            delay(1000)
            emit("Main Dish")
            delay(100)
            emit("Dessert")
        }



        /*val flow1 = flow {
            emit(1)
            delay(500)
            emit(2)

        }*/


        viewModelScope.launch {

            flow.onEach {
                println("Flow: $it is delivered")
            }


                .collectLatest {
                    println("Flow: Now eating $it")
                    delay(1500)
                    println("Flow: Finished eating $it")

            }

          /*  flow1.flatMapConcat { id ->

                getReceipeById(id)
            }.collect { value ->
                println("The value is $value")
            }*/



            /* flow {
                 emit(value + 1)
                 delay(500L)
                 emit(value + 2)
             }
         }.collect { value ->
                 println("The value is $value")
             }*/


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
}

