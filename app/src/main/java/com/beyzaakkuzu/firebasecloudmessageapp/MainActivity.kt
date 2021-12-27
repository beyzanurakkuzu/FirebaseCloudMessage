package com.beyzaakkuzu.firebasecloudmessageapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

const val TOPIC="/topics/myTopics"
class MainActivity : AppCompatActivity() {
    private val tag="MainActivity"
    private var btnSend: Button?=null
    private lateinit var etTitle:EditText
    private lateinit var etToken:EditText
    private lateinit var etMessage:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnSend=findViewById(R.id.btnSend)
        btnSend?.setOnClickListener {
            val title= etTitle.text.toString()
            val message= etMessage.text.toString()
            if(title.isNotEmpty()&& message.isNotEmpty()){
                PushNotification(
                    NotificationData(title,message),TOPIC)
                    .also {
                        sendNotification(it)
                    }
            }
        }
           }

    private fun sendNotification(notification: PushNotification)= CoroutineScope(Dispatchers.IO).launch{
        try{
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful){
                Log.d(tag, "Response: ${Gson().toJson(response)}")

            }else{
                Log.e(tag, response.errorBody().toString())
            }
        }catch (e:Exception){
            Log.e(tag, e.toString())
        }
    }

    fun btnSend(view: android.view.View) {}
}


