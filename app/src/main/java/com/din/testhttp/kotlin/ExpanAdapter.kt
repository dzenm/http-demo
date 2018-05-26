package com.din.testhttp.kotlin

import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter

/**
 * Created by dinzhenyan on 2018/5/7.
 */
class ExpanAdapter(private val firstData: Array<String>, private val secondData: Array<Array<String>>) : BaseExpandableListAdapter() {

    // 分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们。
    override fun hasStableIds(): Boolean {
        return true
    }

    // 获取指定分组中的子选项的个数
    override fun getChildrenCount(p0: Int): Int {
        return secondData[p0].size
    }

    // 获取指定的分组数据
    override fun getGroup(p0: Int): Any {
        return firstData[p0]
    }

    // 获取指定分组中的指定子选项数据
    override fun getChild(p0: Int, p1: Int): Any {
        return secondData[p0][p1]
    }

    // 获取指定分组的ID, 这个ID必须是唯一的
    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    // 获取子选项的ID, 这个ID必须是唯一的
    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    // 获取分组的个数
    override fun getGroupCount(): Int {
        return firstData.size
    }

    // 指定位置上的子元素是否可选中
    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

    // 获取显示指定分组的视图
    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // 获取显示指定分组中的指定子选项的视图
    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}