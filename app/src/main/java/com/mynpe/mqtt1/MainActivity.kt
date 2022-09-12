package com.mynpe.mqtt1

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.mynpe.mqtt1.Support.MqttClient
import com.mynpe.mqtt1.Support.Shared
import com.mynpe.mqtt1.ui.theme.Mqtt1Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var context: Context = LocalContext.current
            Shared.context = context

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly

            ) {
                Mqtt()

            }

        }
    }
}

@Composable
fun Mqtt() {

    Button(onClick = {MqttClient().connect()}) {
        Text(text = "Conenct Mqtt")
    }

    Button(onClick = {MqttClient().connect()}) {
        Text(text = "MQTT Subscribe")
    }

    val topic =  Shared.topic
    val message = "04"

    Button(onClick = {  MqttClient().publish(topic, message)}) {
        Text(text = "MQTT Publish")
    }

    Button(onClick = {MqttClient().connect()}) {
        Text(text = "MQTT Disconnect")
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Mqtt1Theme {
        Mqtt()
    }
}