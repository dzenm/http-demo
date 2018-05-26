package com.din.testhttp.fastjson

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.din.testhttp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.*

/**
 * Created by dinzhenyan on 2018/4/24.
 */
class FastJsonActivity : AppCompatActivity() {

    private var text: TextView? = null
    private var btn: Button? = null
    private var url = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fastjson)

        btn = findViewById<View>(R.id.btn) as Button
        text = findViewById(R.id.text) as TextView
        btn!!.setOnClickListener() {
        }
    }

    private fun sendQuest() {
        var runnable = Runnable {
            var client = OkHttpClient()
            var request = Request.Builder().url(url).build()
            var response = client.newCall(request).execute()
        }
        Thread(runnable).start()
    }

    private fun getResult(date: String) {
        var gson = Gson()
    }
}