package com.mynpe.mqtt1.Support

import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MqttClient {
    lateinit var mqttClient: MqttAndroidClient

    //TAG
    companion object {
        const val TAG = "AndroidMqttClient"
    }

    fun connect() {

        mqttClient = MqttAndroidClient(Shared.context, Shared.serverURI, "kotlin_client")

        mqttClient.setCallback(object : MqttCallback{
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.e(TAG, "Receive message: ${message.toString()} from topic: $topic")
            }

            override fun connectionLost(cause: Throwable?) {
                Log.e(TAG, "Connection lost ${cause.toString()}")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {

            }
        })

        val options = MqttConnectOptions()
        options.userName = Shared.userName
        options.password = Shared.password.toCharArray()

        try {
            mqttClient.connect(options, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.e(TAG, "Connection success")
                    val topics = arrayOf( Shared.topic)
//                    subscribe(topics)

                    val topic = Shared.topic
                    val message = "0"
                    publish(topic, message)
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.e(TAG, "Connection failure")
                }
            })
        }catch (e: MqttException) {
            Log.e(TAG, e.printStackTrace().toString())
        }



    }

    fun subscribe(topics: Array<String>) {

        topics.forEach { topic ->
            Log.e("Sub",topic)
            try {
                mqttClient.subscribe(topic, 1, null, object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        Log.e(TAG, "Subscribed to $topic")
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Log.e(TAG, "Failed to subscribe $topic")
                    }
                })

            } catch (e: MqttException) {
                Log.e("Sub error", e.printStackTrace().toString())
            }
        }



    }

    fun publish(topic: String, msg: String, qos: Int = 1, retained: Boolean = false) {

        println(msg.toByteArray())

        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            message.qos = qos
            message.isRetained = retained
            mqttClient.publish(topic, message, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.e(TAG, "$msg published to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.e(TAG, "Failed to publish $msg to $topic")
                }
            })
        }catch (e: MqttException) {
            Log.e("Publish error :", e.printStackTrace().toString())
        }
    }

    fun disconnect() {
        try {
            mqttClient.disconnect(null, object : IMqttActionListener{
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Disconnected")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Failed to disconnect")
                }
            })
        }catch (e: MqttException) {
            Log.e("Discconect error: ", e.printStackTrace().toString())
        }
    }
}