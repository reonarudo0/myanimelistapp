package com.example.learningleo.repository

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatBotService
{
    @POST("api/message/text/send")
    fun sendResponse(
        @Body body : ResponseBody
    ): Call<ChatResponseDTO>
}

class ChatBotRepository(context: Context,baseUrl: String) : BaseRetrofit(context,baseUrl)
{
    private val service = retrofit.create(ChatBotService::class.java)

    fun send(text: String,callback : (reply:String?) -> Unit)
    {
        val body = ResponseBody(text,"test@test.com","123")
        service.sendResponse(body).enqueue(object : Callback<ChatResponseDTO>{
            override fun onResponse(call: Call<ChatResponseDTO>,response: Response<ChatResponseDTO>) {
                val reply = response.body()?.result?.result?.get(0)?.result?.result?.get(0)
                callback(reply)
            }

            override fun onFailure(call: Call<ChatResponseDTO>, t: Throwable) {
                Log.d("ERRO", t.toString())
                callback("")
            }
        })
    }

}

