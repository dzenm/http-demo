package com.din.testhttp.rxjavahtml

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.din.testhttp.R

class RxJavaAdapter(private val list: ArrayList<RxJava_Item>, private val context: Context) :
        RecyclerView.Adapter<RxJavaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.pic_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var rxJava: RxJava_Item = list.get(position)
        holder!!.bindData(rxJava)
    }

    inner class ViewHolder(internal var view: View) : RecyclerView.ViewHolder(view) {
        private var title: TextView? = null
        private var picture: ImageView? = null

        init {
            title = view.findViewById<View>(R.id.text) as TextView
            picture = view.findViewById<View>(R.id.image) as ImageView
        }

        fun bindData(rxJava: RxJava_Item) {
            title!!.setText(rxJava.title)
            Glide.with(context).load(rxJava.picture).into(picture!!)
        }
    }
}