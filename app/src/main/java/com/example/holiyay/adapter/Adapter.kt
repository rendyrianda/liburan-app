package com.example.holiyay.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.holiyay.R
import com.example.holiyay.model.Model


class Adapter internal constructor(private val context: Context) : BaseAdapter() {
    internal var hotel = arrayListOf<Model>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false)
        }
        val viewHolder = ViewHolder(view as View)
        val resort = getItem(position) as Model
        viewHolder.bind(resort)
        return view

    }

    override fun getItem(position: Int): Any {
        return hotel[position]

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()

    }

    override fun getCount(): Int = hotel.size

    //ngeget data & nge set view
    private inner class ViewHolder internal constructor(view: View) {
        private val tvTitle: TextView = view.findViewById(R.id.titleName)
        private val tvDesc: TextView = view.findViewById(R.id.desc)
        //inisialize imageview
        private val image: ImageView = view.findViewById(R.id.picture)

        //ngeget data & nge set view
        internal fun bind(model: Model) {
            tvTitle.text = model.title
            tvDesc.text = model.desc
            //resource nya dari model
            Glide.with(context).load(model.image).circleCrop().override(100).into(image)

        }

    }
}