package com.din.testhttp.jsoup

import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.bumptech.glide.Glide

import com.din.testhttp.R
import com.din.testhttp.databinding.ActivityHtmlBinding

import org.jsoup.Jsoup
import org.jsoup.select.Selector.select

import java.io.IOException
import java.util.ArrayList

/**
 * Created by dinzhenyan on 2018/4/1.
 */

/** * * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　 ┃
 * 　　┃　　　━　　┃
 * 　　┃　┳┛　┗┳  ┃
 * 　　┃　　　　　 ┃
 * 　　┃　　　┻　　┃
 * 　　┃　　　　　 ┃
 * 　　┗━┓　　　┏━┛      Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃         神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　┣┓
 * 　　　　┃　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * * * ━━━━━━感觉萌萌哒━━━━━━  */


class HTMLActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var bind: ActivityHtmlBinding
    private lateinit var pictureAdapter: PictureAdapter
    private lateinit var layoutManager: LinearLayoutManager

    fun dataFromJsoup(): MutableList<Item> {
        val list = mutableListOf<Item>()
        for (i in 2..10) {
            val url = "http://www.99mm.me/meitui/mm_1_$i.html"
            val document = Jsoup.connect(url).get()
            val elements = document.select("div.main").select("ul[id=piclist]").select("li")
            var title: String? = null
            var picture: String? = null
            for (j in elements.indices) {
                title = elements.select("dt")[j].select("img").attr("alt").toString()
                picture = elements.select("dt")[j].select("img").attr("data-img").toString()
                list.add(Item(title, picture))
            }
        }
        return list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_html)
        bind.refresh.isRefreshing = true
        pictureAdapter = PictureAdapter(this@HTMLActivity)
        layoutManager = LinearLayoutManager(this@HTMLActivity, LinearLayoutManager.VERTICAL, false)
        bind.recyclerView.layoutManager = layoutManager
        bind.recyclerView.adapter = pictureAdapter
        MyAsync().execute()
        bind.refresh.setOnRefreshListener(this);
        bind.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(this@HTMLActivity).resumeRequests()
                    val start = layoutManager.findFirstVisibleItemPosition()
                    val end = layoutManager.findLastVisibleItemPosition()
                    pictureAdapter.setCurrentPage(start, end)
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    Glide.with(this@HTMLActivity).pauseRequests()
                }
            }
        })
    }
    
    override fun onRefresh() {
        MyAsync().execute()
    }

    internal inner class MyAsync : AsyncTask<String, Void, List<Item>>() {
        override fun doInBackground(vararg strings: String): List<Item> {
            return dataFromJsoup()
        }

        override fun onPostExecute(items: List<Item>) {
            super.onPostExecute(items)
            pictureAdapter.setInitList(items)
            pictureAdapter.setCurrentPage(0, 3)
            bind.refresh.isRefreshing = false
        }
    }
}