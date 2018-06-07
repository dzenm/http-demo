package com.din.testhttp.kotlin

import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.din.testhttp.R
import com.din.testhttp.databinding.ActivityJsonkotlinBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import kotlin.concurrent.thread

class JSONKotlinActivity : AppCompatActivity() {

    private lateinit var bind: ActivityJsonkotlinBinding
    private var WEATHER = "https://www.apiopen.top/weatherApi?city=%E6%8F%AD%E9%98%B3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this@JSONKotlinActivity, R.layout.activity_jsonkotlin)
        bind!!.btn.setOnClickListener {
            getRequest()
        }
    }

    fun getRequest() {
        // kotlin创建匿名线程
        var mythread = object : Thread() {
            override fun run() {}
        }.start()

        // Lambda表达式创建线程
        Thread({
            var client = OkHttpClient()
            var request = Request.Builder().url(WEATHER).build()
            var response: Response = client.newCall(request).execute()
            var data = response.body().string()
            weather(data)
        }).start()

        // 一般形式创建thread线程
        val t = Thread({})
        t.isDaemon = false
        t.name = "Thread"
        t.priority = 3
        t.start()

        // kotlin封装的thread线程
        thread(start = true, isDaemon = false, name = "KotlinThread", priority = 3) {}
    }

    fun weather(data: String) {

        var ganmao: String
        var wendu: String
        var city: String

        var jsonObject = JSONObject(data)
        var code: String = jsonObject.getString("code")

        if (code.equals("200")) {
//            var result: JSONObject = jsonObject.getJSONObject("result")
            var data: JSONObject = jsonObject.getJSONObject("data")
            ganmao = data.getString("ganmao")
            wendu = data.getString("wendu")
            city = data.getString("city")
            runOnUiThread {
                bind!!.text.append("ganmao\n" + ganmao)
                bind!!.text.append("wendu\n" + wendu)
                bind!!.text.append("city\n" + city)
            }
        }
    }
}