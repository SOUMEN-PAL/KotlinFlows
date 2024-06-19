package com.example.kotlin_flow

import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.kotlin_flow.ui.theme.Kotlin_flowTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis


class MainActivity : ComponentActivity() {
    val channel = Channel<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Kotlin_flowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    flow(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun flow(modifier: Modifier){

    


    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {

            producer()
            consumer1()


        }) {
            Text(text = "Click")
        }
    }

}

fun producer() = flow<Int> {

        val list = listOf(1, 2, 3, 4, 5)
        list.forEach {
            delay(1000)
            Log.d("thread", Thread.currentThread().name)
            emit(it)
        }


}

fun consumer1() {
    val job = GlobalScope.launch(Dispatchers.Main) {
        val data = producer()
        val x = data

            .map {
                delay(1000)
                it * 2
                Log.d("map" , Thread.currentThread().name)
            }
            .flowOn(Dispatchers.IO)
            .collect {
                Log.d("thread" , Thread.currentThread().name)
            }

    }
}





