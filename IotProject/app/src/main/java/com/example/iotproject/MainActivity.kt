package com.example.iotproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var api: Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        api = Retrofit.Builder().baseUrl("http://192.168.0.38/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Api::class.java)
        

        doInCoroutine { api.postServerKey("AAAAa-yQSAI:APA91bFcnWWfqM895Y46EL66jrlHRJdbGA_LDoeh9h29lZMBpZ7zF5QOzWF6TuJDr0vdcczzaiU_nUZZ8L4W3wyUNBV69LXkuln-gg6NMrqcXee0PBmUw-EqE6nteDctXdO9QEXSqO1f") }


        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            doInCoroutine { api.postToken(it.result!!) }
        }

        findViewById<Button>(R.id.btn_led_off).setOnClickListener {
            doInCoroutine { api.ledOff() }
        }
        findViewById<Button>(R.id.btn_led_on).setOnClickListener {
            doInCoroutine { api.ledOn() }
        }
    }

    private fun doInCoroutine(job: suspend () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            job.invoke()
        }
    }
}