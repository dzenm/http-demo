package com.din.testhttp.rxjavahtml

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.din.testhttp.R
import com.din.testhttp.databinding.ActivityRxJavaBinding
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup

class RxJavaActivity : AppCompatActivity() {

    private var bind: ActivityRxJavaBinding? = null
    private var URL: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this@RxJavaActivity, R.layout.activity_rx_java)

        var observable = Observable.create<String> {
            it.onNext("Hello RxJava!")
        }
        observable.subscribe(myObserver())
        bind!!.recyclerView.layoutManager = LinearLayoutManager(this@RxJavaActivity, LinearLayoutManager.VERTICAL, false)
        bind!!.recyclerView.adapter = RxJavaAdapter(getData(), this@RxJavaActivity)
    }

    fun getData(): ArrayList<RxJava_Item> {
        var list = ArrayList<RxJava_Item>()
        for (i in 0..20) {
            list.add(RxJava_Item("1", "1"))
        }
        return list
    }

    inner class myObserver : Observer<String> {
        override fun onComplete() {}
        override fun onSubscribe(d: Disposable) {}
        override fun onNext(t: String) {
            Toast.makeText(this@RxJavaActivity, "RxJava Hello!", Toast.LENGTH_SHORT).show()
        }

        override fun onError(e: Throwable) {}
    }

    private fun getConnec() {
        var client = OkHttpClient()
        var request = Request.Builder().url(URL).build()
        var response = client.newCall(request).execute()
        var result: String = response.body().string()
    }

    private fun dealData(data: String): ArrayList<RxJava_Item> {
        var list = ArrayList<RxJava_Item>()
        for (i in 2..97) {
            var url = "http://www.99mm.me/meitui/mm_1_" + i + ".html";
            var document = Jsoup.connect(url).get()
            var element = document.select("ul[id=piclist]").select("li")
            var title: String? = null
            var picture: String? = null
            for (j in element.indices) {
                title = element.select("dt").select("a")[j].select("img").attr("alt")
                picture = element.select("dt").select("a")[j].select("img").attr("src")
                list.add(RxJava_Item(title, picture))
            }
        }
        return list
    }


}