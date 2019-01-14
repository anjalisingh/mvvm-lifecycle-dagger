package com.solutions.openweatherapp.ui.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.solutions.openweatherapp.R
import com.solutions.openweatherapp.model.CityModel
import kotlinx.android.synthetic.main.location_list_item.view.*

/**
 * Created by anjalisingh on 14,January,2019
 */

class LocationsAdapter(mContext: Context?,
                       val deleteLocationListener : DeleteLocationListener?= null,
                       val disabledDeleteion : Boolean? = true) : BaseAdapter<OptionViewHolder, CityModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val holder = OptionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.location_list_item, parent, false)
        )

        disabledDeleteion?.let {
            holder?.itemView?.ivDeleteCity?.visibility = if(it) View.GONE else View.VISIBLE
        }

        holder?.itemView?.ivDeleteCity?.setOnClickListener {
            val position = it.tag as Int
            deleteLocationListener?.onDeleteLocation(mItems[position])
        }
        return holder
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val option = mItems[position]
        holder?.itemView?.tvCityName.text = option.name
        holder?.itemView?.tag = option.name
        holder?.itemView?.ivDeleteCity.tag = position
    }

    //    //Item Decorator for Adapter
    inner class ItemDecoration(val mContext: Context) : RecyclerView.ItemDecoration() {
        private var space = 0
        private val mDivider: Drawable? = ContextCompat.getDrawable(mContext, R.drawable.divider)

        init {
            space = mContext.resources.getDimensionPixelOffset(R.dimen.spacing_sixteen)
        }

        override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            val childCount = parent.childCount
            for (i in 0 until childCount - 1) {
                val child = parent.getChildAt(i)
                val position = parent.getChildAdapterPosition(child)
                if (position == -1) break
                if (position != childCount - 1) {
                    val top = child.bottom
                    val bottom = top + mDivider?.intrinsicHeight!!
                    val left = parent.paddingLeft
                    val right = parent.width - (parent.paddingRight)
                    mDivider?.setBounds(left, top, right, bottom)
                    mDivider?.draw(canvas)
                }
            }
        }
    }
}

interface DeleteLocationListener {
    fun onDeleteLocation(city : CityModel)
}

class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)