package com.din.testhttp.kotlin

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.din.testhttp.R
import com.din.testhttp.databinding.ActivityKotlinBinding

class KotlinActivity : AppCompatActivity() {

    var bind: ActivityKotlinBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_kotlin)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btnOne ->
                bind!!.text.setText("dataBinding")
            else -> {
            }
        }
    }


    fun expandableData() {
        var firstData = arrayOf("西游记", "水浒传", "三国演义", "红楼梦")
        var secondData = arrayOf(
                arrayOf("唐三藏", "孙悟空", "猪八戒", "沙和尚"),
                arrayOf("宋江", "林冲", "李逵", "鲁智深"),
                arrayOf("曹操", "刘备", "孙权", "诸葛亮"),
                arrayOf("贾宝玉", "林黛玉", "薛宝钗", "王熙凤"))
    }
}