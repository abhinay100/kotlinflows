package com.abhinay.kotlinflows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.abhinay.kotlinflows.ui.theme.KotlinflowsTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { number ->
                    // binding.tvCounter.text = number.toString()
                }
            }


        }*/

        collectLatestLifeCycleFlow(viewModel.stateFlow) {number ->
            // binding.tvCounter.text = number.toString()

        }

        setContent {
            KotlinflowsTheme {
                   val viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
                   val time = viewModel.countDownFlow.collectAsState(initial = 10)
                   val count = viewModel.stateFlow.collectAsState(initial = 0)
                   Box(modifier = Modifier.fillMaxSize())  {

                     /*  Text(
                           text = time.value.toString(),
                           fontSize = 30.sp,
                           modifier = Modifier.align(Alignment.Center)
                       )*/
                       Button(onClick = { viewModel.incrementCounter() }) {
                           Text(text = "Counter: ${count.value}")
                       }

                   }



            }
        }
    }
}

fun <T> ComponentActivity.collectLatestLifeCycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {

    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }


}


