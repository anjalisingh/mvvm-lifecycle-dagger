package com.solutions.mvvmlifeycledagger.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import java.util.ArrayList

/**
 * Created by anjalisingh on 14,January,2019
 */

abstract class BaseAdapter<VH : RecyclerView.ViewHolder, T>  : RecyclerView.Adapter<VH> {

    protected var mItems: MutableList<T>

    protected val stableId: Boolean
        get() = true

    var items: MutableList<T>
        get() = mItems
        set(items) {
            this.mItems.clear()
            this.mItems = items
            notifyDataSetChanged()
        }

    constructor() {
        mItems = ArrayList()
        setHasStableIds(stableId)
    }

    constructor(items: MutableList<T>) {
        this.mItems = items
        setHasStableIds(stableId)
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    abstract override fun onBindViewHolder(holder: VH, position: Int)

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun getItem(position: Int): T {
        return mItems[position]
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun addItem(position: Int, item: T) {
        var position = position

        if (position < 0) {
            return
        }

        if (position < mItems.size) {
            mItems.add(position, item)
        } else {
            mItems.add(item)
            position = mItems.size
        }

        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        this.mItems.removeAt(position)
        notifyItemRemoved(position)
    }

    fun removeItems() {
        this.mItems.clear()
        notifyItemRangeRemoved(0, mItems.size)
    }

    fun addItems(items: List<T>) {
        val startIndex = this.mItems.size
        this.mItems.addAll(startIndex, items)
        notifyItemRangeInserted(startIndex, items.size)
    }
}