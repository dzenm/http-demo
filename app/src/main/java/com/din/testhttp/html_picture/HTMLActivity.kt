package com.din.testhttp.html_picture

import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast

import com.din.testhttp.R
import com.din.testhttp.databinding.ActivityHtmlBinding

import org.jsoup.Jsoup

import java.io.IOException
import java.util.ArrayList

/**
 * Created by dinzhenyan on 2018/4/1.
 */

/** * * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛      Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃         神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * * * ━━━━━━感觉萌萌哒━━━━━━  */


class HTMLActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private var bind: ActivityHtmlBinding? = null

    //  从URL加载一个Document对象
    //  从Document对象选择一个节点
    val dataFromJsoup: List<Item>
        get() {
            val list = ArrayList<Item>()
            try {
                for (i in 2..96) {
                    val url = "http://www.99mm.me/meitui/mm_1_$i.html"
                    val document = Jsoup.connect(url).get()
                    val elements = document.select("ul[id=piclist]").select("li")
                    var title: String? = null
                    var picture: String? = null
                    for (j in elements.indices) {
                        title = elements.select("dt").select("a")[j].select("img").attr("alt").toString()
                        picture = elements.select("dt").select("a")[j].select("img").attr("src").toString()
                        list.add(Item(title, picture))
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return list
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_html)
        Toast.makeText(this@HTMLActivity, ">>>>", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        MyAsync().execute()
        //        bind.refresh.setOnRefreshListener(this);
    }

    override fun onRefresh() {}

    internal inner class MyAsync : AsyncTask<String, Void, List<Item>>() {
        override fun doInBackground(vararg strings: String): List<Item> {
            return dataFromJsoup
        }

        override fun onPostExecute(items: List<Item>) {
            super.onPostExecute(items)
            val pictureAdapter = PictureAdapter(this@HTMLActivity, items)
            bind!!.recyclerView.layoutManager = LinearLayoutManager(this@HTMLActivity, LinearLayoutManager.VERTICAL, false)
            bind!!.recyclerView.adapter = pictureAdapter
        }
    }
}