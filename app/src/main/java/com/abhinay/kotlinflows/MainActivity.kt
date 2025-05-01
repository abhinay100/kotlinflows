package com.abhinay.kotlinflows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.abhinay.kotlinflows.ui.theme.KotlinflowsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinflowsTheme {
                   val viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
                   val time = viewModel.countDownFlow.collectAsState(initial = 10)
                   Box(modifier = Modifier.fillMaxSize())  {

                       Text(
                           text = time.value.toString(),
                           fontSize = 30.sp,
                           modifier = Modifier.align(Alignment.Center)
                       )
                   }



            }
        }
    }
}


