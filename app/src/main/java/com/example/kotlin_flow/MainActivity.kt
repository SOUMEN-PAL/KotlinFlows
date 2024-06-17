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
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlin_flow.ui.theme.Kotlin_flowTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val channel = Channel<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Kotlin_flowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    flow(modifier = Modifier.padding(innerPadding) , channel)
                }
            }
        }
    }
}

@Composable
fun flow(modifier: Modifier , channel: Channel<Int>){

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {

            producer(channel)
            consumer(channel)


        }) {
            Text(text = "Click")
        }
    }

}

fun producer(channel: Channel<Int>) {
    CoroutineScope(Dispatchers.Main).launch {
        for (i in 1..2) {
            channel.send(i)
            Log.d("Producer", "Sent: $i")
             // Send a value every 500ms
        }
        channel.close() // Close the channel when done
    }
}

fun consumer(channel: Channel<Int>) {
    CoroutineScope(Dispatchers.Main).launch {
        delay(3000) // Start consuming after a 2-second delay
        for (value in channel) {
            Log.d("Consumer", "Received: $value")
        }
    }
}



